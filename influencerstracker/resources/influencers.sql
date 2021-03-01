-- name: create-influencer
-- create infulencer
INSERT INTO influencers
( user_id, username, game, views, language, timestamp)
VALUES (:user_id, :username, :game, :views, :language, CURRENT_TIMESTAMP)

-- name: find-influencers
-- Find influencers
SELECT *
FROM influencers

-- name: influencers-count
-- Counts all the influencers.
SELECT count(*) AS count
FROM influencers

-- name: influencer-by-id
-- Find influencer by id.
SELECT *
FROM influencers
WHERE id = :id

-- name: influencer-order-by-timestamp
-- Return influencers order by timestamp from most recent ones to the oldest ones.
SELECT *
FROM influencers
ORDER BY timestamp DESC;

-- name: influencer-last-7-days
-- Return influencers order by timestamp.
SELECT *
FROM influencers
WHERE timestamp >= CURRENT_DATE - INTERVAL '7 day';

-- name: change-timestamp
-- change timestamp for testing
UPDATE influencers
SET timestamp = timestamp - INTERVAL '30 day'
WHERE id = :id