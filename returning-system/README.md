# TVRage search engine

Search engine simulator.

Asynchronous REST service using a client provided callback:
* a search request will return a the status status of the requested operation

### Building

* package the search engine

```
mvn clean package
```

### Running

```
> java -jar target/search-provider-with-callback.jar --server.port=8083
```

The 8083 port is mandatory if you want to be able tu run requests from SWAGGER

### Using
```
> curl -XGET http://localhost:8083/tvrage/query/<query_word>?callback=<url_encoded_callback>
```
The system does not connect to TvRage it just uses a subset of it's database. When the server starts you will see a list of the tv series that are indexed. 

Examples of keywords of <query_word> values: 
```
stargate, robin, hood, and, californication, the, a.
```

Example of <url_encoded_callback> value: 
```
http%3A%2F%2Flocalhost%3A8080%2Fcallback%3Fqueried_for%3Dgigel.
```
The value coresponds to the following URL: [http://localhost:8080/callback?queried_for=gigel](http://localhost:8080/callback?queried_for=gigel)

The callback has to accept a HTTP Post with only a POST parameter. Using that parameter the search engine will post the results to your application

### REST API
The REST API documentation is available at ```http://localhost:8083/index.html```


