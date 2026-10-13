
ALTER TABLE users
    ADD COLUMN used_bytes BIGINT NOT NULL DEFAULT 0,
    ADD COLUMN reserved_bytes BIGINT NOT NULL DEFAULT 0,
    ADD COLUMN limit_bytes BIGINT NOT NULL DEFAULT 10737418240;

ALTER TABLE users
    ADD CONSTRAINT chk_users_storage_quota
        CHECK (
            used_bytes >= 0
                AND reserved_bytes >= 0
                AND limit_bytes >= 0
                AND used_bytes <= limit_bytes
                AND reserved_bytes <= limit_bytes - used_bytes
            );
