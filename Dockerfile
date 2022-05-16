FROM alpine:latest AS BUILDER

RUN apk update
RUN apk upgrade --no-cache
RUN apk add --no-cache openjdk17

COPY . /app/
WORKDIR /app/
RUN ./gradlew clean jar

RUN jdeps \
    --ignore-missing-deps \
    -q \
    --multi-release 17 \
    --print-module-deps \
    --class-path ./build/lib/* \
    ./build/libs/* > jre-deps.info

RUN jlink --verbose \
    --compress 2 \
    --strip-java-debug-attributes \
    --no-header-files \
    --no-man-pages \
    --output ./build/jre \
    --add-modules $(cat jre-deps.info)

FROM alpine:latest AS RUNNER

WORKDIR /app
COPY --from=BUILDER /app/build/jre/ jre/
COPY --from=BUILDER /app/build/lib/* lib/
COPY --from=BUILDER /app/build/libs/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ['jre/bin/java', '-jar', 'app.jar']