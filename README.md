# tweet-manager

Features :

* Application downloads tweets from my timeline and stores in mysql database.
* Application downloads last 100 favorited tweets

OAuth is used as authentication for calling twitter timeline API.

Tech stack used in the application :

* Java
* Spring boot
* MySQL
* Memcache
* Log4j

Instructions to Run the application :

* Property file need to be created in this location on the machine you want to run the application :
    * /var/personal_projects/tweet_manager/application.properties

* Log file need to be created in this location on the machine you want to run the application :
    * /var/personal_projects/tweet_manager/tweet-manager.log
    * Make sure write permission is enabled for the log file, as application writes logs to above file

Right click on TweetManagerApplication class and run it in Intellij - spring boot will start the application

Tweet data will be stored in MySQL - We don't need databases like mongo db for storing data
as we will only be storing one users data and MySQL can scale just fine for the load.

