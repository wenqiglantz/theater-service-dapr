# Theater Service

###  This demo app, Theater Service, consumes TheaterCreatedEvent published by a provider. It also provides endpoints to create/get/get history of a theater. 
A newly created theater will be in PENDING status, when a TheaterCreatedEvent is consumed, it updates the theater status to CREATED, and keeps a log of its history.

* **A. Prerequisites**
   * 1. Windows/Linux based operating system
   * 2. Java 11 
   * 3. Dapr CLI (follow instructions on https://docs.dapr.io/getting-started/)
   * 4. Docker (follow instructions on https://docs.docker.com/get-docker/)
   * 5. Database has been created or assuming using an in-memory database for testing purpose.
  

* **B. Getting Started & Run the Application**
   * 1. Clone the git repo in to your local directory from the github repository
        
   * 2. Open the command prompt and navigate to the main project directory.
   
   * 3. Copy mssql.jks file located under rest-controller submodule to under "tmp" directory on your local env. Empty file merely for local testing purpose.

   * 4. Build the application:
        * `mvn clean install`

   * 5. Run the application with Dapr as sidecar WITH docker container:
        * `docker-compose up --build`

   * 6. Stop the app:
        * `docker-compose stop`

   * 7. Tear down the app (warning: this will delete the contents of your app's database):
        * `docker-compose down`

   * 8. If you don't have Docker installed locally, run the application with Dapr as sidecar locally WITHOUT docker container:
        * `dapr run --components-path ./theater-service-dapr-components --app-id theater-service --app-port 9000 -- java -jar theater-service-rest-controller/target/theater-service-rest-controller-0.0.1-SNAPSHOT-exec.jar -p 9000`


* **C. Project Code Modules**
    * `theater-service-config` - Spring Configuration classes for all of the modules.
    * `theater-service-dapr-components` - dapr components files for pubsub.
    * `theater-service-data` - Contains domain data shared with other applications such as rest-api, events etc.
    * `theater-service-persistence` - Responsible for hosting the Entities and Repositories for the database. 
    * `theater-service-qa` - Integration/Functional Tests.
    * `theater-service-rest-controller` - REST Endpoints for the application.
    * `theater-service-service` - Contains business logic for the application.


* **D. Application Launch**
    * web: http://localhost:9000/swagger-ui.html


### PubSub

For testing purpose, we are using EMQ X MQTT public broker [https://www.emqx.io/mqtt/public-mqtt5-broker](https://www.emqx.io/mqtt/public-mqtt5-broker) so the Subscriber app can communicate with this service through MQTT pubsub.


### Instructions to deploy to AKS

Refer to my Medium article for detailed instructions to deploy to Azure Kubernetes Service: 
https://betterprogramming.pub/how-to-deploy-microservice-using-dapr-in-azure-kubernetes-service-da75319dd0ea?sk=54a491852d5993df0a1eebf7387ccad2


