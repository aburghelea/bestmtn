# IMDB search engine

Search engine simulator. The engine is *not* using real-time IMDB data; we're using a snapshot of a few records from IMDB

Asynchronous REST service using pooling:

* a search request will return a "search reference"
![step 1](https://github.com/icemanftg/bestmtn/blob/master/search-provider-with-pooling/src/main/resources/diagrams/step1-%20initial%20query.png?raw=true)
* the "search reference" can be used to obtain the actual search results. It's possible to take up to a few seconds until the results are available.
![step 2](https://github.com/icemanftg/bestmtn/blob/master/search-provider-with-pooling/src/main/resources/diagrams/step%202%20-%20get%20the%20results.png?raw=true)


### Running
```
> java -jar search-provider-with-pooling.jar server conf/imbd-search-node1.yaml
```

### Using
The engine can be accessed using GET request, either from java code, a web browser or command line.

Command-line examples:

```
> curl -XGET http://localhost:8080/movies/<query>
> curl -XGET http://localhost:8090/movies/results/<reference>
```
The <reference> is obtained from the "Location" response header of the initial query (assuming a 303 status code).


### REST API
The REST API documentation is available at ```http://localhost:8080/docs/```
You can also use Swagger at [swagger.wordnik.com](http://swagger.wordnik.com/) using ```http://localhost:8080/api-docs```.

