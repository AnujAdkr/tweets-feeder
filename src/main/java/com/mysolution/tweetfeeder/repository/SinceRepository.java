package com.mysolution.tweetfeeder.repository;

import com.mysolution.tweetfeeder.model.Since;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SinceRepository extends CrudRepository<Since,Long> {
    @Query("SELECT * from since_info")
    List<Since> getSinceId();

    @Modifying(clearAutomatically = true)
    @Query("UPDATE since_info c SET c.fetch_from = :sinceId")
    Integer update(@Param("sinceId") long sinceId);
}
