package com.mysolution.tweetfeeder.model;

import jakarta.persistence.*;

@Entity
@Table(name = "since_info")
public class Since {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "fetch_from")
    private long fetchFrom;

    @Column(name = "created_dtm")
    private long createdTimestamp;

    public Since(long sinceId, long created) {
        this.fetchFrom = sinceId;
        this.createdTimestamp = created;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getFetchFrom() {
        return fetchFrom;
    }

    public void setFetchFrom(long fetchFrom) {
        this.fetchFrom = fetchFrom;
    }

    public long getCreatedTimestamp() {
        return createdTimestamp;
    }

    public void setCreatedTimestamp(long createdTimestamp) {
        this.createdTimestamp = createdTimestamp;
    }

    @Override
    public String toString() {
        return "Since{" +
                "id=" + id +
                ", sinceId=" + fetchFrom +
                ", createdTimestamp=" + createdTimestamp +
                '}';
    }
}
