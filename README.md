# tweet-manager

Features :

* Application downloads tweets from my timeline and stores in mysql database.
* Application downloads last 100 favorited tweets
* Application stores the details of when it make can next requests to twitter API after number of requests cap is reached in memcache

OAuth is used as authentication for calling twitter timeline API.

Tech stack used in the application :

* Java
* Spring boot
* Memcache
* MySQL
* Log4j

Instructions to Run the application :

* Property file need to be created in this location on the machine you want to run the application :
    * /var/personal_projects/tweet_manager/application.properties

* Log file need to be created in this location on the machine you want to run the application :
    * /var/personal_projects/tweet_manager/tweet-manager.log
    * Make sure write permission is enabled for the log file, as application writes logs to above file

* Make sure memcache is up and running on port 11211 on the machine you want to run the application
    * memcache server info need to be set in application property file created above.

Right click on TweetManagerApplication class and run it in Intellij - spring boot will start the application

Tweet data will be stored in MySQL - We don't need databases like mongo db for storing data
as we will only be storing one users data and MySQL can scale just fine for the load.