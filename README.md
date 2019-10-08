# acmeBank
# Running the application
1. Clone the repository with git (if you don't have git please follow: https://www.linode.com/docs/development/version-control/how-to-install-git-on-linux-mac-and-windows/)
2. If you don't have maven please follow: https://www.baeldung.com/install-maven-on-windows-linux-mac
3. Go to cloned repository via terminal
4. Type in command <b>mvn spring-boot:run</b> and hit Enter.

#ENDPOINTS
##GET /balance/user/<userId>
Returns the current balance value for the specified user.

##GET /history/user/<userId>
Returns the history of the transactions. The array is ordered from the first transaction

##POST /balance/user/<userId>/increase
Headers:
Content-Type: application/json

Body:
{ 
  “value”: 100
}

Performs balance increase operation for the specified user.

##POST /balance/user/<userId>/decrease
Headers:
Content-Type: application/json

Body:
{ 
  “value”: 100,
  “token”: “3a81de”
}

Performs balance decrease operation for the specified user. The operation is secured using one time password generated using the other endpoint.

##POST /tokens/user/<userId>
Generates one time password that can be used to secure other operations.
