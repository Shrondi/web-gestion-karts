# Usar una imagen base de Tomcat
FROM tomcat:8.5-jdk17-openjdk

# Copiar el archivo WAR al directorio webapps de Tomcat
COPY target/web-gestion-karts.war /usr/local/tomcat/webapps/

# Exponer el puerto que usará tu aplicación
EXPOSE 8080

# Comando para iniciar Tomcat
CMD ["catalina.sh", "run"]
