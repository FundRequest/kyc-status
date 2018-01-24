FROM frolvlad/alpine-oraclejdk8:slim

VOLUME /tmp
ADD build/libs/tokensale-status-0.0.1-SNAPSHOT.jar kycstatus.jar
RUN sh -c 'touch /kycstatus.jar' && \
    mkdir config

ENV JAVA_OPTS=""

EXPOSE 8080

ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /kycstatus.jar" ]
