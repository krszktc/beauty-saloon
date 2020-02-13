# Info

Simple api which allow to fulfill and manage beauty saloon like data

# Installation

  * run "mvn clean install" in /app folder
  * some data are loaded in runtime and more entitiec can be created by upload proper file from /imports
  * by db integrity constraints order or uploading is important: clients -> appointments -> services/purchases
  
# Functionality

* an endpoints to manage single entity
* an endpoints to consume and parse csv file and import data into database
* an endpoint to list the top X number (endpoint parameter eg: 50) of clients that have accumulated the most loyalty points since Y date (endpoint parameter eg: 2018-01-01). Please exclude any banned clients.

# Usage

  * Use http://localhost:8082 to access H2 console:  
  Driver: org.h2.Driver  
  JDBC URL: jdbc:h2:mem:demo-db
  * Use http://localhost:4567/api/ with endpoints listed below to menage data
  
# Endpoints:

### /client, /appointment, /purchase, /service

* GET /
* GET /id
* POST /create
* POST /upload
* PUT /update/id
* DELETE /delete/id

### /client

* PATCH /ban

### /loyalty

* GET /count/date

# Data format:

* entity id:  
a0000002-11aa-11aa-11aa-111111aaaaaa
* loyalty params:  
/20/2019-01-01
* ban body:  
{ id: "a0000001-11aa-11aa-11aa-111111aaaaac", status: true }
* client create/update body:  
{ firstName: "Name", lastName: "Surname", email: "soem@email.com", phone: "111-222-333", gender: "Male" }
* appointment create/update body:  
{ clientId: "a0000002-11aa-11aa-11aa-111111aaaaaa", startTime: "2020-01-01 12:00:00", endTime: "2020-01-01 12:30:00" }
* purchase create/update body:  
{ appointmentId: "b0000002-11aa-11aa-11aa-111111aaaaaa", name: "Purchase name", price: 10.5, loyaltyPoint: 10 }
* service create/update body:  
{ appointmentId: "b0000002-11aa-11aa-11aa-111111aaaaaa", name: "Service name", price: 15.0, loyaltyPoint: 20 }
* csv file:  
find example in /imports folder
