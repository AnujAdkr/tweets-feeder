package com.mysolution.tweetfeeder.consumer;

import com.mysolution.tweetfeeder.model.Since;
import com.mysolution.tweetfeeder.model.Tweet;
import com.mysolution.tweetfeeder.model.TweetMessage;
import com.mysolution.tweetfeeder.repository.SinceRepository;
import com.mysolution.tweetfeeder.repository.TweetRepository;
import com.mysolution.tweetfeeder.scheduling.TweetEngine;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import twitter4j.*;
import twitter4j.conf.ConfigurationBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Service
public class TwitterApiConsumer {

        @Value("${twitter.consumerKey}")
        private String consumerKey;

        @Value("${twitter.consumerSecret}")
        private String consumerSecret;

        @Value("${twitter.accessToken}")
        private String accessToken;

        @Value("${twitter.accessTokenSecret}")
        private String accessTokenSecret;

        //ToDo -- add this in properties file
        @Value("${twitter.maxTweetCount}")
        private int maxCount;

        @Value("${spring.kafka.producer.topic}")
        private String tweetTopic;

        @Autowired
        SinceRepository sinceRepo;

        @Autowired
        TweetRepository tweetRepo;

        @Autowired
        KafkaTemplate<String, TweetMessage> kafkaTemplate;

        org.slf4j.Logger logger = LoggerFactory.getLogger(TweetEngine.class);

        public List<Status> getTweetsByHashtagAndFromTime(String hashtag, Long sinceId) throws TwitterException {
            Twitter twitter = getTwitterInstance();
            Query query = new Query(hashtag);
            query.setCount(maxCount);
            query.setSinceId(sinceId);
            QueryResult result = twitter.search(query);
            List<Status> tweets = new ArrayList<>();
            for (Status status : result.getTweets()) {
                tweets.add(status);
            }

            return result.getTweets();
        }

        public void processTweets(String hashtag) throws TwitterException {
            Optional<Since> maybeSince = sinceRepo.getSinceId().stream().findFirst();
            long fetchFrom = 0L;
            if (maybeSince.isPresent()) {
                fetchFrom = maybeSince.get().getFetchFrom();
            }
            List<Status> tweetResults = getTweetsByHashtagAndFromTime(hashtag,fetchFrom);
            long sinceIdToBePersisted = 0L;
            for (Status status: tweetResults) {
                sinceIdToBePersisted = status.getId();
                long createdTime = System.currentTimeMillis();
                String datastoreId = status.getId() + "-" + hashtag + "-" + createdTime;
               Tweet tweet = new Tweet(datastoreId, status.getId(),hashtag,createdTime, status.getText());
               tweetRepo.save(tweet);
               TweetMessage tweetMessage = new TweetMessage(tweet.getTweetId(), tweet.getHashTag(),createdTime,
                       tweet.getTweetText());

               kafkaTemplate.send(tweetTopic, Long.toString(tweet.getTweetId()), tweetMessage)
                       .whenComplete((sendResult, ex) -> {
                           if(ex == null) {
                               logger.debug("Successfully Sent message with id = {}",tweetMessage.getTweetId());
                           } else {
                               logger.error("Unable to send message with id = {}. Error = {}",
                                       tweetMessage.getTweetId(),ex.getMessage());
                           }
                       });

            }
            sinceRepo.update(sinceIdToBePersisted);

        }

        private Twitter getTwitterInstance() {
            ConfigurationBuilder configBuilder = new ConfigurationBuilder();
            configBuilder.setDebugEnabled(true);
            configBuilder.setOAuthConsumerKey(consumerKey);
            configBuilder.setOAuthConsumerSecret(consumerSecret);
            configBuilder.setOAuthAccessToken(accessToken);
            configBuilder.setOAuthAccessTokenSecret(accessTokenSecret);
            return new TwitterFactory(configBuilder.build())
                    .getInstance();
        }

}
