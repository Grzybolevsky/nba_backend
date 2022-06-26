FROM openjdk:19-slim-buster AS BUILDER

COPY ./build/ /app/
WORKDIR /app/
RUN jdeps --ignore-missing-deps \
        -q \
        --multi-release 17 \
        --print-module-deps \
        --class-path ./lib/* \
        ./libs/* > jre-deps.info && \
    jlink --verbose \
        --compress 2 \
        --strip-java-debug-attributes \
        --no-header-files \
        --no-man-pages \
        --output ./jre \
        --add-modules $(cat jre-deps.info) \
    && \
    mkdir /out && \
    mv ./jre /out/ && \
    mv ./lib /out/ && \
    mv ./libs/*.jar /out/app.jar

FROM ubuntu:latest AS RUNNER

RUN apt update && apt upgrade -y && apt install -y gnupg curl wget unzip
ENV TZ=Europe/Warsaw

RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

ENV LC_ALL=C.UTF-8
ENV LANG=C.UTF-8
COPY --from=BUILDER /out /app

EXPOSE 8080
ENTRYPOINT ["/app/jre/bin/java", "-jar", "/app/app.jar"]