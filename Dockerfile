FROM ubuntu:latest

RUN apt-get -y update
RUN apt-get -y install default-jre wget

# Copy in the jar
COPY target/* /

WORKDIR /data
VOLUME /data

EXPOSE 25565

CMD java -jar /HoseCraft.jar