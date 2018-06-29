![](jason-logger.jpg)  
*Jason "Ring" Logger and his colleague at work.*

# ring-json-logger

[![Clojars Project](https://img.shields.io/clojars/v/ring-json-logger.svg)](https://clojars.org/ring-json-logger)
![license](https://img.shields.io/github/license/BillFront/ring-json-logger.svg)

This is a ring middleware to response time and other details about the requests arriving at your web server in a JSON format to `stdout`.

> A twelve-factor app never concerns itself with routing or storage of its output stream. It should not attempt to write to or manage logfiles. Instead, each running process writes its event stream, unbuffered, to `stdout`.  
[The Twelve-Factor App: Logs](https://12factor.net/logs)

Example output:
```json
{"method":"get","uri":"/health","query-string":"some=1&query=params","remote-addr":"172.18.0.1","duration":0,"status":200}
{"method":"post","uri":"/customers","query-string":null,"remote-addr":"172.18.0.1","duration":32,"status":500,"exception":"class java.lang.ArithmeticException","exception-message":"Divide by zero"}
```

## Usage

```clj
(require '[ring.middleware.json-logger :refer [wrap-json-logger]])

; app-routes should be a valid ring application, e.g. routes defined with compojure
(def app (wrap-json-logger app-routes))
```

Please note that all query params are logged, so don't submit sensitive data like passwords, tokens, or credit card numbers in the URI. Parameters posted in the request body are not logged at the moment.

## TODO

* Filter sensitive parameters like tokens or passwords
* Log post params
* Config options

## Licenses

Copyright © 2018 BillFront GmbH

Distributed under MIT License.
