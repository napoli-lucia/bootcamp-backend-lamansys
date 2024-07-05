# Herramientas

## Git

En Windows usamos [git bash](https://git-scm.com/downloads).

En Linux se puede hacer `sudo apt-get install git`.

En MacOs se puede hacer `brew install git`.


## Docker

Se puede descargar desde [la web de docker](https://www.docker.com/products/docker-desktop/).

## Cliente Postgres

Se puede usar el que tengan, sugerimos [DBeaver](https://dbeaver.io/download/) o [DbVisualizer](https://www.dbvis.com/download/).

## Postman

Descargar desde [la web de postman](https://www.postman.com/downloads/).

## Java / Maven

> No instalar una versión mas nueva si ya tienen una

En Windows [descargar Java JDK](https://www.oracle.com/java/technologies/downloads/?er=221886) y [Descargar Maven](https://maven.apache.org/download.cgi).

En Linux `sudo apt install openjdk-11-jdk` y `sudo apt install maven`.

En MacOs `brew install openjdk` y `brew install maven`.

Verificar versión:

1. Java `java -version` >> 11
2. Java compiler `javac -version` >> javac 11
3. Maven `mvn -version` >> Apache Maven 3.8.6

## IntelliJ IDEA Community Edition

Descargar desde [la web de jetbrains](https://www.jetbrains.com/idea/download/)

# Compilar y Testear

Estos comandos deberían funcionar siempre y a todos los desarrolladores:

`docker run -itv ${PWD}:/usr/src/app -w /usr/src/app maven:3.8.6-openjdk-11 ./back-end/build.sh`

`docker run -itv ${PWD}:/usr/src/app -w /usr/src/app maven:3.8.6-openjdk-11 ./back-end/run-tests.sh`
