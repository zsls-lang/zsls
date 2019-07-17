FROM java:8
EXPOSE 8080
VOLUME /tmp
ADD chapter25-0.0.1-SNAPSHOT.jar mytestapp.jar
RUN sh -c 'touch /mytestapp.jar'
ENV JAVA_OPTS=""
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /mytestapp.jar" ]