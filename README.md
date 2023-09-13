# movies-actors

## Using docker commands to containerize application

First the application was packaged using maven command in project root folder
```shell script
./mvnw package
```

Then from project root command following commands were used
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

