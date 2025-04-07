FROM eclipse-temurin:23-jdk AS builder

# Install Maven manually
RUN apt-get update && \
    apt-get install -y curl unzip && \
    curl -fsSL https://downloads.apache.org/maven/maven-3/3.9.6/binaries/apache-maven-3.9.6-bin.zip -o maven.zip && \
    unzip maven.zip -d /opt && \
    ln -s /opt/apache-maven-3.9.6/bin/mvn /usr/bin/mvn


WORKDIR /build
COPY . .
RUN mvn clean package -DskipTests


# ------ Stage 2: Run ------

FROM openjdk:23
WORKDIR /app

COPY --from=builder /build/target/Wahak-0.0.1-SNAPSHOT.jar /app/Wahak-0.0.1-SNAPSHOT.jar



#COPY target/Wahak-0.0.1-SNAPSHOT.jar /app/Wahak-0.0.1-SNAPSHOT.jar
EXPOSE 8080

ENTRYPOINT ["java", "-jar", "Wahak-0.0.1-SNAPSHOT.jar","--spring.profiles.active=dev" ,">","output.txt"]

# docker build -t docker .
