Why steplogs?

steplogs provides seamless way to print logs and sanitize sensitive/PII content, highly controllable traces.

1: logging/logger could be much ambiguous leading the troubleshoot very exhausted. with steplogs, just configuration can cover all of necessary logs. steplogs also support writing logs your own but keep tracing.

2: When you have complicated business logic, tracing the payloads cross services would be much challenging. steplogs provides logging in language (No need http proxy/servers) to capture the entire logic traces - you don't need to search logs any more although we provide.

3: when you sanitize sensitive/PII in the logs, the difficulty is you may do it before write into logs, or other assistances. Simple steplogs configurations can convert it to the desired mask or encryption.

More details please check out [www.steplogs.io](https://www.steplogs.io) and [steplogs-logging-integration-java-spring-example](https://github.com/FrankNPC/steplogs-logging-examples/tree/main/steplogs-logging-integration-java-spring-example) 

more language supports coming soon
