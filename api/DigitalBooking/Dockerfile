# Usa una imagen base de Java
FROM openjdk:17-jdk-alpine

# Establece el directorio de trabajo dentro del contenedor
WORKDIR /app

# Copia los archivos de compilación (JAR) y los recursos necesarios al contenedor
COPY build/libs/*.jar app.jar

# Expone el puerto en el que escucha tu aplicación (ajústalo según tus necesidades)
EXPOSE 8000

# Comando para ejecutar tu aplicación cuando el contenedor se inicie
CMD ["java", "-jar", "app.jar"]

# Añade los comandos necesarios para configurar Fly.io
RUN apk add --no-cache ca-certificates curl \
    && curl -fsSL https://fly.io/install.sh | sh
RUN flyctl auth login
RUN flyctl regions set  Bogotá, Colombia
RUN flyctl apps create piprueba --image jcaicer/backend-app
