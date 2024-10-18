/*
 * SCRIPT CREADOR ESTRUCTURA BBDD
 * 
 */


/*
 * BORRADO DE TABLAS
 */

SET FOREIGN_KEY_CHECKS = 0;
DROP TABLE IF EXISTS KART, PISTA, USUARIO, RESERVA, BONO;
SET FOREIGN_KEY_CHECKS = 1;

/*
 * CREACION DE TABLAS
 */

-- TABLA PISTAS

CREATE TABLE PISTA(   
    nombre varchar(64) primary key,
	estado boolean NOT NULL,
	dificultad enum('INFANTIL','FAMILIAR','ADULTOS') NOT NULL,
    max_karts int(3) NOT NULL,
    asoc_karts_disp_infantiles int(3) default 0,
    asoc_karts_disp_adultos int(3) default 0

)ENGINE=InnoDB;

-- TABLA KART
/*
 * ATRIBUTOS
 * 	- Id: PRIMARY KEY. Numero que identifica un kart. Variable de tipo autincrementable.
 * 	- tipo: Boolean para el tipo de kart, niños como true y adultos como false. Atributo de tipo NOT NULL
 * 	- estado: Enum que indica el estado del kart. Atributo de tipo NOT NULL
 * 	- pista_asociada: String que contiene el nombre de la pista a la que esta asociado el kart.
 * 
 * CONSTRAINTS
 * - fk_karts_pistas: Foreign key que relaciona el atributo pista_asociada con la primary key nombre de la tabla PISTA. Solo se puede asociar un kart a una pista existente
 */
CREATE TABLE KART(   
    id int(3) PRIMARY KEY AUTO_INCREMENT,
	tipo boolean NOT NULL,
	estado enum('DISPONIBLE','RESERVADO','MANTENIMIENTO') NOT NULL,
    pista_asociada varchar(16),

	CONSTRAINT fk_karts_pistas FOREIGN KEY (pista_asociada) references PISTA(nombre)
)ENGINE=InnoDB;


-- TABLA USUARIOS
/*
 * ATRIBUTOS
 * - correo: PRIMARY KEY. String que indica el correo de una persona
 * - nombre: String que contiene el nombre de una persona. Atributo de tipo NOT NULL.
 * - apellidos: String que contiene los apellidos de una persona. Artributo de tipo NOT NULL.
 * - fecha_Nacimiento: String (formato dd/mm/yyyy) que indica la fecha de nacimiento de una persona. Atributo tipo NOT NULL.
 * - fecha_Inscripcion: String (formato dd/mm/yyyy) que indica la fecha de cuando se hizo la primera reserva. Atributo con valor DEFAULT '1/1/1900'
 */
CREATE TABLE USUARIO(
    correo varchar(64) PRIMARY KEY,
    password varchar(16) NOT NULL,
    administrador boolean NOT NULL,
    nombre varchar(32) NOT NULL,
    apellidos varchar(32) NOT NULL,
    fecha_Nacimiento varchar(16) NOT NULL,
    fecha_Inscripcion varchar(16) NOT NULL
    
)ENGINE=InnoDB;


-- TABLA BONO
/*
 * ATRIBUTOS
 * - id_Bono: PRIMARY KEY. Entero que indica la id de un bono. Atributo autoincrementable
 * - usuario: String que contiene el correo de un usuario. Atributo de tipo NOT NULL.
 * - fecha_caducidad: String (formato dd/mm/yyyy) que indica la fecha de caducidad del bono. Atributo tipo NOT NULL.
 * 
 * CONSTRAINTS
 * - fk_bono_usuario: Foreign key que relaciona el atributo usuario con la primary key correo de la tabla USUARIO. Solo permitimos usuarios registrados
 */
CREATE TABLE BONO(
	id_Bono int(3) PRIMARY KEY AUTO_INCREMENT,
	numero_sesiones int(1) NOT NULL DEFAULT 0,
	usuario varchar(64) NOT NULL,
	tipo_Bono enum('INFANTIL','FAMILIAR','ADULTOS') NOT NULL,
	fecha_caducidad date DEFAULT '1970-1-1',
	
	CONSTRAINT fk_bono_usuario foreign key (usuario) references USUARIO(correo)
)ENGINE=InnoDB;

-- TABLA RESERVAS
/*
 * ATRIBUTOS
 * - id_Reserva: PRIMARY KEY. Entero que indica el id de una reserva. Variable autoincrementable.
 * - usuario: String que contiene el correo de un ususario. Atributo de tipo NOT NULL.
 * - id_Bono: Entero que contiene el id de un bono.
 * - modalidad_reserva: Enum que indica la modalidad de la reserva. Atributo de tipo NOT NULL
 * - tipo_reserva: Enum que indica el publico de la reserva. Atributo de tipo NOT NULL
 * - participantes_infantiles: Entero que indica el numero de participantes infantiles
 * - participantes_adultos: Entero que indica el numero de participantes adultos
 * - fecha: Date que contiene la fecha de la reserva. Atributo tipo NOT NULL
 * - duracion: Entero que indica la duracion de la reserva. Atributo NOT NULL
 * - descuento: Flotante indicando el descuento segun antiguedad o si es bono. Atributo NOT NULL.
 * - precio: Flotante qur contiene el precio de la reserva. Atributo NOT NULL
 * - pista: String que indica la pista de la reserva. Atributo NOT NULL.
 * 
 * CONSTRAINTS
 * - fk_reserva_usuario: Foreign key que relaciona el atributo usuario con la PK correo de la tabla USUARIO. Para solo permitir reservas de usuarios registrados.
 * - fk_resera_pista: Foreign key que relaciona el atributo pista con la PK nombre de la tabla PISTA. Solo se pueden añadir pistas que existan.
 * - fk_reserva_bono: Foreign key que realciona el atributo id_Bono con la PK id_Bono de la table BONO. Solo se puede relacionar una reserva con un bono si este existe
 */

CREATE TABLE RESERVA(
    id_Reserva int(8) PRIMARY KEY AUTO_INCREMENT,
    usuario varchar(64) NOT NULL,
    id_Bono int(8),
    modalidad_reserva enum('INDIVIDUAL','BONO') NOT NULL,
    tipo_Reserva enum('INFANTIL','FAMILIAR','ADULTOS') NOT NULL,
    participantes_infantiles int(3),
    participantes_adultos int(3),
    fecha datetime NOT NULL,
    duracion int(3) NOT NULL,
    descuento float NOT NULL,
    precio float NOT NULL,
    pista varchar(16) NOT NULL,
	
	CONSTRAINT fk_reserva_usuario foreign key (usuario) references USUARIO(correo),
	CONSTRAINT fk_reserva_pista foreign key (pista) references PISTA(nombre),
	CONSTRAINT fk_reserva_bono foreign key (id_Bono) references BONO(id_Bono)
)ENGINE=InnoDB;


/*
* DATOS USUARIO
*/

INSERT INTO USUARIO (correo, password, administrador, nombre, apellidos, fecha_Nacimiento, fecha_Inscripcion) VALUES ('admin@admin.es', 'admin', true, 'User', 'Admin Admin', '22/12/2022', '11/11/2011');
INSERT INTO USUARIO (correo, password, administrador, nombre, apellidos, fecha_Nacimiento, fecha_Inscripcion) VALUES ('jpp@gmail.com', 'pass', false, 'Jose', 'Perez Perez', '23/02/1982', '01/01/2019');

INSERT INTO PISTA 
VALUES ('A1', 1, 'ADULTOS', 10, 0, 10); 
INSERT INTO PISTA 
VALUES ('A2', 1, 'ADULTOS', 10, 0, 5); 
INSERT INTO PISTA 
VALUES ('F1', 1, 'FAMILIAR', 20, 15, 5); 
INSERT INTO PISTA 
VALUES ('F2', 1, 'FAMILIAR', 20, 10, 10); 
INSERT INTO PISTA 
VALUES ('I1', 1, 'INFANTIL', 5, 5, 0); 
INSERT INTO PISTA 
VALUES ('I2', 1, 'INFANTIL', 10, 8, 0); 

INSERT INTO PISTA 
VALUES ('A3', 1, 'ADULTOS', 5, 0, 0); 
INSERT INTO PISTA 
VALUES ('A4', 1, 'ADULTOS', 10, 0, 0); 
INSERT INTO PISTA 
VALUES ('F3', 1, 'FAMILIAR', 15, 0, 0); 
INSERT INTO PISTA 
VALUES ('F4', 1, 'FAMILIAR', 15, 0, 0); 
INSERT INTO PISTA 
VALUES ('I3', 1, 'INFANTIL', 10, 0, 0); 
INSERT INTO PISTA 
VALUES ('I4', 1, 'INFANTIL', 10, 0, 0); 

INSERT INTO KART(tipo, estado, pista_asociada) VALUES (false,'DISPONIBLE', 'A1');
INSERT INTO KART(tipo, estado, pista_asociada) VALUES (false,'DISPONIBLE', 'A1');
INSERT INTO KART(tipo, estado, pista_asociada) VALUES (false,'DISPONIBLE', 'A1');
INSERT INTO KART(tipo, estado, pista_asociada) VALUES (false,'DISPONIBLE', 'A1');
INSERT INTO KART(tipo, estado, pista_asociada) VALUES (false,'DISPONIBLE', 'A1');
INSERT INTO KART(tipo, estado, pista_asociada) VALUES (false,'DISPONIBLE', 'A1');
INSERT INTO KART(tipo, estado, pista_asociada) VALUES (false,'DISPONIBLE', 'A1');
INSERT INTO KART(tipo, estado, pista_asociada) VALUES (false,'DISPONIBLE', 'A1');
INSERT INTO KART(tipo, estado, pista_asociada) VALUES (false,'DISPONIBLE', 'A1');
INSERT INTO KART(tipo, estado, pista_asociada) VALUES (false,'DISPONIBLE', 'A1');

INSERT INTO KART(tipo, estado, pista_asociada) VALUES (false,'DISPONIBLE', 'A2');
INSERT INTO KART(tipo, estado, pista_asociada) VALUES (false,'DISPONIBLE', 'A2');
INSERT INTO KART(tipo, estado, pista_asociada) VALUES (false,'DISPONIBLE', 'A2');
INSERT INTO KART(tipo, estado, pista_asociada) VALUES (false,'DISPONIBLE', 'A2');
INSERT INTO KART(tipo, estado, pista_asociada) VALUES (false,'DISPONIBLE', 'A2');



INSERT INTO KART(tipo, estado, pista_asociada) VALUES (true,'DISPONIBLE', 'F1');
INSERT INTO KART(tipo, estado, pista_asociada) VALUES (true,'DISPONIBLE', 'F1');
INSERT INTO KART(tipo, estado, pista_asociada) VALUES (true,'DISPONIBLE', 'F1');
INSERT INTO KART(tipo, estado, pista_asociada) VALUES (true,'DISPONIBLE', 'F1');
INSERT INTO KART(tipo, estado, pista_asociada) VALUES (true,'DISPONIBLE', 'F1');
INSERT INTO KART(tipo, estado, pista_asociada) VALUES (true,'DISPONIBLE', 'F1');
INSERT INTO KART(tipo, estado, pista_asociada) VALUES (true,'DISPONIBLE', 'F1');
INSERT INTO KART(tipo, estado, pista_asociada) VALUES (true,'DISPONIBLE', 'F1');
INSERT INTO KART(tipo, estado, pista_asociada) VALUES (true,'DISPONIBLE', 'F1');
INSERT INTO KART(tipo, estado, pista_asociada) VALUES (true,'DISPONIBLE', 'F1');
INSERT INTO KART(tipo, estado, pista_asociada) VALUES (true,'DISPONIBLE', 'F1');
INSERT INTO KART(tipo, estado, pista_asociada) VALUES (true,'DISPONIBLE', 'F1');
INSERT INTO KART(tipo, estado, pista_asociada) VALUES (true,'DISPONIBLE', 'F1');
INSERT INTO KART(tipo, estado, pista_asociada) VALUES (true,'DISPONIBLE', 'F1');
INSERT INTO KART(tipo, estado, pista_asociada) VALUES (true,'DISPONIBLE', 'F1');
INSERT INTO KART(tipo, estado, pista_asociada) VALUES (false,'DISPONIBLE', 'F1');
INSERT INTO KART(tipo, estado, pista_asociada) VALUES (false,'DISPONIBLE', 'F1');
INSERT INTO KART(tipo, estado, pista_asociada) VALUES (false,'DISPONIBLE', 'F1');
INSERT INTO KART(tipo, estado, pista_asociada) VALUES (false,'DISPONIBLE', 'F1');
INSERT INTO KART(tipo, estado, pista_asociada) VALUES (false,'DISPONIBLE', 'F1');

INSERT INTO KART(tipo, estado, pista_asociada) VALUES (true,'DISPONIBLE', 'F2');
INSERT INTO KART(tipo, estado, pista_asociada) VALUES (true,'DISPONIBLE', 'F2');
INSERT INTO KART(tipo, estado, pista_asociada) VALUES (true,'DISPONIBLE', 'F2');
INSERT INTO KART(tipo, estado, pista_asociada) VALUES (true,'DISPONIBLE', 'F2');
INSERT INTO KART(tipo, estado, pista_asociada) VALUES (true,'DISPONIBLE', 'F2');
INSERT INTO KART(tipo, estado, pista_asociada) VALUES (true,'DISPONIBLE', 'F2');
INSERT INTO KART(tipo, estado, pista_asociada) VALUES (true,'DISPONIBLE', 'F2');
INSERT INTO KART(tipo, estado, pista_asociada) VALUES (true,'DISPONIBLE', 'F2');
INSERT INTO KART(tipo, estado, pista_asociada) VALUES (true,'DISPONIBLE', 'F2');
INSERT INTO KART(tipo, estado, pista_asociada) VALUES (true,'DISPONIBLE', 'F2');
INSERT INTO KART(tipo, estado, pista_asociada) VALUES (false,'DISPONIBLE', 'F2');
INSERT INTO KART(tipo, estado, pista_asociada) VALUES (false,'DISPONIBLE', 'F2');
INSERT INTO KART(tipo, estado, pista_asociada) VALUES (false,'DISPONIBLE', 'F2');
INSERT INTO KART(tipo, estado, pista_asociada) VALUES (false,'DISPONIBLE', 'F2');
INSERT INTO KART(tipo, estado, pista_asociada) VALUES (false,'DISPONIBLE', 'F2');
INSERT INTO KART(tipo, estado, pista_asociada) VALUES (false,'DISPONIBLE', 'F2');
INSERT INTO KART(tipo, estado, pista_asociada) VALUES (false,'DISPONIBLE', 'F2');
INSERT INTO KART(tipo, estado, pista_asociada) VALUES (false,'DISPONIBLE', 'F2');
INSERT INTO KART(tipo, estado, pista_asociada) VALUES (false,'DISPONIBLE', 'F2');
INSERT INTO KART(tipo, estado, pista_asociada) VALUES (false,'DISPONIBLE', 'F2');



INSERT INTO KART(tipo, estado, pista_asociada) VALUES (true,'DISPONIBLE', 'I1');
INSERT INTO KART(tipo, estado, pista_asociada) VALUES (true,'DISPONIBLE', 'I1');
INSERT INTO KART(tipo, estado, pista_asociada) VALUES (true,'DISPONIBLE', 'I1');
INSERT INTO KART(tipo, estado, pista_asociada) VALUES (true,'DISPONIBLE', 'I1');
INSERT INTO KART(tipo, estado, pista_asociada) VALUES (true,'DISPONIBLE', 'I1');

INSERT INTO KART(tipo, estado, pista_asociada) VALUES (true,'DISPONIBLE', 'I2');
INSERT INTO KART(tipo, estado, pista_asociada) VALUES (true,'DISPONIBLE', 'I2');
INSERT INTO KART(tipo, estado, pista_asociada) VALUES (true,'DISPONIBLE', 'I2');
INSERT INTO KART(tipo, estado, pista_asociada) VALUES (true,'DISPONIBLE', 'I2');
INSERT INTO KART(tipo, estado, pista_asociada) VALUES (true,'DISPONIBLE', 'I2');
INSERT INTO KART(tipo, estado, pista_asociada) VALUES (true,'DISPONIBLE', 'I2');
INSERT INTO KART(tipo, estado, pista_asociada) VALUES (true,'DISPONIBLE', 'I2');
INSERT INTO KART(tipo, estado, pista_asociada) VALUES (true,'DISPONIBLE', 'I2');


INSERT INTO KART(tipo, estado) VALUES (true,'DISPONIBLE');
INSERT INTO KART(tipo, estado) VALUES (true,'DISPONIBLE');
INSERT INTO KART(tipo, estado) VALUES (true,'DISPONIBLE');
INSERT INTO KART(tipo, estado) VALUES (true,'DISPONIBLE');
INSERT INTO KART(tipo, estado) VALUES (true,'DISPONIBLE');
INSERT INTO KART(tipo, estado) VALUES (true,'DISPONIBLE');
INSERT INTO KART(tipo, estado) VALUES (true,'DISPONIBLE');
INSERT INTO KART(tipo, estado) VALUES (true,'DISPONIBLE');
INSERT INTO KART(tipo, estado) VALUES (true,'DISPONIBLE');
INSERT INTO KART(tipo, estado) VALUES (true,'DISPONIBLE');
INSERT INTO KART(tipo, estado) VALUES (false,'DISPONIBLE');
INSERT INTO KART(tipo, estado) VALUES (false,'DISPONIBLE');
INSERT INTO KART(tipo, estado) VALUES (false,'DISPONIBLE');
INSERT INTO KART(tipo, estado) VALUES (false,'DISPONIBLE');
INSERT INTO KART(tipo, estado) VALUES (false,'DISPONIBLE');
INSERT INTO KART(tipo, estado) VALUES (false,'DISPONIBLE');
INSERT INTO KART(tipo, estado) VALUES (false,'DISPONIBLE');
INSERT INTO KART(tipo, estado) VALUES (false,'DISPONIBLE');
INSERT INTO KART(tipo, estado) VALUES (false,'DISPONIBLE');
INSERT INTO KART(tipo, estado) VALUES (false,'DISPONIBLE');










