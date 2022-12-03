GITHUB
-------------
https://github.com/ssCuacKss/WebProyectoPW

* Subir siempre los cambios despues de terminar de trabajar cada dia. Usad la web o terminal:
	git add . (añade todos los ficheros que habeis cambiado)
	git commit -m "Mensaje" (realiza un commit poniendo un mensaje)
	git push -u origin main (Subir los cambios)

* Bajar SIEMPRE los cambios ANTES de ponerse a trabajar. Usad web o terminal:
	git pull origin main
	
MEMORIA
-------------
https://docs.google.com/document/d/1P4qKDG4Ods3_mFkFS1dXrS1Qt5z1rNUrDlRDhby5Gvo/edit?usp=sharing

* Completar memoria
* Mapa de navegacion web
* IMPORTANTE, realizar el informe de autoevaluacion (se encuentra en moodle). Se entrega el mismo dia que la practica

COMO ORGANIZARSE
-------------
La idea es hacer las tareas que quedan descritas en las siguientes fechas:

1) Sabado 3/12 hasta el Domingo 11/12: Dejar ejercicio 1 y servlets terminados
2) Domingo 11/12 hasta el Jueves 15/12: Dias de margen para terminar
3) Viernes 16/12 hasta Domingo 18/12: Terminar memoria, hacer informes de autoevaluacion, testear, arreglar bugs


A HACER EN GENERAL
-------------

1) Desarrollar front-end empleando HTML y CSS
2) Utilizar JavaScript para la validacion de formularios
3) Uso de JavaScript para la paginacion de resultados de reservas y pistas
4) Redirigir a las paginas de error correctas


EJERCICIO 1 -- [¿Quien lo realiza?]
-------------
Falta añadir/modificar/arreglar las siguientes funcionalidades en el index.jsp respecto a los dos tipos de usuario.

En principio, los contraladores y los display funcionan correctamente, solo seria añadir los siguientes cambios al index.jsp

El cambio mas significativo seria usar JavaScript para validar los formularios de los display y uso de CSS para dejar tanto
los display e index presentables.

	 1) Cliente
		-> Mensaje de bienvenida
		-> Fecha actual
		-> Antiguedad
		-> Fecha de proxima reserva (si tiene alguna)
		-> Mostrar opcion "Modificar datos"
			* Mostrar cada uno de los campos y solo permitir cambiar aquellos permitidos
		-> Mostrar opcion "Cerrar Sesion"

	 2) Administrador
		-> Mensaje de bienvenida o fecha actual 
		   * (No dice nada explicito de implementarlo pero es un añadido extra sencillo)
		-> Mostrar opcion "Registrar usuario administrador"
			* Segun lo que se quedo dicho en clase por el profesor, tenemos desde el principio 
			  un administrador introducido manualmente en la BD. 
			  Luego, un admin desde la aplicacion puede registrar otros administradores. 
			  (el controlador registro y el display estan adaptados para contemplar tal situacion y no requiere cambios)
		-> Mostrar opcion "Modificar datos"
		-> Mostrar opcion "Cerrar Sesion"
 	
EJERCICIO 2
--------------
Implementar la consulta y gestion de kart, pistas y reservas controlando el acceso a las funcionalidades segun el tipo de usuario usando servlets y las vistas necesarias

Se debe usar tantas vistas como sean necesarias (utilizando JavaScript para validar formularios y CSS).
Las nuevas opciones a añadir deben mostrarse junto a las opciones del ejericio 1 (en este caso van al index)
(sigo insistiendo en la utilidad de mantener las dos vistas de cliente y admin separadas y no tenerlo todo junto en el index)

Manteniendo la misma logica que la practica anterior recuerdo: 

* Usar los DAOS que tengamos disponibles, modificar aquellos que os vengan bien o crear nuevos si lo veis muy necesario

* Recordad que se hablo que una pista tenemos numero maximo de karts y un numero asociado de karts, es decir,
una pista puede tener 20 karts maximos pero el admin puede decidir asociar tanto karts que quiera sin pasarse de ese limite y por tanto para una reserva solo habra esos karts asociados disponibles (ademas, hay que tener en cuenta otras restricciones para que se lleve acabo la asociacion)

* Respecto a las reservas, consultar que hacer una reserva conlleva una serie de restricciones  (numero de karts de cada tipo por pista, numero, tipo, etc) y al cancelar una reserva se desmarcan los karts como reservados.

		1) Cliente
			-> Consultar reservas entre una fecha de inicio y fin. -- [¿Quien lo realiza?]
				Diferenciar entre reservas finalizadas o futuras
			-> Buscar una pista disponible segun uno de los siguientes criterios: -- [¿Quien lo realiza?]
				Tipo de pistas, fecha o numero minimo de karts disponibles
			-> Realizar reserva individual para una fecha dada, del tipo solicitado y tenga los suficientes karts -- [Carlos]
			-> Realizar una reserva bono [Carlos]
			-> Modificar una reserva (mirar restricciones del enunciado y practicas anteriores) -- [Carlos]
			-> Cancelar una reserva (mirar restricciones del enunciado y practicas anteriores) -- [Carlos]

		2) Administrador
			-> Dar de alta pistas -- [¿Quien lo realiza?]
			-> Dar de alta karts -- [¿Quien lo realiza?]
			-> Asociar kart a pista (mirar restricciones practicas anteriores) -- [¿Quien lo realiza?]
			-> Modificar el estado de karts y pistas -- [¿Quien lo realiza?]
			-> Eliminar reservas -- [Carlos]


