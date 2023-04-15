# tweets-feeder
a tweet feeder showing tweets on hashtag data

Problem Statement :
There are 2 microservices:
The 1st Microservice (’producer’) subscribes to Twitter for specific hashtags and associates each hashtag with 
one or more labels (genres). The list of hashtags and their labels can be hardcoded and read from a file at startup.


The 2nd Microservice (‘consumer’) manages a set of users who express their interest in one or more genres 
and receive corresponding tweets. Again the list of users and their passwords can be hardcoded and read from a 
file at startup. Both services expose a REST API (basic authentication on these APIs is sufficient). 
The ‘consumer’ subscribes to the ‘producer’ via REST APIs. Then it receives tweets via Kafka which are 
then appended to the corresponding subscriber’s (i.e. user’s) account in a database. 
Users can use the consumer’s REST API to read these messages. 