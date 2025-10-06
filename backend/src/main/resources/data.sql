-- Ensure the 'tasks' table exists
CREATE TABLE IF NOT EXISTS tasks (
    id              UUID PRIMARY KEY,
    title           VARCHAR(255) NOT NULL,
    description     TEXT,
    status          VARCHAR(50) DEFAULT 'PENDING' CHECK (status IN ('PENDING','IN_PROGRESS','COMPLETED','ARCHIVED')),
    priority        VARCHAR(20) DEFAULT 'MEDIUM' CHECK (priority IN ('LOW','MEDIUM','HIGH')),
    due_date        TIMESTAMP(3) NULL,
    completed_at    TIMESTAMP(3) NULL,
    created_at      TIMESTAMP(3) DEFAULT CURRENT_TIMESTAMP,
    updated_at      TIMESTAMP(3) DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX IF NOT EXISTS idx_tasks_status ON tasks(status);
CREATE INDEX IF NOT EXISTS idx_tasks_due_date ON tasks(due_date);

-- Insert well-known UUIDs for specific tasks
INSERT INTO tasks (id, title, description, status, priority, due_date, completed_at, created_at, updated_at)
SELECT '123e4567-e89b-12d3-a456-426614174000',
       'Design Landing Page',
       'Create a modern responsive design for the product landing page.',
       'IN_PROGRESS',
       'HIGH',
       '2025-10-15 18:00:00',
       NULL,
       NOW(),
       NOW()
    WHERE NOT EXISTS (SELECT 1 FROM tasks WHERE id = '123e4567-e89b-12d3-a456-426614174000');

INSERT INTO tasks (id, title, description, status, priority, due_date, completed_at, created_at, updated_at)
SELECT '123e4567-e89b-12d3-a456-426614174001',
       'Write API Documentation',
       'Complete REST API documentation for user and task modules.',
       'PENDING',
       'MEDIUM',
       '2025-10-12 12:00:00',
       NULL,
       NOW(),
       NOW()
    WHERE NOT EXISTS (SELECT 1 FROM tasks WHERE id = '123e4567-e89b-12d3-a456-426614174001');

INSERT INTO tasks (id, title, description, status, priority, due_date, completed_at, created_at, updated_at)
SELECT '123e4567-e89b-12d3-a456-426614174002',
       'Fix Login Bug',
       'Resolve authentication issue when logging in via Google OAuth.',
       'COMPLETED',
       'HIGH',
       '2025-09-28 15:00:00',
       '2025-09-27 16:30:00',
       NOW(),
       NOW()
    WHERE NOT EXISTS (SELECT 1 FROM tasks WHERE id = '123e4567-e89b-12d3-a456-426614174002');

INSERT INTO tasks (id, title, description, status, priority, due_date, completed_at, created_at, updated_at)
SELECT '123e4567-e89b-12d3-a456-426614174003',
       'Implement Notification System',
       'Set up email and in-app notifications for new task assignments.',
       'IN_PROGRESS',
       'HIGH',
       '2025-10-20 09:00:00',
       NULL,
       NOW(),
       NOW()
    WHERE NOT EXISTS (SELECT 1 FROM tasks WHERE id = '123e4567-e89b-12d3-a456-426614174003');

INSERT INTO tasks (id, title, description, status, priority, due_date, completed_at, created_at, updated_at)
SELECT '123e4567-e89b-12d3-a456-426614174004',
       'Database Optimization',
       'Optimize slow queries in task and user tables.',
       'PENDING',
       'MEDIUM',
       '2025-10-25 17:00:00',
       NULL,
       NOW(),
       NOW()
    WHERE NOT EXISTS (SELECT 1 FROM tasks WHERE id = '123e4567-e89b-12d3-a456-426614174004');

INSERT INTO tasks (id, title, description, status, priority, due_date, completed_at, created_at, updated_at)
SELECT '223e4567-e89b-12d3-a456-426614174005',
       'Update User Profile UI',
       'Improve UX for editing user profiles.',
       'COMPLETED',
       'LOW',
       '2025-09-25 11:00:00',
       '2025-09-24 09:20:00',
       NOW(),
       NOW()
    WHERE NOT EXISTS (SELECT 1 FROM tasks WHERE id = '223e4567-e89b-12d3-a456-426614174005');

INSERT INTO tasks (id, title, description, status, priority, due_date, completed_at, created_at, updated_at)
SELECT '223e4567-e89b-12d3-a456-426614174006',
       'Integrate Payment Gateway',
       'Add Stripe integration for premium subscriptions.',
       'IN_PROGRESS',
       'HIGH',
       '2025-10-18 14:00:00',
       NULL,
       NOW(),
       NOW()
    WHERE NOT EXISTS (SELECT 1 FROM tasks WHERE id = '223e4567-e89b-12d3-a456-426614174006');

INSERT INTO tasks (id, title, description, status, priority, due_date, completed_at, created_at, updated_at)
SELECT '223e4567-e89b-12d3-a456-426614174007',
       'Write Unit Tests',
       'Achieve at least 80% test coverage for backend services.',
       'PENDING',
       'MEDIUM',
       '2025-10-22 13:00:00',
       NULL,
       NOW(),
       NOW()
    WHERE NOT EXISTS (SELECT 1 FROM tasks WHERE id = '223e4567-e89b-12d3-a456-426614174007');

INSERT INTO tasks (id, title, description, status, priority, due_date, completed_at, created_at, updated_at)
SELECT '223e4567-e89b-12d3-a456-426614174008',
       'Deploy to Production',
       'Push the latest stable build to production environment.',
       'COMPLETED',
       'HIGH',
       '2025-09-30 10:00:00',
       '2025-09-30 09:45:00',
       NOW(),
       NOW()
    WHERE NOT EXISTS (SELECT 1 FROM tasks WHERE id = '223e4567-e89b-12d3-a456-426614174008');

INSERT INTO tasks (id, title, description, status, priority, due_date, completed_at, created_at, updated_at)
SELECT '223e4567-e89b-12d3-a456-426614174009',
       'Code Review Cleanup',
       'Refactor code based on peer review feedback.',
       'PENDING',
       'LOW',
       '2025-10-10 10:00:00',
       NULL,
       NOW(),
       NOW()
    WHERE NOT EXISTS (SELECT 1 FROM tasks WHERE id = '223e4567-e89b-12d3-a456-426614174009');

INSERT INTO tasks (id, title, description, status, priority, due_date, completed_at, created_at, updated_at)
SELECT '223e4567-e89b-12d3-a456-426614174010',
       'Setup CI/CD Pipeline',
       'Automate build, test, and deploy process with GitHub Actions.',
       'IN_PROGRESS',
       'HIGH',
       '2025-10-19 20:00:00',
       NULL,
       NOW(),
       NOW()
    WHERE NOT EXISTS (SELECT 1 FROM tasks WHERE id = '223e4567-e89b-12d3-a456-426614174010');

INSERT INTO tasks (id, title, description, status, priority, due_date, completed_at, created_at, updated_at)
SELECT '223e4567-e89b-12d3-a456-426614174011',
       'Research Caching Strategies',
       'Investigate Redis and Memcached performance benchmarks.',
       'PENDING',
       'LOW',
       '2025-10-14 16:00:00',
       NULL,
       NOW(),
       NOW()
    WHERE NOT EXISTS (SELECT 1 FROM tasks WHERE id = '223e4567-e89b-12d3-a456-426614174011');

INSERT INTO tasks (id, title, description, status, priority, due_date, completed_at, created_at, updated_at)
SELECT '223e4567-e89b-12d3-a456-426614174012',
       'Security Audit',
       'Run penetration tests and fix vulnerabilities.',
       'PENDING',
       'HIGH',
       '2025-10-30 12:00:00',
       NULL,
       NOW(),
       NOW()
    WHERE NOT EXISTS (SELECT 1 FROM tasks WHERE id = '223e4567-e89b-12d3-a456-426614174012');

INSERT INTO tasks (id, title, description, status, priority, due_date, completed_at, created_at, updated_at)
SELECT '223e4567-e89b-12d3-a456-426614174013',
       'Archive Old Tasks',
       'Clean up completed and archived tasks older than 6 months.',
       'ARCHIVED',
       'LOW',
       NULL,
       '2025-08-01 10:00:00',
       NOW(),
       NOW()
    WHERE NOT EXISTS (SELECT 1 FROM tasks WHERE id = '223e4567-e89b-12d3-a456-426614174013');

INSERT INTO tasks (id, title, description, status, priority, due_date, completed_at, created_at, updated_at)
SELECT '223e4567-e89b-12d3-a456-426614174014',
       'Setup Error Monitoring',
       'Integrate Sentry for real-time error tracking.',
       'COMPLETED',
       'MEDIUM',
       '2025-09-29 14:00:00',
       '2025-09-29 13:50:00',
       NOW(),
       NOW()
    WHERE NOT EXISTS (SELECT 1 FROM tasks WHERE id = '223e4567-e89b-12d3-a456-426614174014');