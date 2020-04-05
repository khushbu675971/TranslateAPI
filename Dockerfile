FROM tomcat:9.0.33-jdk8-openjdk-slim

ADD /target/translate-api-*.war /usr/local/tomcat/webapps/translate-api.war

ADD google-service-account.json .

ENV GOOGLE_APPLICATION_CREDENTIALS google-service-account.json

CMD ["catalina.sh", "run"]