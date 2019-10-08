package com.acme.account.service;

import com.acme.account.dto.TransactionType;
import com.acme.account.dto.request.DecreaseRequestDto;
import com.acme.account.dto.response.TransactionDto;
import com.acme.account.exception.BadTokenException;
import com.acme.account.exception.DecreaseAmountException;
import com.acme.account.exception.UserWithoutHistoryException;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static com.acme.account.dto.TransactionType.DECREASE;
import static com.acme.account.dto.TransactionType.INCREASE;

/**
 * Service for account operations
 */
@Service
public class AccountService {

  @Autowired
  private TokenService tokenService;

  private Map<String, Float> accounts = new ConcurrentHashMap<>();
  private Map<String, List<TransactionDto>> transactionHistory = new ConcurrentHashMap<>();

  @PostConstruct
  public void init(){
    accounts.put("user1", 1000f);
    accounts.put("user2", 50f);
    accounts.put("user3", 100f);
    accounts.put("user4", 10000f);
  }

  /**
   * Returns user balance
   *
   * @param userId proper user ID
   * @return balance
   */
  public float getUserBalance(String userId){
    return accounts.get(userId);
  }

  /**
   * Increases users balance by the given amount
   *
   * @param userId proper user ID
   * @param value increase value
   */
  public void increaseUserBalance(String userId, float value){
    accounts.put(userId, accounts.get(userId) + value);
    addToHistory(userId, getTransactionObject(INCREASE, value));
  }

  /**
   * Decreases users balance by the given amount
   *
   * @param userId proper user ID
   * @param decreaseRequestDto decrease value with token
   */
  public void decreaseUserBalance(String userId, DecreaseRequestDto decreaseRequestDto){
    Float balance = accounts.get(userId);
    DecreaseOperationPO po = new DecreaseOperationPO(userId, balance, decreaseRequestDto);
    if(isDecreaseOperationAllowed(po)) {
      if(isAmountSufficient(po)) {
        float decreaseValue = decreaseRequestDto.getValue();
        accounts.put(userId, accounts.get(userId) - decreaseValue);
        addToHistory(userId, getTransactionObject(DECREASE, decreaseValue));
        tokenService.removeKey(userId);
      } else throw new DecreaseAmountException();
    } else throw new BadTokenException();
  }

  /**
   * Returns list of historical transactions
   *
   * @param userId proper user ID
   * @return list of transactions
   */
  public List<TransactionDto> getUserTransactionHistory(String userId){
    if(transactionHistory.containsKey(userId)) return transactionHistory.get(userId);
    else throw new UserWithoutHistoryException();
  }

  /**
   * Checks if given user exists
   *
   * @param userId proper user ID
   * @return true if user exists
   */
  public boolean userExists(String userId){
    return accounts.containsKey(userId);
  }

  private boolean checkNegativeBalanceAfterDecrease(float balance, float changeAmount){
    return balance - changeAmount >= 0;
  }

  private void addToHistory(String userId, TransactionDto action){
    if(transactionHistory.containsKey(userId)) transactionHistory.computeIfPresent(userId, (key, value) -> { value.add(action); return value;} );
    else transactionHistory.computeIfAbsent(userId, k -> new ArrayList<>()).add(action);
  }

  private boolean isAmountSufficient(DecreaseOperationPO decreaseOperationPO) {
    return checkNegativeBalanceAfterDecrease(decreaseOperationPO.getUserBalance(), decreaseOperationPO.getDecreaseRequestDto().getValue());
  }

  private boolean isDecreaseOperationAllowed(DecreaseOperationPO decreaseOperationPO){
    return tokenService.isUserAllowed(decreaseOperationPO.getUserId(), decreaseOperationPO.getDecreaseRequestDto().getToken());
  }

  private TransactionDto getTransactionObject(TransactionType type, float value){
    return TransactionDto.builder().type(type).value(value).build();
  }

  /**
   * Parameter object for decrease operation
   */
  @Getter
  @AllArgsConstructor
  private class DecreaseOperationPO {

    private String userId;
    private float userBalance;
    private DecreaseRequestDto decreaseRequestDto;

  }
}
