-- 1. Usuarios (Los sospechosos habituales)
INSERT INTO usuaris (nombre, password, rol, curso, materia)
VALUES ('Don Errequeerre', 'admin1234', 'director', 'Despacho 1', 'Ego y Protocolo');
INSERT INTO usuaris (nombre, password, rol, curso, materia)
VALUES ('Manolo El Quemao', 'cafeneed42', 'profesor', '2º DAW', 'Base de Datos');
INSERT INTO usuaris (nombre, password, rol, curso, materia)
VALUES ('Alex el Alegre', 'rainbow6', 'alumno', '1º DAM', 'Estética');
INSERT INTO usuaris (nombre, password, rol, curso, materia)
VALUES ('Raul Inazuma', 'pika_pika', 'alumno', '2º ASIR', 'Legendarios');
INSERT INTO usuaris (nombre, password, rol, curso, materia)
VALUES ('Josep el Jabato', 'fuerza_bruta', 'profesor', 'Gimnasio', 'Rugidos');

-- 2. Espacios (Donde ocurren las desgracias)
INSERT INTO espacios (nombre) VALUES ('Aseos Planta 3');
INSERT INTO espacios (nombre) VALUES ('Sala de Servidores');
INSERT INTO espacios (nombre) VALUES ('Cafetería');
INSERT INTO espacios (nombre) VALUES ('Despacho del Director');

-- 3. Incidencias (He quitado 'image' porque NO existe en tu clase Java)
-- He añadido 'estado' y 'alumnoNIA' que sí están en tu código.
INSERT INTO incidencias (nombre, tipo, zona, descripcion, fecha, alumnoNIA, estado)
VALUES ('Fuga de gas persistente', 'Crítica', 'Sector 7-G', 'Huele a huevos podridos...', '2026-03-04', 3, 'ABIERTA');
INSERT INTO incidencias (nombre, tipo, zona, descripcion, fecha, alumnoNIA, estado)
VALUES ('Ratas mutantes', 'Sanidad', 'Sótano -2', 'Tienen el tamaño de un Golden Retriever', '2026-03-03', 4, 'EN PROCESO');
INSERT INTO incidencias (nombre, tipo, zona, descripcion, fecha, alumnoNIA, estado)
VALUES ('Cafetera explotada', 'Urgencia', 'Office', 'Nube de vapor y desesperación', '2026-03-04', 1, 'CERRADA');