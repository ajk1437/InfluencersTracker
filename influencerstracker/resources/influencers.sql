-- name: create-influencer
-- create infulencer
INSERT INTO influencers
( name, game, views, language, timestamp)
VALUES (:name, :game, :views, :language, :timestamp)

-- name: find-influencers
-- Find influencers
SELECT *
FROM influencers

-- name: influencers-count
-- Counts all the influencers.
SELECT count(*) AS count
FROM influencers

