# Herramientas

## Git

En Windows usamos [git bash](https://git-scm.com/downloads).

En Linux se puede hacer `sudo apt-get install git`.

En MacOs se puede hacer `brew install git`.


## Docker

Se puede descargar desde [la web de docker](https://www.docker.com/products/docker-desktop/).

### Compilar y Testear con Docker

Estos comandos deberían funcionar siempre y a todos los desarrolladores:

`docker run -itv ${PWD}:/usr/src/app -w /usr/src/app maven:3.8.6-openjdk-11 ./back-end/build.sh`

`docker run -itv ${PWD}:/usr/src/app -w /usr/src/app maven:3.8.6-openjdk-11 ./back-end/run-tests.sh`


## Cliente Postgres

Se puede usar el que tengan, sugerimos [DBeaver](https://dbeaver.io/download/) o [DbVisualizer](https://www.dbvis.com/download/).

Otra alternativa es [Datagrip](https://www.jetbrains.com/datagrip/download/).

## Postman

Descargar desde [la web de postman](https://www.postman.com/downloads/).

### Probar endpoint publico con curl

curl 'https://echo.free.beeceptor.com'


## Java / Maven

> No instalar una versión mas nueva si ya tienen una

En Windows [descargar Java JDK](https://www.oracle.com/java/technologies/downloads/?er=221886) y [Descargar Maven](https://maven.apache.org/download.cgi).

En Linux `sudo apt install openjdk-11-jdk` y `sudo apt install maven`.

En MacOs `brew install openjdk` y `brew install maven`.

Verificar versión:

1. Java `java -version` >> 11
2. Java compiler `javac -version` >> javac 11
3. Maven `mvn -version` >> Apache Maven 3.8.6
4. Generar jar `mvn clean package`
5. Ejecutar jar `java -jar target/mensajes-1.0-SNAPSHOT.jar`

## IntelliJ IDEA Community Edition

1. Descargar desde [la web de jetbrains](https://www.jetbrains.com/idea/download/)
2. Ejecutar `Run` sobre la clase principal.
3. Agregar `-Dspring.profiles.active=dev` en el campo **"VM Options"** del Run Configuration.


### Compilar y Testear localmente

Estos comandos deberían funcionar siempre y a todos los desarrolladores:

`./back-end/build.sh`

`./back-end/run-tests.sh`

### Iniciar localmente con docker

Iniciar postgres

`docker compose up bootcamp-db`

Iniciar backend

`docker compose up --build bootcamp-backend`

