DROP TABLE IF EXISTS notice;

CREATE TABLE notice
(
    id         SERIAL PRIMARY KEY,
    title      VARCHAR(255) NOT NULL,
    content    TEXT         NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

COMMENT ON COLUMN notice.id IS 'Auto-incrementing unique identifier for each notice';
COMMENT ON COLUMN notice.title IS 'Title of the notice (required, up to 255 characters)';
COMMENT ON COLUMN notice.content IS 'Body/content of the notice (required, no length limit)';
COMMENT ON COLUMN notice.created_at IS 'Timestamp of creation, defaults to current time';
