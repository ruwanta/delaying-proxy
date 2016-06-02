# Simple TCP proxy simulating delays in network

This simple proxy allows one to simulate different delays between various nodes.
Basic useful feature list:

 * YAML based configuration
 * Supports HTTP, HTTPS, and SQL Proxying
 * Allows delaying (simulating) slow SQL servers
 * Supports any TCP based Database interfaces e.g.MySQL, Oracle(tm)
 * (Upcoming) Dynamic changes in delay parameters via REST API



### how to build:
mvn clean install

mvn package



The binaries are available at
/modules/dist/target/appassembler