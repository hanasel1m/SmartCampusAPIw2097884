# Client-Server Architectures 5COSC022W

## Hana Selim

### w20978848

# Smart Campus REST API
## API Overview

This project implements a RESTful Smart Campus API using JAX-RS (Jersey)

Base URL: http://localhost:8080/SmartCampusAPIw2097884/api/v1

Resources:

### Rooms (/rooms) 
Manages physical room within campus; each room contains: ID (unique identifier), 
name (room name), capacity (maximum occupancy), sensorIds 
(list of linked sensors)

Operations:

- GET /rooms : retrieve all rooms
- POST /rooms : create a new room
- GET /rooms/{roomId} : retrieve a specific room
- DELETE /rooms/{roomId} : delete a room (only if no sensors are assigned)

### Sensors (/sensors)
Manages sensors assigned to rooms; each sensor contains: id (identifier), type 
(sensor type), status (active or maintenance), currentValue 
(latest reading value), roomId (associated room)

Operations: 
- GET /sensors : retrieve all sensors
- GET /sensors?type=TYPE : filter sensors by type
- POST /sensors : create a sensor (must be linked to an existing room)

### Sensor Readings (/sensors/{sensorId}/readings)
Manages readings for each sensor using a sub-resource; each reading 
contains: id (identifier), timestamp (time of reading), value 
(measured reading value)

Note: Adding a reading automatically updates the sensor's currentValue.


Operations: 
- GET /sensors/{sensorId}/readings : retrieve all readings for a sensor
- POST /sensors/{sensorId}/readings : add a new reading


### Features: 
- Versioned base path (/api/v1)
- In-memory data storage using shared DataStore
- Full CRUD operations for rooms and sensors
- Data validation for all incoming requests
- Business rules enforcement (can't delete room with sensors)
- Query filtering using query parameters 
- Sub-resource architecture for nested data (sensor readings)
- Request and response logging using JAX-RS filters
- Global exception mapper to prevent stack trace exposure
- Location headers returned on resource creation
- Custom exception handling with meaningful HTTP status codes: 
  - 404 Not Found 
  - 409 Conflict
  - 422 Unprocessable Entity
  - 403 Forbidden
  - 500 Internal Server Error
 

## Build and Run Instructions

- Open project in netbeans (ensure Java 17 is installed)
- Clean and build project
- Deploy to Apache Tomcat
- Start server and access API at base URL

## Sample cURL requests

### 1. Create Room
```bash
curl -X POST http://localhost:8080/SmartCampusAPIw2097884/api/v1/rooms \
-H "Content-Type: application/json" \
-d '{"id":"ROOM-1","name":"Lab","capacity":30}'
```

### 2. Get All Rooms
```bash
curl http://localhost:8080/SmartCampusAPIw2097884/api/v1/rooms
```

### 3. Create Sensor
```bash
curl -X POST http://localhost:8080/SmartCampusAPIw2097884/api/v1/sensors \
-H "Content-Type: application/json" \
-d '{"id":"TEMP-001","type":"Temperature","status":"ACTIVE","currentValue":0,"roomId":"ROOM-1"}'
```

### 4. Filter Sensors
```bash
curl "http://localhost:8080/SmartCampusAPIw2097884/api/v1/sensors?type=Temperature"
```

### 5. Add Sensor Reading
```bash
curl -X POST http://localhost:8080/SmartCampusAPIw2097884/api/v1/sensors/TEMP-001/readings \
-H "Content-Type: application/json" \
-d '{"id":"R1","timestamp":1710000000,"value":25.5}'
```

## Report : Question & Answer

### 1- Question: 
In your report, explain the default lifecycle of a JAX-RS Resource 
class. Is a new instance instantiated for every incoming request, or does the 
runtime treat it as a singleton? Elaborate on how this architectural decision 
impacts the way you manage and synchronize your in-memory data structures 
(maps/lists) to prevent data loss or race con-ditions

---

### Answer : 
JAX-RS resource classes are instantiated for every incoming HTTP 
request by default, which ensures thread safety at resource-level, as each 
request operates on it's own object instance. 

In this project I stored data in static memory structures 
(DataStore using HashMaps). 

Because these are shared across requests, they are application-wide singletons 
which introduces risks such as race conditions and concurrent modifications. 

In this implementation, synchronisation is not explicitly handled 
because the system is a lightweight simulation. 

In a production system, thread-safe structures or proper sync methods would be 
required

________________________________________________________________________________


### 2- Question: 
Why is the provision of ”Hypermedia” (links and navigation within 
responses) considered a hallmark of advanced RESTful design (HATEOAS)? How does 
this approach benefit client developers compared to static documentation?

---

### Answer: 
Hypermedia allows API responses to include links that guide clients to 
the next actions available instead of relying on static documentation; clients 
dynamically discover endpoints through responses which: 

- reduces hardcoded URLs in clients
- improves flexibility if endpoints change
- enables self-descriptive APIs 
- simplifies client development

as a result APIS become more scalable and easier to maintain.


________________________________________________________________________________


### 3- Question: 
When returning a list of rooms, what are the implications of 
returning only IDs versus returning the full room objects? Consider network 
bandwidth and client side processing.

---

### Answer: 
Returning only IDs reduces network bandwidth usage and response sizes.

However, it increases client complexity because additional requests are needed 
to get the full details.

Returning full objects increases payload size but simplifies client-side 
processing.

In my API, to improve usability and reduce extra requests, full objects are 
returned.

________________________________________________________________________________


### 4- Question: 
Is the DELETE operation idempotent in your implementation? Provide 
a detailed justification by describing what happens if a client mistakenly sends 
the exact same DELETE request for a room multiple times.

---

### Answer: 
The DELETE request is idempotent because repeating the same request 
produces the same result.

In my implementaion, on the initial delete request a room is removed and a 204 
is returned. On second delete, the room no longer exists and a 404 
(room not found) is returned.

Even though the response is different (204 vs 404), the system state stays the 
same after the first request, which satisfies idempotency.

________________________________________________________________________________


### 5- Question: 
We explicitly use the @Consumes (MediaType.APPLICATION_JSON) 
annotation on the POST method. Explain the technical consequences if a client 
attempts to send data in a different format, such as text/plain or 
application/xml. How does JAX-RS handle this mismatch?


### Answer: 
The @Consumes(MediaType.APPLICATION_JSON) annotation declares that the 
endpoint accepts JSON input only. 

If a client sends text/plain or application/xml, JAX-RS can't find a suitable 
MessageBodyReader and returns HTTP 415 unsupported media type, which prevents 
invalid data formats from being processed.

________________________________________________________________________________


### 6- Question: 
You implemented this filtering using @QueryParam. Contrast this 
with an alterna-tive design where the type is part of the URL path 
(e.g., /api/vl/sensors/type/CO2). Why is the query parameter approach generally 
considered superior for filtering and searching collections?

---

### Answer: 
Query parameters are preferred for filtering because:

- they represent optional criteria
- they allow flexible combinations (e.g. multiple filters)
- they don't change the resource identity

Using /sensors/type/CO2 implies a fixed resource structure and reduces 
flexibility

Using /sensors?type=CO2 expresses filtering clearly and follows REST conventions


________________________________________________________________________________


### 7- Question: 
Discuss the architectural benefits of the Sub-Resource Locator 
pattern. How does delegating logic to separate classes help manage complexity 
in large APIs compared to defining every nested path 
(e.g., sensors/{id}/readings/{rid}) in one massive con-troller class?

----

### Answer: 
The Sub-Resource Locator pattern assigns nested resource handling to 
separate classes. The benefits are: 

- it improves modularity
- it reduces complexity in main resource classes
- it keeps code maintainable
- it separates responsiblities clearly

A single class would become too big and hard to manage without it.

________________________________________________________________________________


### 8- Question: 
Why is HTTP 422 often considered more semantically accurate than a 
standard 404 when the issue is a missing reference inside a valid JSON payload?

---

### Answer: 
HTTP 422 is more appropriate when the request structure is valid JSON but 
contains invalid data

In this case the sensor request is valid but references a non-existent room

Using 404 would imply the endpoint is missing which is incorrect, which makes 
422 a better representative of the semantic error in the request.

________________________________________________________________________________


### 9 - Question: 
From a cybersecurity standpoint, explain the risks associated with exposing 
internal Java stack traces to external API consumers. What specific information 
could an attacker gather from such a trace?

---

### Answer: 
Exposing stack traces can reveal internal class names, package structure, file 
paths and framework details.

Attackers can use this information to identify vulnerabilities and target 
specific components 

The GenericExceptionMapper prevents this by returning a safe, generic error 
response.

________________________________________________________________________________


### 10- Question: 
Why is it advantageous to use JAX-RS filters for cross-cutting 
concerns like logging, rather than manually inserting Logger.info() statements 
inside every single re-source method?

---

### Answer: 
Filters allow logging to be applied globally across all endpoints. The 
advantages are:

- avoids repititive code in every resource method
- ensures consistent logging
- separates cross-cutting from business logic
- improves maintainability

Logging would need to be manually  added to every method without filters, 
which is inefficient.
