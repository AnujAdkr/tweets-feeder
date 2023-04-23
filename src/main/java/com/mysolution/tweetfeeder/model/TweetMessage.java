package com.mysolution.tweetfeeder.model;

import jakarta.persistence.Column;

public class TweetMessage {
    long tweetId;
    String hashTag;
    long created;
    String tweetText;

    public TweetMessage(long tweetId, String hashTag, long created, String tweetText) {
        this.tweetId = tweetId;
        this.hashTag = hashTag;
        this.created = created;
        this.tweetText = tweetText;
    }

    public long getTweetId() {
        return tweetId;
    }

    public void setTweetId(long tweetId) {
        this.tweetId = tweetId;
    }

    public String getHashTag() {
        return hashTag;
    }

    public void setHashTag(String hashTag) {
        this.hashTag = hashTag;
    }

    public long getCreated() {
        return created;
    }

    public void setCreated(long created) {
        this.created = created;
    }

    public String getTweetText() {
        return tweetText;
    }

    public void setTweetText(String tweetText) {
        this.tweetText = tweetText;
    }

}
