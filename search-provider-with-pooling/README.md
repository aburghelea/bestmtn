# IMDB search engine

Search engine simulator.

Asynchronous REST service using pooling:
* a search request will return a "search reference"
![step 1](https://github.com/icemanftg/bestmtn/blob/master/search-provider-with-pooling/src/main/resources/diagrams/step1-%20initial%20query.png?raw=true)
* the "search reference" can be used to obtain the actual search results. It's possible to take up to a few seconds until the results are available.
![step 2](https://github.com/icemanftg/bestmtn/blob/master/search-provider-with-pooling/src/main/resources/diagrams/step%202%20-%20get%20the%20results.png?raw=true)

## If you're lazy
 If you're in a rush, you can use the deployment at [imdb.archeus.ro](http://imdb.archeus.ro:8080/docs/index.html) to play around with it.
 The below links are assuming local deployment, replace ```localhost``` with ```imdb.archeus.ro``` if you're using the web deployment.

### Building
* clone & install dropwizard: https://github.com/dropwizard/dropwizard (necessary because 0.7.0 is not yet released)
```
git clone https://github.com/dropwizard/dropwizard.git
cd dropwizard
mvn clean install
```
* clone dropwizard-spring: https://github.com/gid79/dropwizard-spring (necessary because 0.3.1 is not yet released)
* checkout & install sha 71e97fe44 of dropwizard-spring
```
git clone https://github.com/gid79/dropwizard-spring.git
cd dropwizard-spring
git checkout 71e97fe44ccca1721d2c7df6f0d715fed19bf06c
mvn clean install
```
* package the search engine
```
mvn verify
```

### Running
```
> java -jar target/search-provider-with-pooling.jar server conf/imbd-search-node1.yaml
```

### Using
```
> curl -XGET http://localhost:8080/movies/<query>
> curl -XGET http://localhost:8090/movies/results/<reference>
```
The <reference> is obtained from the "Location" response header of the initial query (assuming an 303 status code).

### Monitoring
```
> curl -XGET http://localhost:8081/healthcheck
```

### Clearing the Blacklist
```
> curl -XPOST http://localhost:8081/tasks/purge-black-list
```

### Spam filter management (active by default)
#### status
```
> curl -XPOST http://localhost:8081/tasks/spam-service-management
```
#### Activation
```
> curl -XPOST http://localhost:8081/tasks/spam-service-management?activate=true

```
#### Deactivation
```
> curl -XPOST http://localhost:8081/tasks/spam-service-management?deactivate=true
```



### Status
```
> curl -XGET http://localhost:8081/metrics
```

### REST API
The REST API documentation is available at ```http://localhost:8080/docs/```
You can also use Swagger at [swagger.wordnik.com](http://swagger.wordnik.com/) using ```http://localhost:8080/api-docs```.

