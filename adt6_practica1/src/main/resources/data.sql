-- Tabla Incidencias: Cambiamos 'titulo' por 'nombre'
INSERT INTO incidencias (nombre, tipo, zona, descripcion, image, fecha)
VALUES ('Fuga de gas persistente', 'Crítica', 'Sector 7-G', 'Huele a huevos podridos...', 'gas_leak.jpg', '2026-03-04');
INSERT INTO incidencias (nombre, tipo, zona, descripcion, image, fecha)
VALUES ('Avistamiento de ratas mutantes', 'Sanidad', 'Sótano -2', 'Tienen el tamaño de un Golden...', 'rat_king.png', '2026-03-03');
INSERT INTO incidencias (nombre, tipo, zona, descripcion, image, fecha)
VALUES ('Cafetera de la oficina explotada', 'Urgencia Máxima', 'Office', 'Nube de vapor...', 'rip_coffee.jpg', '2026-03-04');

-- Tabla Espacios: Solo tienes 'nombre' en tu clase Java, así que solo podemos insertar 'nombre'
INSERT INTO espacios (nombre) VALUES ('Aseos Planta 3');
INSERT INTO espacios (nombre) VALUES ('Sala de Servidores');
INSERT INTO espacios (nombre) VALUES ('Cafetería');
INSERT INTO espacios (nombre) VALUES ('Despacho del Director');
INSERT INTO espacios (nombre) VALUES ('Parking Subterráneo');

--Ponemos lo usuarios
INSERT INTO usuaris (nombre, password, rol, curso, materia)
VALUES ('Don Errequeerre', 'admin1234', 'director', 'Despacho 1', 'Ego y Protocolo');
INSERT INTO usuaris (nombre, password, rol, curso, materia)
VALUES ('Manolo "El Quemao"', 'cafeneed42', 'profesor', '2º DAW', 'Base de Datos (y misticismo)');
INSERT INTO usuaris (nombre, password, rol, curso, materia)
VALUES ('Rubén el Liante', 'password', 'alumno', '1º ASIR', 'Fisica Recreativa');
INSERT INTO usuaris (nombre, password, rol, curso, materia)
VALUES ('Paco "Cinta Americana"', 'paco33', 'mantenimiento', 'Sótano -2', 'Supervivencia Extrema');
INSERT INTO usuaris (nombre, password, rol, curso, materia)
VALUES ('Gemini', 'wit_and_irony', 'soporte', 'La Nube', 'Paciencia Infinita');