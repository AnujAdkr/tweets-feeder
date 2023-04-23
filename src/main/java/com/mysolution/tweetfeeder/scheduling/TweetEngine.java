package com.mysolution.tweetfeeder.scheduling;

import com.mysolution.tweetfeeder.consumer.TwitterApiConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import twitter4j.TwitterException;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableScheduling
@ConditionalOnProperty(name = "scheduler.enabled", matchIfMissing = true)
public class TweetEngine {

    @Autowired
    TwitterApiConsumer tweetConsumer;

    Logger logger = LoggerFactory.getLogger(TweetEngine.class);

    @Scheduled(initialDelay = 60000, fixedDelay = 300000)
    public void consumeTweets() throws TwitterException {
        //ToDo -- Read hastags from a datastore / file
        List<String> hashTags = Arrays.asList("cricket", "football", "tennis", "elections", "potus");
        logger.info("Starting execution to fetch tweets");
        for (String hashTag: hashTags){
            logger.info("Fetching Tweets with hashtag = {}",hashTag);
                tweetConsumer.processTweets(hashTag);
        }
    }
}
