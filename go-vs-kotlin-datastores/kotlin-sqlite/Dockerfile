FROM ghcr.io/graalvm/graalvm-ce:latest as graalvm
RUN gu install native-image

ENTRYPOINT ["native-image"]
