CREATE TABLE files (
       id UUID PRIMARY KEY,
       user_id UUID NOT NULL REFERENCES users(id),

       original_name VARCHAR(255) NOT NULL,
       s3_key VARCHAR(1024) NOT NULL UNIQUE,
       content_type VARCHAR(255) NOT NULL,
       size_bytes BIGINT NOT NULL CHECK (size_bytes >= 0),

       status VARCHAR(20) NOT NULL DEFAULT 'PENDING'
           CHECK (status IN ('PENDING', 'READY', 'DELETED')),

       created_at TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

CREATE INDEX idx_files_user_id_created_at
    ON files(user_id, created_at DESC);
