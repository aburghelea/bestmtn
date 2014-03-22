# IMDB search engine

Search engine

### Building
```
mvn verify
```

### Running
```
> java -jar target/search-provider-with-pooling.jar server conf/imbd-search-node1.yaml
```

### Using
```
> curl -XGET http://localhost:8080/movies/time
> curl -XGET http://localhost:8090/results/<reference>
```

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

To see the REST API you can use Swagger at [swagger.wordnik.com](http://swagger.wordnik.com/) using ```http://localhost:8080/api-docs```.

