create table "since_info" (
  id bigint,
  fetch_from bigint not null,
  created_dtm bigint not null
  primary key (id)
);

create table "tweet_details" (
  id bigint,
  tweet_id bigint not null,
  hashtag_value text not null,
  created_dtm bigint not null,
  tweet_text text not null
  primary key (id)
);

insert into since_info (id, fetch_from, created_dtm) values (1, 0, (extract(epoch from now()) * 1000));