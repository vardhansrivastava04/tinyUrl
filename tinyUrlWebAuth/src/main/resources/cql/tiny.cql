CREATE TABLE tiny.url (
    shorturl text PRIMARY KEY,
    createdat bigint,
    expireat bigint,
    url text,
    userid text
)
CREATE INDEX url_userid_idx ON tiny.url (userid);


CREATE TABLE tiny.user (
    username text PRIMARY KEY,
    name text,
    password text
);


CREATE TABLE tiny.urllog (
    userid text,
    accessedat bigint,
    shorturl text,
    PRIMARY KEY (userid, accessedat)
) 
CREATE INDEX urllog_shorturl_idx ON tiny.urllog (shorturl);
