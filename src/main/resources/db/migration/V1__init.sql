-- Tabla de usuarios (campos de seguridad)
CREATE TABLE IF NOT EXISTS users (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    registration_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    locked BOOLEAN NOT NULL DEFAULT FALSE,
    disabled BOOLEAN NOT NULL DEFAULT FALSE
    );

-- Tabla de roles
CREATE TABLE IF NOT EXISTS role (
    id SERIAL PRIMARY KEY,
    role VARCHAR(25) NOT NULL,
    user_id INTEGER NOT NULL,
    UNIQUE (role, user_id),
    FOREIGN KEY (user_id) REFERENCES users (id)
    );

-- Tabla de permisos
CREATE TABLE IF NOT EXISTS permission (
    id SERIAL PRIMARY KEY,
    resource_path VARCHAR(255) NOT NULL,
    http_method VARCHAR(10) NOT NULL
    );

-- Tabla de relación roles-permisos
CREATE TABLE IF NOT EXISTS role_permission (
    role_id INTEGER,
    permission_id INTEGER,
    PRIMARY KEY (role_id, permission_id),
    FOREIGN KEY (role_id) REFERENCES role (id),
    FOREIGN KEY (permission_id) REFERENCES permission (id)
    );

-- Tabla pets
CREATE TABLE IF NOT EXISTS pets (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    species VARCHAR(50) NOT NULL,
    race VARCHAR(50),
    age INTEGER,
    owner_name VARCHAR(100),
    user_id INTEGER NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(id)
    );

-- Tabla vital signs
CREATE TABLE IF NOT EXISTS vital_signs (
    id SERIAL PRIMARY KEY,
    temperature DECIMAL(4,2),
    heart_rate INTEGER,
    timestamp TIMESTAMP NOT NULL,
    device VARCHAR(100),
    pet_id INTEGER NOT NULL,
    FOREIGN KEY (pet_id) REFERENCES pets(id)
    );

