# Gestión de Circuitos de Karts

Aplicación web para la gestión y centralización de distintos tipos y modalidades de reservas de karts y pistas.

## Autores
- Carlos Lucena Robles - f92luroc@uco.es
- Miguel Raigón Jiménez - i92rajim@uco.es
- Kamal Abdelkader Haddu - i02abhak@uco.es
- Pablo Roldán Puebla - i92ropup@uco.es
- Paloma Romero Delgado - i92rodep@uco.es

## Información del Proyecto
- **Asignatura**: Programación Web
- **Curso**: 2022 - 2023
- **Universidad**: Universidad de Córdoba
- **Grado**: 3º de Ingeniería Informática

## Pila de Tecnología

### Back-end
- **Lenguaje**: Java
- **Base de datos**: MySQL
- **Servidor**: Apache Tomcat

### Front-end
- **HTML**
- **CSS**
- **JavaScript**

## Conceptos clave

*   **Patrones de Diseño:**
    *   **Factoría:** Se utiliza este patrón para la creación de los diferentes tipos de reservas (infantil, familiar, adulto, individual y bono). 
    *   **MVC (Modelo Vista Controlador):** Se utiliza este patrón para organizar la arquitectura de la aplicación. Separa la lógica de negocio (modelo), la interfaz de usuario (vista) y el control de flujo de la aplicación (controlador).

*   **Interacción con la Base de Datos:**
    *   **JDBC:**  Se utiliza la API JDBC (Java Database Connectivity) para conectar con la base de datos MySQL y ejecutar consultas.
    *   **DAOs (Data Access Objects):** Se implementan DAOs para encapsular la lógica de acceso a los datos.  Estos objetos actúan como intermediarios entre la aplicación y la base de datos, proporcionando una interfaz para interactuar con los datos. Se han creado DAOs específicos para cada tipo de entidad del sistema (karts, pistas, usuarios, reservas). 
    *   **DTOs (Data Transfer Objects):**  Se utilizan DTOs para la transferencia de información entre la base de datos y la aplicación.

*   **Desarrollo Front-End:**
    *   **HTML, CSS y JavaScript:** Se utilizan estos lenguajes para crear la interfaz de usuario de la aplicación web. JavaScript se utiliza para la validación de formularios, la paginación de resultados, y para controlar algunos aspectos de la lógica de la aplicación en el lado del cliente.
    *   **JSP (JavaServer Pages):** Se utiliza JSP para implementar las vistas de la aplicación web.
    *   **Servlets:** Se implementan servlets para gestionar las peticiones del usuario y la lógica de control de la aplicación web.

*   **Gestión de Usuarios y Sesiones:** La aplicación implementa mecanismos para registrar, acceder, modificar datos y desconectar usuarios.  También gestiona las sesiones de usuario para mantener el estado de la aplicación.

*   **Control de Acceso (ACL):** Se implementa un mecanismo de control de acceso (ACL) para restringir el acceso a las funcionalidades según el rol del usuario (administrador o cliente).

*   **Buenas Prácticas de Programación:** Se siguen las buenas prácticas de programación a lo largo del desarrollo del proyecto. Esto incluye el uso de nombres significativos para variables, paquetes y clases, la documentación del código utilizando Javadoc, y la organización del código en paquetes y clases. 

*   **Gestión de Errores:** Se han implementado controles de errores para validar los datos introducidos por los usuarios.

*   **Gestión de equipo:** Se utilizó la herramienta Jira Software para mejorar la planificación del proyecto y la gestión de los errores detectados durante el desarrollo.


# Descripción

Producto final, realizado a lo largo de tres prácticas, centrado en el desarrollo de una aplicación web para gestionar un circuito de karts juto a sus reservas. La aplicación web ofrece distintas funcionalidades para los administradores y usuarios habituales:

## Funcionalidades para Administradores:

*   **Gestión de Karts:**
    *   **Alta de Karts:** Permite registrar nuevos karts en el sistema. Los administradores deben introducir información sobre el tipo de kart (infantil o adulto) y su estado inicial (disponible, reservado o en mantenimiento).
    *   **Modificación del Estado de los Karts:** Permite a los administradores actualizar el estado de los karts. Se pueden registrar cambios de estado de disponible a reservado, de reservado a mantenimiento, de reservado a disponible o de mantenimiento a disponible. El sistema permite la modificación del estado de varios karts a la vez, siempre que el cambio de estado sea el mismo para todos. Es importante destacar que al cambiar el estado de un kart de reservado a disponible o a mantenimiento, este se retira de la reserva en la que se encontraba, y la pista asociada al kart pasa a ser nula.

*   **Gestión de Pistas:**
    *   **Alta de Pistas:** Los administradores pueden crear nuevas pistas en el sistema. La información que deben introducir incluye el nombre de la pista, su estado inicial (disponible o no disponible), la dificultad (infantil, familiar o adultos) y el número máximo de karts que pueden circular en la pista simultáneamente. Se ha establecido un mínimo de 5 karts por pista.
    *   **Asociación de Karts a Pistas:** Permite a los administradores asignar karts disponibles a pistas específicas. El sistema controla que los karts a asignar no estén ya asignados a otras pistas o en mantenimiento. La asociación debe cumplir las restricciones de tipo de kart (infantil o adulto) y el número máximo de karts permitidos en la pista. Por ejemplo, en una pista infantil solo se pueden añadir karts para niños.
    *   **Modificación del Estado de las Pistas:** Los administradores pueden cambiar el estado de una pista a disponible o no disponible. Este cambio no afecta las reservas ya existentes. El sistema permite modificar el estado de varias pistas a la vez, siempre y cuando el cambio sea el mismo para todas.

*   **Gestión de Reservas:**
    *   **Eliminación de Reservas:** Permite a los administradores eliminar reservas que aún no se han realizado. Al eliminar una reserva, los recursos (karts y pista) asociados a dicha reserva se liberan. 

*   **Gestión de Usuarios:**
    *   **Registro de Otros Administradores:**  Los administradores solo pueden registrar a otros usuarios con el rol de administrador. El sistema cuenta con un administrador inicial creado manualmente en la base de datos.

## Funcionalidades para Clientes:

*   **Gestión de Reservas:**
    *   **Consulta de Reservas:** Los clientes pueden consultar un listado de las reservas que han realizado, diferenciando entre las que ya se han realizado (finalizadas) y las que están programadas para el futuro (futuras). La tabla que muestra las reservas utiliza diferentes colores de fondo para distinguir las finalizadas de las futuras.
    *   **Realización de una Reserva Individual:** Permite a los clientes reservar una pista disponible para una fecha y hora específicas.  Se deben seleccionar el tipo de pista y la duración de la reserva (60, 90 o 120 minutos). El sistema aplica descuentos a los usuarios con más de 2 años de antigüedad.  Existen restricciones en la fecha de la reserva: no puede ser el mismo día en que se realiza, y no se permite reservar a menos de 2 horas de la última reserva realizada para la misma fecha. El número máximo de participantes por reserva es de 20.
    *   **Adquisición de un Bono de Reservas:** Permite a los clientes comprar un bono de 5 sesiones a un precio reducido. Las sesiones del bono pueden ser de cualquier tipo (infantil, familiar o adulto). No se aplica el descuento por antigüedad a las reservas asociadas a un bono.  Para eliminar un bono es necesario eliminar primero todas las reservas asociadas al mismo.
    *   **Asociación de Nuevas Reservas al Bono:** Los clientes pueden utilizar las sesiones de su bono para realizar reservas. El sistema controla que la fecha de la reserva esté dentro del periodo de validez del bono, que es de un año desde la fecha de la primera reserva.
    *   **Cancelación o Modificación de Reservas:** Los clientes pueden cancelar o modificar las reservas que aún no se han realizado, siempre y cuando falten al menos 24 horas para su inicio. Se pueden modificar el número de participantes, el tipo de pista (solo para reservas individuales), la duración y la fecha de la reserva.

*   **Búsqueda de Pistas:**
    *   **Búsqueda de Pistas Disponibles:** Permite a los clientes buscar pistas disponibles según diferentes criterios: tipo de pista, número mínimo de karts disponibles, o ambos.

## Funcionalidades comunes (Administradores y Clientes):

*   **Gestión de Usuarios:**
    *   **Registro:** Permite a los usuarios crear una nueva cuenta en el sistema. El sistema implementa validaciones para asegurar que el correo electrónico introducido no esté duplicado y que el usuario sea mayor de edad.
    *   **Acceso:** Permite a los usuarios iniciar sesión con una cuenta existente. El sistema verifica las credenciales del usuario en la base de datos. La aplicación distingue entre administradores y clientes utilizando un atributo específico en la base de datos.
    *   **Modificación de Datos:** Permite a los usuarios actualizar la información de su cuenta.  Existen restricciones en la modificación de ciertos campos, como el correo electrónico y el tipo de usuario. El sistema utiliza el campo de la contraseña para confirmar la modificación de datos o para establecer una nueva contraseña.
    *   **Desconexión:** Permite a los usuarios cerrar la sesión actual.





