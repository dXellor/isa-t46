# Web application for medical equipment marketplace


## About

This repository contains the source code of the project which is the part of the **Internet Software Architectures** course. The goal of the project is to design scalable web application which aims to help private hospitals buy and reserve their medical equipment. The complete proof of concept document that describes our vision for the scalable architecture can be found in [docs](https://github.com/dXellor/isa-t46/tree/main/docs) directory.

![Scalable architecture diagram](/docs/scalable-arch-diagram.png)

## Technologies

* Backend - Java Spring Boot 2.7
* Frontend - Angular 16.2.1
* Data persistency - PostgreSQL database
* L2 Caching - EhCache
* Message Queue Broker - Eclipse Mosquitto 


## How to install and run

### Requirements
You must have software from this list installed on your system to be able to run the project
* Java Runtime 8 or higher
* Apache Maven
* Angular 16.2.1
* PostgreSql database server
* Mosquitto MQTT broker

### Running project
After you setup all the required software follow these steps to run the application:
1. Create database on your PostgreSQL server and update application.properties (you can find example with all the parameters [here](https://github.com/dXellor/isa-t46/blob/main/isa-back/src/main/resources/application.properties.example))
2. Enter the Eclipse or JetBrains InteliJ IDE, build Maven project and run
3. Run frontend application with command ``ng serve``


## Authors

* [Anastasija Radić](https://github.com/anastasijaradic)
* [Jelena Vujović](https://github.com/zanyaIO)
* [Srđan Petronijević](https://github.com/srdjanpetronijevic)
* [Nikola Simić](https://github.com/dXellor)


