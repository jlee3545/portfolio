BEGIN TRANSACTION;

DROP TABLE IF EXISTS leaderboard;

CREATE TABLE leaderboard(
    player_id serial PRIMARY KEY,
    player_name varchar(50) NOT NULL,
    score int NOT NULL
);

COMMIT;


-- Adding a default entry into the database for testing purposes
-- Feel free to remove later
INSERT INTO leaderboard
(player_name, score)
VALUES
('Neo', 42);
