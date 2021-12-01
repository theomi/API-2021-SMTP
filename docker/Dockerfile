FROM openjdk:11

EXPOSE 8282
EXPOSE 25

COPY mockmock/ /usr/src/mockmock

WORKDIR /usr/src/mockmock

CMD ["java", "-jar", "MockMock-1.4.0.one-jar.jar"]