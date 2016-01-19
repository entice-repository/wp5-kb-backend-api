select image_id from img_tags where tag_id in (2fb154c0-ee19-11e2-9b25-a0b3cc8175f0,2fb106a0-ee19-11e2-9b25-a0b3cc8175f0) allow filtering;
-- in == OR
select count(*) from img_tags where tag_id in (2fb154c0-ee19-11e2-9b25-a0b3cc8175f0,2fb106a0-ee19-11e2-9b25-a0b3cc8175f0) allow filtering;
select count(*) from img_tags where tag_id = 2fb154c0-ee19-11e2-9b25-a0b3cc8175f0 allow filtering;


-- Browser window GUI queries: AND, OR, NEGATE

SELECT image_id FROM img_tags WHERE tag_id = <tag_uuid> allow filtering;
SELECT count(*) FROM img_tags WHERE tag_id in(2fb0b880-ee19-11e2-9b25-a0b3cc8175f0,
                                                2fb09170-ee19-11e2-9b25-a0b3cc8175f0,
                                                2fb06a60-ee19-11e2-9b25-a0b3cc8175f0,
                                                2fb01c40-ee19-11e2-9b25-a0b3cc8175f0,
                                                2fb154c0-ee19-11e2-9b25-a0b3cc8175f0,
                                                2fb12db0-ee19-11e2-9b25-a0b3cc8175f0,
                                                2fb106a0-ee19-11e2-9b25-a0b3cc8175f0) allow filtering;

SELECT image_id FROM img_tags WHERE tag_id = 2fb12db0-ee19-11e2-9b25-a0b3cc8175f0 and tag_id = 2fb06a60-ee19-11e2-9b25-a0b3cc8175f0 allow filtering;

SELECT image_id,tag_id FROM img_tags WHERE tag_id in(2fb0b880-ee19-11e2-9b25-a0b3cc8175f0, 2fb106a0-ee19-11e2-9b25-a0b3cc8175f0) allow filtering;