FROM ubuntu:latest

RUN apt-get -y update
RUN apt-get -y install default-jre

# Copy in the jar
RUN mkdir /hosecraft
COPY target/*.jar /hosecraft/

WORKDIR /data
VOLUME /data

EXPOSE 25565

CMD java -jar /hosecraft/HoseCraft.jar