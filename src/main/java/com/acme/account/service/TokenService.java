package com.acme.account.service;

import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class TokenService {

  private Map<String, String> tokens = new ConcurrentHashMap<>();

  public String getToken(String userId){
    String token = UUID.randomUUID().toString();
    tokens.put(userId, token);
    return token;
  }

  public boolean isUserAllowed(String userId, String token){
    return tokens.containsKey(userId) && tokens.get(userId).equals(token);
  }

  public void removeKey(String userId) {
    tokens.remove(userId);
  }

  public boolean isTokenValid(String token){
    return tokens.values().contains(token);
  }

}
