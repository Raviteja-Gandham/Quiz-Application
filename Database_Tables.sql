/* To create Admin Table */

Create Table admin (
    admin_id INT NOT NULL AUTO_INCREMENT,
    password VARCHAR(30),
    username VARCHAR(30) NOT NULL UNIQUE,
    PRIMARY KEY (admin_id)
);

/* To create Users Table */

Create Table Users (
    user_id INT NOT NULL AUTO_INCREMENT,
    email VARCHAR(255) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    name VARCHAR(30),
    PRIMARY KEY (user_id)
);

/* To create past_quiz_results Table*/

Create Table past_quiz_results (
    id INT NOT NULL AUTO_INCREMENT,
    user_id INT NOT NULL,
    quiz_name VARCHAR(255) NOT NULL,
    marks_obtained INT NOT NULL,
    attempts INT NOT NULL,
    last_attempt TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    KEY (user_id)
);

/* To create Quiz_results1 */

Create Table quiz_results1 (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    quiz_name VARCHAR(255) NOT NULL,
    marks_obtained INT NOT NULL,
    attempts INT NOT NULL,
    last_attempt TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES user(id)
);

/* To Create Feedback Table */

CREATE TABLE quiz_Feedback (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    quiz_name VARCHAR(255) NOT NULL,
    Feedback_description varchar(300) not null,
    FOREIGN KEY (user_id) REFERENCES users(user_id)
);
