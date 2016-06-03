# Simple TCP proxy simulating delays in network

This simple proxy allows one to simulate different delays between different network nodes.
Basic useful feature list:

 * Intuitive YAML based configuration.
 * Supports HTTP, HTTPS, and SQL Proxying.
 * Allows delaying (simulating) slow SQL servers.
 * Supports any TCP based Database interfaces e.g.MySQL, Oracle(tm) .
 * Dynamic changes in delay parameters via REST API.
 * Uses [Netty](http://netty.io/) for IO handling.
 * Uses [WSO2 MSF4J](https://github.com/wso2/msf4j) for REST API publishing.
 * High performance, Low memory footprint, low CPU and resource usage.
 * Can run headless.



### how to build
```
mvn clean install
mvn package
```

### How to Run
The binaries are available at /modules/dist/target/appassembler.
You can copy entire content and distribute as you wish.

Go to "appassembler" directory 

Linux/Unix/OS-X
```
bin/proxy proxy-conf.yaml
```

Windows
```
bin\proxy.bat proxy-conf.yaml
```


### YAML Configuration
Configurations are done with "proxy-conf.yaml". 

```yaml
proxies:
  - name: "To WSO2 IS"    # Unique human readable name
    enable: true          # enable or disable this proxy
    type: TCP             # Proxy type, TCP or SQL
    in:                   # Inbound configuration
      port: 10080         # Inbound listening port
      host: localhost     # Inbound listening IP (currently all IPs)
    out:                  # Outbound configuration
      port: 80            # Remote port to be proxied
      host: www.google.lk # Remote host to be proxied
    delay:                # Delay configurations (only one delay set can be configured currently)
      match: all          # - Unused
      min: 10             # Minimum Delay in milliseconds
      avg: 30             # Average delay in milliseconds 
      max: 130            # Maximum delay in milliseconds
```   

The delay is randomized on average delay. However it will be in between min and max value.

### REST API

Set min, max or average delay
```
http://<host>:<port>/configure/proxy/{index}/delay/{match}/<min|max|average>/{value}
```
e.g. 
```
http://<host>:<port>/configure/proxy/0/delay/match/min/3
```
Sets the minimum delay 3 milliseconds for the proxy at index 0. ("match" term currently unused and can put any string.) 
