DROP TABLE feedback_table CASCADE CONSTRAINTS;
DROP TABLE interview_table CASCADE CONSTRAINTS;
DROP TABLE permissions_table CASCADE CONSTRAINTS;
DROP TABLE admin_permissions_table CASCADE CONSTRAINTS;
DROP TABLE users_table CASCADE CONSTRAINTS;
DROP TABLE question_bank_table CASCADE CONSTRAINTS;
DROP TABLE answers_table CASCADE CONSTRAINTS;
DROP SEQUENCE user_id_seq; 

CREATE SEQUENCE user_id_seq
    START WITH 1       
    INCREMENT BY 1;     

CREATE TABLE users_table (
    user_id INT PRIMARY KEY,              
    password VARCHAR2(255) NOT NULL,                           
    user_type VARCHAR2(20) CHECK (user_type IN ('JobSeeker', 'Mentor', 'Administrator')) NOT NULL, 
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP  
);

INSERT INTO users_table (user_id, password,  user_type, created_at) 
VALUES (user_id_seq.NEXTVAL, '123', 'JobSeeker', CURRENT_TIMESTAMP);

INSERT INTO users_table (user_id, password,  user_type, created_at) 
VALUES (user_id_seq.NEXTVAL, '456', 'Mentor', CURRENT_TIMESTAMP);

INSERT INTO users_table (user_id, password,  user_type, created_at) 
VALUES (user_id_seq.NEXTVAL, '789', 'Administrator', CURRENT_TIMESTAMP);

INSERT INTO users_table (user_id, password, user_type, created_at) 
VALUES (user_id_seq.NEXTVAL, 'deema', 'Administrator', CURRENT_TIMESTAMP);

INSERT INTO users_table (user_id, password, user_type, created_at) 
VALUES (user_id_seq.NEXTVAL, 'lana', 'Administrator', CURRENT_TIMESTAMP);

INSERT INTO users_table (user_id, password, user_type, created_at) 
VALUES (user_id_seq.NEXTVAL, 'sara', 'Administrator', CURRENT_TIMESTAMP);

INSERT INTO users_table (user_id, password, user_type, created_at) 
VALUES (user_id_seq.NEXTVAL, 'hanan', 'Administrator', CURRENT_TIMESTAMP);

INSERT INTO users_table (user_id, password, user_type, created_at) 
VALUES (user_id_seq.NEXTVAL, 'ghaida', 'Administrator', CURRENT_TIMESTAMP);

INSERT INTO users_table (user_id, password, user_type, created_at) 
VALUES (user_id_seq.NEXTVAL, '123', 'Mentor', CURRENT_TIMESTAMP);

INSERT INTO users_table (user_id, password, user_type, created_at) 
VALUES (user_id_seq.NEXTVAL, '567', 'Mentor', CURRENT_TIMESTAMP);

INSERT INTO users_table (user_id, password, user_type, created_at) 
VALUES (user_id_seq.NEXTVAL, '987', 'Mentor', CURRENT_TIMESTAMP);

INSERT INTO users_table (user_id, password, user_type, created_at) 
VALUES (user_id_seq.NEXTVAL, '648', 'Mentor', CURRENT_TIMESTAMP);

INSERT INTO users_table (user_id, password, user_type, created_at) 
VALUES (user_id_seq.NEXTVAL, '478', 'Mentor', CURRENT_TIMESTAMP);

INSERT INTO users_table (user_id, password, user_type, created_at) 
VALUES (user_id_seq.NEXTVAL, '576', 'Mentor', CURRENT_TIMESTAMP);

INSERT INTO users_table (user_id, password, user_type, created_at) 
VALUES (user_id_seq.NEXTVAL, 'testuser', 'JobSeeker', CURRENT_TIMESTAMP);

CREATE TABLE interview_table (
    interview_id INT PRIMARY KEY,         
    job_seeker_id INT,                      
    mentor_id INT,                        
    interview_status VARCHAR2(20) DEFAULT 'Scheduled' CHECK (interview_status IN ('Scheduled', 'Completed', 'Cancelled')),
    FOREIGN KEY (job_seeker_id) REFERENCES users_table(user_id), -- Link to JobSeeker
    FOREIGN KEY (mentor_id) REFERENCES users_table(user_id)      -- Link to Mentor
);

INSERT INTO interview_table (interview_id, job_seeker_id, mentor_id, interview_status)
VALUES (1, 1, 2, 'Scheduled');

INSERT INTO interview_table (interview_id, job_seeker_id, mentor_id, interview_status)
VALUES (2, 1, 2, 'Completed');

CREATE TABLE feedback_table (
    feedback_id INT PRIMARY KEY,                  -- feedback_id as the primary key
    interview_id INT,                             -- Foreign key referencing interview_table
    mentor_id INT,                               -- Mentor providing feedback
    job_seeker_id INT,                           -- JobSeeker receiving feedback
    feedback_text VARCHAR2(1000),                 -- Feedback text provided by Mentor
    rating INT CHECK (rating BETWEEN 1 AND 5),    -- Rating from 1 to 5
    FOREIGN KEY (interview_id) REFERENCES interview_table(interview_id),
    FOREIGN KEY (mentor_id) REFERENCES users_table(user_id),  -- Mentor giving feedback
    FOREIGN KEY (job_seeker_id) REFERENCES users_table(user_id) -- JobSeeker receiving feedback
);

-- Step 8: Insert sample feedbacks
INSERT INTO feedback_table (feedback_id, interview_id, mentor_id, job_seeker_id, feedback_text, rating)
VALUES (1, 1, 2, 1, 'Great answers, but needs to improve communication skills.', 3);

INSERT INTO feedback_table (feedback_id, interview_id, mentor_id, job_seeker_id, feedback_text, rating)
VALUES (2, 2, 2, 1, 'Excellent performance. Very good at solving technical problems.', 5);

-- Step 9: Create the permissions table
CREATE TABLE permissions_table (
    permission_id INT PRIMARY KEY,
    permission_name VARCHAR2(100) UNIQUE NOT NULL  -- e.g., 'GRANT_PERMISSION', 'REVOKE_PERMISSION'
);

-- Step 10: Insert permissions data
INSERT INTO permissions_table (permission_id, permission_name) 
VALUES (1, 'GRANT_PERMISSION');

INSERT INTO permissions_table (permission_id, permission_name) 
VALUES (2, 'REVOKE_PERMISSION');

INSERT INTO permissions_table (permission_id, permission_name) 
VALUES (3, 'VIEW_REPORTS');

-- Step 11: Create the admin_permissions table (linking Admin users and their permissions)
CREATE TABLE admin_permissions_table (
    admin_id INT,                              -- Admin user_id
    permission_id INT,                         -- Permission (GRANT, REVOKE, etc.)
    PRIMARY KEY (admin_id, permission_id),
    FOREIGN KEY (admin_id) REFERENCES users_table(user_id),        -- Link to users_table (Admin)
    FOREIGN KEY (permission_id) REFERENCES permissions_table(permission_id) -- Link to permissions_table
);

-- Step 12: Insert admin permissions data
INSERT INTO admin_permissions_table (admin_id, permission_id)
VALUES (3, 1);  -- Admin with GRANT_PERMISSION

INSERT INTO admin_permissions_table (admin_id, permission_id)
VALUES (3, 2);  -- Admin with REVOKE_PERMISSION

INSERT INTO admin_permissions_table (admin_id, permission_id)
VALUES (3, 3);  -- Admin with VIEW_REPORTS permission

-- Step 13: Create the question_bank_table for interview questions (no categories)
-- Step 1: Create the question_bank_table with additional columns
CREATE TABLE question_bank_table (
    question_id INT PRIMARY KEY,               -- question_id as the primary key
    question_text VARCHAR2(1000) NOT NULL,     -- question text                   -- category for the question (e.g., Technical, HR, etc.)
    date_added DATE DEFAULT SYSDATE            -- date when the question was added
);

INSERT INTO question_bank_table (question_id, question_text, date_added)
VALUES (1, 'Tell us about a time when you worked successfully in a team.', SYSDATE);

INSERT INTO question_bank_table (question_id, question_text, date_added)
VALUES (2, 'How do you handle pressure or tight deadlines?', SYSDATE);

INSERT INTO question_bank_table (question_id, question_text, date_added)
VALUES (3, 'Where do you see yourself in 5 years?', SYSDATE);

INSERT INTO question_bank_table (question_id, question_text, date_added)
VALUES (4, 'Explain a time when you had to solve a complex technical problem.',SYSDATE);

INSERT INTO question_bank_table (question_id, question_text, date_added)
VALUES (5, 'What programming languages are you proficient in?', SYSDATE);

INSERT INTO question_bank_table (question_id, question_text, date_added)
VALUES (6, 'Describe a project where you had to manage a team. How did you approach it?', SYSDATE);

INSERT INTO question_bank_table (question_id, question_text, date_added)
VALUES (7, 'How do you stay organized and prioritize tasks in your work?', SYSDATE);

INSERT INTO question_bank_table (question_id, question_text,date_added)
VALUES (8, 'Tell me about a time when you disagreed with a colleague. How did you handle it?', SYSDATE);

INSERT INTO question_bank_table (question_id, question_text,date_added)
VALUES (9, 'How do you approach learning new technologies or tools?', SYSDATE);

INSERT INTO question_bank_table (question_id, question_text, date_added)
VALUES (10, 'What is your approach to troubleshooting technical issues?', SYSDATE);

CREATE TABLE answers_table (
    answer_id INT PRIMARY KEY,
    interview_id INT,
    question_id INT,
    job_seeker_id INT,
    answer_text VARCHAR2(1000),
    FOREIGN KEY (interview_id) REFERENCES interview_table(interview_id),
    FOREIGN KEY (question_id) REFERENCES question_bank_table(question_id),
    FOREIGN KEY (job_seeker_id) REFERENCES users_table(user_id)
);

-- Step 3: Commit the transaction to save the changes
COMMIT;

