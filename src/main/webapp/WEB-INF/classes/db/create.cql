CREATE KEYSPACE <database_name> WITH replication = {'class': 'NetworkTopologyStrategy', 'datacenter1': '2', 'datacenter2': '2'} AND durable_writes = true;

-- test other options:
--CREATE KEYSPACE <database_name> WITH replication = {'class': 'NetworkTopologyStrategy', 'DC1' : 1, 'DC2' : 1} AND durable_writes = true;
--- CREATE KEYSPACE <database_name> WITH replication = {'class': 'SimpleStrategy', 'replication_factor' : 4};

-- use entice;

TRUNCATE monitoring_data;

CREATE KEYSPACE entice WITH replication = {
  'class': 'SimpleStrategy',
  'replication_factor': '1'
};

CREATE TABLE monitoring_data (
    id uuid,
    monitoring_metric text,
	value double,
	time timestamp,
	PRIMARY KEY (id, time, value)
);


SELECT * FROM tag WHERE time >= '2013-01-01 00:00:00+0200' AND  time <= '2013-08-13 23:59:00+0200' AND token(user_id) > previous_token LIMIT 100 ALLOW FILTERING;

SELECT * FROM monitoring_data WHERE monitoring_metric = 'CPU_USAGE' and value > 90 limit 10 allow filtering;

CREATE INDEX time_m on monitoring_data(time);
CREATE INDEX value_m on monitoring_data(value);
CREATE INDEX monitoring_metric_m on monitoring_data(monitor	ing_metric);

SELECT * FROM monitoring_data WHERE time >= '2015-11-12 09:30:00+0000' AND time <= '2015-11-12 11:10:00+0000' limit 20 allow filtering;
SELECT value from monitoring_data where time >= '2015-10-28 00:00:00+0200' limit 10 allow filtering;


INSERT INTO monitoring_data (id, monitoring_metric, value, time) VALUES ( now() , 'CPU_USAGE', 99.9 ,'2015-11-12 12:12:07+0000' );

INSERT INTO monitoring_data (id, monitoring_metric, value, time) VALUES ( now() , 'CPU_USAGE2', 99.9 , 1443657600 );



HTTP Cassandra examples:

http://localhost:8080/JerseyREST/rest/service/get_cpu_usage_times?min_cpu=80&max_cpu=90



CREATE TABLE image (
    id uuid PRIMARY KEY,
    data blob,
    thumbnail_data blob,
	is_jpeg boolean,
	comment text,
	time timestamp
);

CREATE TABLE img_tags (
    id uuid,
    image_id uuid,
	tag_id uuid,
	time timestamp,
	PRIMARY KEY (id, tag_id)
);

-- http://www.datastax.com/support-forums/topic/no-indexed-columns-present-in-by-columns-clause-with-equals-operator

CREATE INDEX ix on img_tags(image_id);

CREATE TABLE img_seq (
    id uuid,
    image_id uuid,
	sequence_id uuid,
	order_asc int,
	time timestamp,
	PRIMARY KEY (id, sequence_id)
);

CREATE INDEX image_s on img_seq(image_id);

CREATE TABLE metadata_type (
    id uuid,
    name text,
	time timestamp,
	PRIMARY KEY (id, name)
);

CREATE TABLE metadata (
    id uuid,
	image_id uuid,
    data text,
	metadata_type_id uuid,
	time timestamp,
	PRIMARY KEY (id)
);

CREATE INDEX image_m on metadata(image_id);

CREATE TABLE attachment_type (
    id uuid,
    name text,
	time timestamp,
	PRIMARY KEY (id, name)
);

CREATE TABLE attachment (
    id uuid,
	image_id uuid,
    data blob,
	attachment_type_id uuid,
	time timestamp,
	PRIMARY KEY (id, image_id)
);

-- CREATE INDEX image_idatt on attachment(image_id);

CREATE INDEX attachment_type_idatt on attachment(attachment_type_id);

