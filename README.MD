# This is a simple docker Spring Boot app

## How to run

### Install Docker first

Download Docker from [here](https://www.docker.com/)

Windows 7 please use Docker Toolbox

### Run from command line

$ ./gradlew build buildDocker

docker run -p 8080:8080 -t au.com.jiangren/spring-boot-microservice:1.0-SNAPSHOT

If you are using Intellij, you can install Docker Integration plugin and run

### Test coverage
[![codecov](https://codecov.io/gh/YixiuJiang/springboot-microservice/branch/master/graph/badge.svg)](https://codecov.io/gh/YixiuJiang/springboot-microservice)

