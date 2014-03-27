# TrackTV Legacy Search Engine

Search engine simulator.

Synchronous REST service:
* a search request will return the result based on a query in a JSON format
* the system also has a limited number of simultaneous connections, just 2 and one connection waiting in a queue;
if more than 3 simultaneous requests are made, the server will not be able to handle them

### Building

* package the search engine
```
mvn clean package
```

### Running

```
> java -jar target/syncwebserver.jar -p <portNumber>
```

You can specify any valid port number. If no port is specified the default 8000 is used.

### Using
```
> curl -XGET http://localhost:8000/tracktv?query=<query>
```
The system does not connect to TrackTv, it just uses a subset of it's database.
When the server starts you will see a list of the tv series that are indexed.

Examples of queries:
```
"stargate", "robin hood", "californication".
```

The result will be a JSON and it will contain the number of entries that matched the query and the content, which is a list of entries from our virtual database.
```
{
    "noEntries":50,
    "content":[
        {},
        {}
    ]
}
```






