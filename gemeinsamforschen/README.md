
# GroupAl Configuration

## building your own groupal server

The Source code lies currently at https://gitup.uni-potsdam.de/dehne/groupal-mono.
Read the README in the CSharp directory (not in the main directory).

## accessing the groupal server 

1. Groupal is currently deployed at http://fleckenroller.cs.uni-potsdam.de:12345.
1. The package unipotsdam.gf.modules.group.preferences.groupal contains examples for request and possible responses
1. It also contains mapping classes
1. The class GroupAlMatcher is the access point for matching groups using groupal

# CompBase Configuration

## building compbase from source

The compbase code can be found at https://gitup.uni-potsdam.de/dehne/compbase. Use the extensive documentation
in git wiki to get it to run.

## accessing the compbase

1. Compbase is deployed at https://apiup.uni-potsdam.de/endpoints/competenceAPI
2. An swagger documentation can be found at http://fleckenroller.cs.uni-potsdam.de/doku/api/#!/default
3. The class unipotsdam.gf.modules.group.learninggoals.CompBaseMatcher is the entry point for accessing the Compbase 
over java

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

###To clean rocketChat, clean mongoDB. This is how you do it:
   
   1. log into "fleckenroller.cs.uni-potsdam.de" via putty with your credentials of UP
   2. type following lines into console:
   3. mongo			//starts mongoDB
   4. use rocketchat//changes from testDB to rocketchatDB		
   5. db.users.remove({"name": /[^fltrailadmin]/})
   6. db.rocketchat_room.remove({"name":/[^general]/})
   7. exit