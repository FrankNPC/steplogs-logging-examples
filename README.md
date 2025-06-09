Why steplogs?

steplogs provides seamless way to print logs and sanitize sensitive/PII content, highly controllable traces.

1: logging/logger could be much ambiguous leading the troubleshoot very exhausted. with steplogs, just configuration can cover all of necessary logs. steplogs also support writing logs to the tracing by your own. -- even no need to write logs with proper configure.

2: When you have complicated business logic cross services, tracing the payloads would be much challenging. steplogs provides logging in language support(No need http proxy/servers) to capture the entire logic traces - no need to search logs any more although we provide.

3: when you sanitize sensitive/PII in the logs, the difficulty is you may do it before write into logs, or other assistances. Simple steplogs configurations can convert to the desired mask or encryption.

4: steplogs can co-exist with any logger like log4j, or no longer need these loggers.

More details please check out [www.steplogs.io](https://www.steplogs.io) and [steplogs-logging-integration-java-spring-example](https://github.com/FrankNPC/steplogs-logging-examples/tree/main/steplogs-logging-integration-java-spring-example) 

more language supports coming soon
