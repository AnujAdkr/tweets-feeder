package com.mysolution.tweetfeeder.model;

import jakarta.persistence.*;

@Entity
@Table(name = "tweet_details")
public class Tweet {
    @Id
    @Column(name = "id")
    String id;

    @Column(name = "tweet_id")
    long tweetId;

    @Column(name = "hashtag_value")
    String hashTag;

    @Column(name = "created_dtm")
    long created;

    @Column(name = "tweet_text")
    String tweetText;

    public Tweet(String id, long tweetId, String hashTag, long created, String tweetText) {
        this.id = id;
        this.tweetId = tweetId;
        this.hashTag = hashTag;
        this.created = created;
        this.tweetText = tweetText;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    @Override
    public String toString() {
        return "Tweet{" +
                "id=" + id +
                ", tweetId=" + tweetId +
                ", hashTag='" + hashTag + '\'' +
                ", created=" + created +
                ", tweetText='" + tweetText + '\'' +
                '}';
    }
}
