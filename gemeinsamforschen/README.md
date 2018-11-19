# Rocket Chat Configuration

## Admin Account
In `GFRocketChatConfig.java` add admin and test user account data.
Username, password, email and rocketChatUserId is necessary. 

## Personal Access Token for API
### Create non expiring access token
Go to `Administration -> Allgemeines -> REST API -> Enable Personal Access Tokens to REST API` 

### Manual Personal Access Token
1. Click on your profile picture -> `Mein Konto` -> `Personal Access Token`
1. add a new personal access token
1. add it to configuration class `GFRocketChatConfig`

### Enable iframe integration
Dont mix up with General/Allegemeines->IFRAME INTEGRATION

Accounts/Konten->IFRAME:

1. click on enable

1. FOR API URL: http://141.89.53.195:8080/gemeinsamforschen/rest/chat/sso
localhost:8080/gemeinsamforschen/rest/chat/sso

1. FOR IFRAME URL: http://141.89.53.195:8080/gemeinsamforschen/rest/chat/login
localhost:8080/gemeinsamforschen/rest/chat/login

