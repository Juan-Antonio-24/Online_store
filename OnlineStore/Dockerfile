# Usar una imagen base con JDK para construir el proyecto
FROM eclipse-temurin:17-jdk-jammy AS builder

WORKDIR /app

# Copiar todos los archivos necesarios
COPY . .

# Ejecutar Maven para empaquetar la aplicación
RUN ./mvnw clean package -DskipTests

# Usar una imagen base con JRE para ejecutar la aplicación
FROM eclipse-temurin:17-jre-jammy AS final

WORKDIR /app

# Copiar el archivo JAR desde el contenedor de construcción
COPY --from=builder /app/target/OnlineStore-0.0.1-SNAPSHOT.jar /app/app.jar

# Exponer el puerto 8080
EXPOSE 8080

# Ejecutar la aplicación Spring Boot
ENTRYPOINT ["java", "-Duser.timezone=GMT", "-jar", "/app/app.jar"]
