# tweet-manager

Features :

* Application downloads tweets from my twitter timeline and stores them in mysql database.
* Application downloads last 100 tweets I favorited.
* Application stores the details of when it make can next requests to twitter API after number of requests cap is reached in memcached
    * It stores the time details in memcached for timeline api and favorites API.

OAuth is used as authentication for calling twitter timeline API.

Tech stack used in the application :

* Java
* Spring boot
* Memcached
* MySQL
* Log4j

Instructions to Run the application :

* Property file need to be created in this location on the machine you want to run the application :
    * /var/personal_projects/tweet_manager/application.properties

* Log file need to be created in this location on the machine you want to run the application :
    * /var/personal_projects/tweet_manager/tweet-manager.log
    * Make sure write permission is enabled for the log file, as application writes logs to above file

* Make sure memcached is up and running on port 11211 on the machine you want to run the application
    * memcached server info need to be set in application property file created above.
    * nc -v localhost 11211 -> to connect to memcached on local machine

* Right click on TweetManagerApplication class and run it in Intellij - spring boot will start the application
    * Tail the logs here : /var/personal_projects/tweet_manager/tweet-manager.log to see what application is doing

Tweet data will be stored in MySQL - We don't need databases like mongo db for storing data
as we will only be storing one users data and MySQL can scale just fine for the load.

*Example Keys for records in memcached :*
```
timeTillNextRequestTimeline
timeTillNextRequestFavorite
```