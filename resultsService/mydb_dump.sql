PRAGMA foreign_keys=OFF;
BEGIN TRANSACTION;
CREATE TABLE quiz_results (quiz_id integer not null, user_id bigint not null, score integer, primary key (quiz_id, user_id));
INSERT INTO quiz_results VALUES(152,1,20);
INSERT INTO quiz_results VALUES(152,4,-1);
COMMIT;
