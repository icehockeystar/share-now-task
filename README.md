#Carspolygons
## Run application locally
1. Start challenge API and MongoDB locally : `docker-compose up`
2. to start the carspolygons application run `./gradlew bootRun` or start `CarsPolygonsApplicationKt` class from your favorite IDE.
3. open in browser `http://localhost:8080/swagger-ui.html`

## Build docker image
In order to build a docker image run:
1. `./gradlew build`
2. `docker build -t app/carspolygons:v0.1.0 .`

## Run docker image
In order to run the application in docker execute:
`docker run app/carspolygons:v0.1.0 -p 8080:8080`
