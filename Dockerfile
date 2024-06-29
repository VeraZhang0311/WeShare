FROM maslick/minimalka:jdk11
ADD ./target/carpool-0.0.1-SNAPSHOT.jar /carpool-0.0.1-SNAPSHOT.jar
EXPOSE 8086
ENTRYPOINT ["java","-jar","/carpool-0.0.1-SNAPSHOT.jar"]
