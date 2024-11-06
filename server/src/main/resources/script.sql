CREATE TABLE companies (
    company_id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    address VARCHAR(255),
    phone VARCHAR(20),
    email VARCHAR(100),
    website VARCHAR(100)
);

CREATE TABLE cars (
    car_id SERIAL PRIMARY KEY,
    make VARCHAR(50) NOT NULL,
    model VARCHAR(50) NOT NULL,
    year INT CHECK (year >= 1886),
    price DECIMAL(10, 2),
    car_status VARCHAR(20) CHECK (status IN ('available', 'sold')),
    company_id INT REFERENCES Company(company_id) ON DELETE SET NULL
);

CREATE TABLE persons (
    person_id SERIAL PRIMARY KEY,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    birth_date DATE,
    gender VARCHAR(10) CHECK (gender IN ('male', 'female')),
    national_id VARCHAR(20) UNIQUE
);

CREATE TABLE users (
    user_id SERIAL PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    user_role VARCHAR(255) NOT NULL,
    email VARCHAR(100) UNIQUE,
    phone VARCHAR(20),
    address VARCHAR(255),
    person_id INT REFERENCES Person(person_id) ON DELETE SET NULL
);

CREATE TABLE orders (
    order_id SERIAL PRIMARY KEY,
    user_id INT REFERENCES "User"(user_id) ON DELETE SET NULL,
    date DATE NOT NULL,
    order_status VARCHAR(20) CHECK (status IN ('processing', 'completed', 'canceled')),
    total_price DECIMAL(10, 2),
    company_id INT REFERENCES Company(company_id) ON DELETE SET NULL
);

CREATE TABLE services (
    service_id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    description TEXT,
    price DECIMAL(10, 2) NOT NULL,
    company_id INT REFERENCES Company(company_id) ON DELETE SET NULL
);

CREATE TABLE employees (
    employee_id SERIAL PRIMARY KEY,
    position VARCHAR(50) NOT NULL,
    hire_date DATE NOT NULL,
    salary DECIMAL(10, 2),
    company_id INT REFERENCES Company(company_id) ON DELETE SET NULL,
    person_id INT REFERENCES Person(person_id) ON DELETE SET NULL
);

CREATE TABLE payments (
    payment_id SERIAL PRIMARY KEY,
    order_id INT REFERENCES "Order"(order_id) ON DELETE CASCADE,
    amount DECIMAL(10, 2) NOT NULL,
    date DATE NOT NULL,
    payment_method VARCHAR(20) CHECK (method IN ('cash', 'card', 'credit')),
    payment_status VARCHAR(20) CHECK (status IN ('processed', 'canceled')),
    user_id INT REFERENCES "User"(user_id) ON DELETE SET NULL,
    company_id INT REFERENCES Company(company_id) ON DELETE SET NULL
);
