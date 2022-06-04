FROM gradle:7.4.2-jdk18-jammy AS BUILDER

COPY . /app/
WORKDIR /app/
RUN gradle clean jar && \
    jdeps \
        --ignore-missing-deps \
        -q \
        --multi-release 18 \
        --print-module-deps \
        --class-path ./build/lib/* \
        ./build/libs/* > jre-deps.info && \
    jlink --verbose \
        --compress 2 \
        --strip-java-debug-attributes \
        --no-header-files \
        --no-man-pages \
        --output ./build/jre \
        --add-modules $(cat jre-deps.info) \
    && \
    mkdir /out && \
    mv ./build/jre /out/ && \
    mv ./build/lib /out/ && \
    mv ./build/libs/*.jar /out/app.jar

FROM ubuntu:latest AS RUNNER

COPY --from=BUILDER /out /app

EXPOSE 8080
ENTRYPOINT ["/app/jre/bin/java", "-jar", "/app/app.jar"]