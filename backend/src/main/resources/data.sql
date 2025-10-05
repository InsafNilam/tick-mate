-- Ensure the 'tasks' table exists
CREATE TABLE IF NOT EXISTS "tasks" (
    id              UUID PRIMARY KEY,
    title           VARCHAR(255) NOT NULL,
    description     TEXT,
    status          VARCHAR(50) DEFAULT 'pending',        -- [pending | in_progress | completed | archived]
    priority        VARCHAR(20) DEFAULT 'medium',         -- [low | medium | high]
    due_date        TIMESTAMP NULL,
    completed_at    TIMESTAMP NULL,
    created_at      TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at      TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
);