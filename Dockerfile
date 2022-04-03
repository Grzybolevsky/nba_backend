FROM alpine:latest

ENV TZ=Europe/Warsaw

RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

ENV LC_ALL=C.UTF-8
ENV LANG=C.UTF-8

RUN apk update
RUN apk upgrade --no-cache
RUN apk add --no-cache openjdk17 gnupg curl wget unzip vim

EXPOSE 8080

COPY . /app/api/
WORKDIR /app/api/

RUN ./gradlew clean build

ENTRYPOINT ["./gradlew", "run"]