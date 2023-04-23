package com.mysolution.tweetfeeder.repository;

import com.mysolution.tweetfeeder.model.Since;
import com.mysolution.tweetfeeder.model.Tweet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TweetRepository extends JpaRepository<Tweet,Long> {

    List<Tweet> getTweetsWithHashTag(String hashTag);
    List<Tweet> getTweetsWithHashTagBetweenTime(String hashTag, Long fromTime, Long toTime);
}
