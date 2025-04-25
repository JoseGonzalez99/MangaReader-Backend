# Usa una imagen oficial de Java 17 para correr el app
FROM eclipse-temurin:17-jdk-alpine

# Directorio de trabajo dentro del contenedor
WORKDIR /app

# Copiar el jar compilado
COPY target/jaity-manga-reader.jar app.jar

# Exponer el puerto (Render suele esperar algo en el 8080)
EXPOSE 8080

# Comando para correr el jar
ENTRYPOINT ["java", "-jar", "app.jar"]
