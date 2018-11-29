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

1. FOR IFRAME URL: http://localhost:8080/gemeinsamforschen/rest/chat/login
localhost:8080/gemeinsamforschen/rest/chat/login

1. FOR API URL: http://localhost:8080/gemeinsamforschen/rest/chat/sso
localhost:8080/gemeinsamforschen/rest/chat/sso



after this only login on rocketchat will be:

fleckenroller.cs.uni-potsdam.de:3000
1. open console
1. Meteor.loginWithPassword('fltrailadmin', 'GEbCM1Rso6TUGGMKtGmg6c5EydMQEu61K9zdD10');

### Enable cors and personal access

1. navigate to "http://fleckenroller.cs.uni-potsdam.de:3000/admin/General --> rest api"
2. DONT activate CORS
3. activate personal rest access
