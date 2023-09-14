# Backend Exercise 
Projct directory:  src\main\resources\postman contains the postman collection, with all the endpoints present, that was used during development.

## Using docker to containerize the application
I have  containerized the service, both by running docker commands separately, as well as using docker compose.

Before running, the application was packaged using maven command in project root folder
```shell script
./mvnw package
```
## Using docker commands to containerize application

From project root command following commands were used
1.) Created  a custom docker image
```shell script
docker build -f src/main/docker/Dockerfile.jvm -t quarkus/movies-actors-jvm .
```

2.) Used command to verify that image is present locally
```shell script
docker image ls
```
```console
REPOSITORY                  TAG       IMAGE ID       CREATED         SIZE
quarkus/movies-actors-jvm   latest    ac8648d7f0b6   2 minutes ago   448MB
```

3.) Created and started docker container with custom name in detached mode, with such port mapping, so that service is present at port 8080:
```shell script
docker run --name movies-actors  -i -d --rm -p  8080:8080 quarkus/movies-actors-jvm
```

4.) Checked than container is running by listing running containers:
```shell script
docker ps
```
```console
CONTAINER ID   IMAGE                       COMMAND                  CREATED         
8e83e819fd2c   quarkus/movies-actors-jvm   "/opt/jboss/containe…"   6 seconds ago  
STATUS         PORTS                              NAMES
 Up 5 seconds  0.0.0.0:8080->8080/tcp, 8443/tcp   movies-actors
```

5.) Used postman to test that application endpoints work correctly.

## Using docker compose to containerize the application

1.) First I removed the old image from before to check that image will be correctly built using docker compose:

```shell script
docker image rm quarkus/movies-actors-jvm
```
2.) Run docker compose from directory where docker-compose.yml is located in detached mode

```shell script
docker-compose up -d
```
3.) Checked than container is running by listing running containers:


```shell script
docker ps
```

```console
docker ps
CONTAINER ID   IMAGE                       COMMAND                  CREATED
83a1cea51387   quarkus/movies-actors-jvm   "/opt/jboss/containe…"   10 seconds ago  
STATUS         PORTS                              NAMES
Up 9 seconds   0.0.0.0:8080->8080/tcp, 8443/tcp   movies-actors
```


4.) Used postman again to test that application endpoints work correctly.

