
CREATE TABLE storage_plans
(
    code VARCHAR(10) PRIMARY KEY,
    limit_bytes BIGINT NOT NULL CHECK (limit_bytes > 0)
);

INSERT INTO storage_plans (code, limit_bytes)
VALUES
    ('FREE', 10737418240),
    ('PLUS', 107374182400),
    ('MAX', 1099511627776);

ALTER TABLE users
    ADD COLUMN plan VARCHAR(10) NOT NULL DEFAULT 'FREE'
        REFERENCES storage_plans(code);

ALTER TABLE users
DROP CONSTRAINT chk_users_storage_quota;

ALTER TABLE users
DROP COLUMN limit_bytes;

ALTER TABLE users
    ADD CONSTRAINT chk_users_storage_counters
        CHECK (
            used_bytes >= 0
                AND reserved_bytes >= 0
            );
