# Translation API using jersey

### Project description:

It is to provide an API for translating the text defined in source language to target language. This project uses google translate api.

### Requirements:

Create REST API endpoint: 

- On path api/v1/translate
- Having parameters: 
	- sourcelang
	- targetlang
	- text
- Forward requests to one of the translation API services Google translate and respond with the translated text
- Uses invocation cache/translation history to decrease necessary network traffic
- Uses J2EE concepts and not using any Spring framework library
- Create a bash client to the API (invoking curl)
- Build application using Maven
- Using Docker that uses latest version of Java and Jetty or Tomcat

### Advanced features:

- Create Swagger API
- Enable requests from a list of specified origins
- Load the list from a property file


### Prerequisites:

- Java 1.8 and above, Eclipse, tomcat.
- Google service account key which can be created using [Google Translate Setup](https://cloud.google.com/translate/docs/advanced/setup-advanced)
- Docker
- Update google cloud ```<project id>``` in ```config.properties``` on these file paths:
    - src/main/resources/config.properties
    - src/test/resources/config.properties
- Set environment variable
```
GOOGLE_APPLICATION_CREDENTIALS=/path/to/google-service-account.json
```

### Run the project using tomcat:

- Make sure to be in the root directory
- Clean and build the project, run the command:
```
mvn clean install
```
- Deploy the war to tomcat web server 
- Start tomcat server.


### Run the project using docker:

- Copy google service account json file in root folder which should named as ```google-service-account.json```
- Create docker image:
```
docker build -t translate-api .
```

- Run docker image:
```
docker run --rm -it -p 8080:8080 translate-api
```

### API Documentaion using Swagger UI

```
http://localhost:8080/translate-api/
```

### Test client from Linux command line
```
translate.sh -from-lang en-US -to-lang cs-CZ -text "Hello world"
```

### Tests
Tests can be executed through Maven with the following instruction:
```
mvn test
```

### Note
- You can set specified origins list in ```config.properties``` using ```origins``` key.

