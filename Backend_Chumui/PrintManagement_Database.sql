Create Database PrintManagement;
Use PrintManagement;

CREATE TABLE users (
    user_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(20) NOT NULL,
    last_name VARCHAR(20) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    package_page INT DEFAULT 0
);
Select * From users;
UPDATE users
SET package_page = 40
WHERE user_id = 4;

DELETE FROM users WHERE user_id = 2;

CREATE TABLE operators (
    operator_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(20) NOT NULL,
    last_name VARCHAR(20) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    phone_number VARCHAR(14) NOT NULL,
    password VARCHAR(255) NOT NULL
);
Select * From operators;



CREATE TABLE printers (
    printer_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(30) NOT NULL,
    location VARCHAR(100) NOT NULL,
    status ENUM('AVAILABLE', 'NOT_AVAILABLE') DEFAULT 'NOT_AVAILABLE',
    time_per_page_bw DOUBLE NOT NULL,
    time_per_page_color DOUBLE NOT NULL,
    busy_till DATETIME DEFAULT NULL,
    cost_bw DECIMAL(4,2) NOT NULL,
    cost_color DECIMAL(4,2) NOT NULL,
    op_id BIGINT NOT NULL,
    
    FOREIGN KEY (op_id) REFERENCES operators(operator_id)
);
Select * From Printers;

ALTER TABLE printers
ADD has_package ENUM('YES', 'NO') DEFAULT 'NO';

ALTER TABLE printers
ADD package_price DECIMAL(6,2) DEFAULT NULL;

ALTER TABLE printers
ADD package_page INT DEFAULT NULL;

ALTER TABLE printers ADD COLUMN available_till DATETIME DEFAULT NULL;

CREATE TABLE payments (
    reference_id VARCHAR(50) PRIMARY KEY,
    amount DECIMAL(6,2) NOT NULL,
    date_n_time DATETIME NOT NULL,
    payment_for ENUM('PACKAGE', 'PRINTING') NOT NULL,
    user_id BIGINT NOT NULL,
    op_id BIGINT NOT NULL,
    
    FOREIGN KEY (user_id) REFERENCES users(user_id),
    FOREIGN KEY (op_id) REFERENCES operators(operator_id)
);
Select * From payments;
ALTER TABLE payments
ADD COLUMN payment_status ENUM('APPROVED', 'REJECTED', 'VERIFYING') NOT NULL DEFAULT 'VERIFYING';

CREATE TABLE documents (
    doc_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    pages INT NOT NULL,
    copies INT NOT NULL,
    sides ENUM('SINGLE_SIDED', 'DOUBLE_SIDED') NOT NULL,
    color ENUM('BW', 'COLOR') NOT NULL,
    punching ENUM('YES', 'NO') NOT NULL,
    status ENUM('COMPLETED', 'REJECTED', 'APPROVED', 'VERIFYING') DEFAULT 'VERIFYING',
    start_time DATETIME,
    end_time DATETIME,
    used_package ENUM('YES', 'NO') DEFAULT 'NO',
    file_path VARCHAR(255),
    
    printer_id BIGINT NOT NULL,
    op_id BIGINT NOT NULL,
    reference_id VARCHAR(50),
	user_id BIGINT NOT NULL,
    
    FOREIGN KEY (printer_id) REFERENCES printers(printer_id),
    FOREIGN KEY (op_id) REFERENCES operators(operator_id),
    FOREIGN KEY (reference_id) REFERENCES payments(reference_id),
    FOREIGN KEY (user_id) REFERENCES users(user_id)
);

ALTER TABLE documents
ADD COLUMN original_file_name VARCHAR(255);

select * From documents;