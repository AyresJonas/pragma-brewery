## pragma-brewery
___
pragma-brewery is a simple _framework-less_ rest server system. It aims to monitor the temperature of stocked beers.

## Requirements
- Java 8 `(java version "1.8.0_221" Java(TM) SE Runtime Environment (build 1.8.0_221-b11) Java HotSpot(TM) 64-Bit Server VM (build 25.221-b11, mixed mode))`
- Maven `(Apache Maven 3.5.4)`
- Python 3.6.3
- Pip 19.1.1

_
### Implementation details
The rest server is a simple implementation of `com.sun.net.httpserver.HttpServer`. It has two main routes:
__/api/beers/__: responsible for beer CRUD services;
__/api/monitoring__: responsible for handle additions and removals of monitored beers, aswell showing and updating the current temperature.
The tests are implemented using JUnit and the terminal-based interface is implemented using Python.

_
### Running
- `pip install -r requirements.txt` installs all project dependencies
- `python rest_interface.py` runs the server and the interface application
- `mvn test` runs all tests

_
### Todo list
- Automated temperature change due to opened door and/or outside temperature
- General exception handling
- Singletons and dependency injections handling
