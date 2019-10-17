Pet Service API
====================

This is a REST API that handles a database of pets. It is written in Java using the Spring Boot framework, utilizing Apache Derby for the in-memory database and Spring Data JPA to access the data. 

## Requirements
* Java version 1.8
* JDK 12+
* Maven

## Building and Running
1. Using a terminal, clone the repo (`git clone https://github.com/Nanjaa/PetService.git`)
2. Navigate into the project folder
3. Run `mvn clean install` to run tests and build the project
4. Once maven has completed building the project, run `java -jar target\pets-0.0.1-SNAPSHOT.jar` if on Windows, or `java -jar target\pets-0.0.1-SNAPSHOT.jar` if on Mac/Linux
5. You'll know the app is successfully running when you see `...Tomcat started on port(s): 8080 (http) with context path ''` and `...Started Application in #.### seconds (JVM running for #.###)`
6. To stop the server, use the key combination `cmd/ctrl+c`

## Navigating the API

* The server is located at http://localhost:8080
* Headers should be:
* Accept: application/json
* Content-Type: application/json

##Create
* Request Method: **POST**
* Endpoint: **/pets**
* Accepts a list of pets (format below)
* Returns a response object with status, errors, and ids list if successful
* Request format: 

```
{ "pets": [
  	{
  		"name": STRING,
  		"type": INT (1, 2, 3, or 4),
  		"age": INT,
  		"sex": INT (1 or 2),
  		"description": STRING,
  		"owner_email": STRING,
  		"image_url": STRING (image path without full url)
  	}
]}
```

##Update
* Request Method: **PUT**
* Endpoint: **/pets**
* Accepts a list of pets (format below)
* Returns a response object with status, errors, and updatedPets list if successful
* Request format: 

```
{ "pets": [
  	{
  		"id": LONG (required),
  		"name": STRING (optional),
  		"type": INT (1, 2, 3, or 4) (optional),
  		"age": INT (optional),
  		"sex": INT (1 or 2) (optional),
  		"description": STRING (optional),
  		"owner_email": STRING (optional),
  		"image_url": STRING (image path without full url) (optional)
  	}
]}
```

##Delete
* Request Method: **DELETE**
* Endpoint: **/pets**
* Accepts a list of ids (format below)
* Returns a response object with status, errors, and deletedIds list if successful
* Request format: 

```
{ "ids": []}
```

##Search
* Request Method: **GET**
* Endpoint: **/pets**
* Accepts a single search criteria object (optional)
* If no search criteria passed, returns all pets
* Returns a response object with status, errors, and pets list if successful
* Request format: 

```
{ "search": {
  		"id": LONG (optional),
  		"name": STRING (optional),
  		"type": INT (1, 2, 3, or 4) (optional),
  		"age": INT (optional),
  		"sex": INT (1 or 2) (optional),
  		"description": STRING (optional),
  		"owner_email": STRING (optional),
  		"image_url": STRING (image path without full url) (optional)
  	}
}
```