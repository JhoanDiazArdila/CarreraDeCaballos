
CREATE DATABASE carrera_burros;

USE carrera_burros;

CREATE TABLE dueños(
    cedula_dueño VARCHAR(50) PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL,
    telefono VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE burros(
    id_burro INT AUTO_INCREMENT PRIMARY KEY,
    cedula_dueño VARCHAR(50) NOT NULL,
    nombre VARCHAR(50) NOT NULL,
    edad INT NOT NULL,
    raza VARCHAR(50) NOT NULL,
    FOREIGN KEY (cedula_dueño) REFERENCES dueños(cedula_dueño)
);


CREATE TABLE competencias(
    id_competencia INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL,
    fecha_inicio DATE NOT NULL,
    lugar VARCHAR(50) NOT NULL,
    finalizada BOOLEAN NOT NULL
);

CREATE TABLE burro_competencia(
    id_burro_competencia INT AUTO_INCREMENT PRIMARY KEY,
    id_burro INT NOT NULL,
    id_competencia INT NOT NULL,
    puesto INT,
    FOREIGN KEY (id_burro) REFERENCES burros(id_burro),
    FOREIGN KEY (id_competencia) REFERENCES competencias(id_competencia)
);

CREATE TABLE participantes(
    cedula_part VARCHAR(100) PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    telefono VARCHAR(50) NOT NULL UNIQUE,
    saldo DECIMAL(10,2) NOT NULL
);

CREATE TABLE apuestas(
    id_apuesta INT AUTO_INCREMENT PRIMARY KEY,
    cedula_part VARCHAR(100) NOT NULL,
    id_burro_competencia INT NOT NULL,
    monto DECIMAL(10,2) NOT NULL,
    FOREIGN KEY (cedula_part) REFERENCES participantes(cedula_part),
    FOREIGN KEY (id_burro_competencia) REFERENCES burro_competencia(id_burro_competencia)
);


INSERT INTO dueños(cedula_dueño, nombre, telefono)
VALUES
('123','Mauricio','123'),
('456','Jose','456'),
('789','Fabian','789');


INSERT INTO burros(cedula_dueño, nombre, edad, raza)
VALUES
('123','Shrek', 5,'Montez'),
('123','Matias', 15,'Montez'),
('456','Alberto', 8,'Albina'),
('789','Patricia', 18,'Toronto');


INSERT INTO competencias(nombre, fecha_inicio, lugar, finalizada)
VALUES
('Competencia 1', '2022-01-01', 'Lima', true),
('Competencia 2', '2022-01-15', 'Arequipa', true),
('Competencia 3', '2022-02-01', 'Lima', true),
('Competencia 4', '2025-02-01', 'Bucaramanga', false);


INSERT INTO burro_competencia(id_burro, id_competencia, puesto)
VALUES
(1,1,1),(2,1,2),(4,1,3),
(1,2,3),(3,2,2),(4,2,1),
(1,3,2),(2,3,1);


INSERT INTO participantes(cedula_part, nombre, telefono, saldo)
VALUES
('321','Laura','321', 200),
('654','Maria','654', 100),
('987','Juan','987', 300),
('1905','Jhoan','741', 10000);


INSERT INTO apuestas(cedula_part, id_burro_competencia, monto)
VALUES
('321', 1, 200),
('321', 2, 200),
('321', 4, 200),
('321', 7, 200),
('321', 8, 200),

('654', 5, 100),

('987', 3, 100),
('987', 6, 100);




-- POR COMPETENCIA
SELECT * 
FROM apuestas a
JOIN burro_competencia bc ON a.id_burro_competencia = bc.id_burro_competencia
WHERE bc.id_competencia = 1;


-- POR CEDULA
SELECT * 
FROM apuestas a
WHERE a.cedula_part = 987;


-- POR BURRO
SELECT * 
FROM apuestas a
JOIN burro_competencia bc ON a.id_burro_competencia = bc.id_burro_competencia
WHERE bc.id_burro = 2 AND bc.id_competencia = 3;


