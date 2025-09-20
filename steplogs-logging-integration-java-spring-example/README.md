
# For logger usage #

Why steplogs?

steplogs is the next generation log and trace solution, provides seamless way to unify workflow, logs and sanitize sensitive/PII content, and unit test supports against models.

1: logging could be much ambiguous leading the troubleshoot very exhausted. with steplogs, just configuration can cover all of necessary logs. steplogs also support writing logs to the tracing by your own. -- with proper configuration even no need to write a single log.

2: When you access complicated business logic cross services, tracing the payloads would be much challenging. steplogs provides logging in language support(No need http proxy/servers) to capture the entire logic traces - no need to search logs any more although we provide.

3: when you sanitize sensitive/PII in the logs, the difficulty is you may do it before writing into logs, or other assistances. Simple steplogs configurations can convert to the desired mask or encryption without changing code.

4: with well preserved logs, in batch basis painlessly testing or re-entering the methods that were failed due to bugs or errors turns troubleshooting and datafix an easy job.

5: steplogs can co-exist with any logger like log4j.

More details please check out [www.steplogs.io](https://www.steplogs.io) and [steplogs-logging-integration-java-spring-example](https://github.com/FrankNPC/steplogs-logging-examples/tree/main/steplogs-logging-integration-java-spring-example) 

more language supports coming soon

#### Introduce in maven ####

```
	<dependency>
		<groupId>io.steplogs</groupId>
		<artifactId>steplogs-logger</artifactId>
		<version>1.0-SNAPSHOT</version> <!-- new version https://repo1.maven.org/maven2/io/steplogs/steplogs-logger -->
	</dependency>
```

#### Configure Logger ####

It's better to start with spring, see [steplogs-logging-integration-java-spring-example](https://github.com/FrankNPC/steplogs-logging-examples/tree/main/steplogs-logging-integration-java-spring-example) 


### For Logging, there are two ways to use ###

- 1, On method (Recommended, supporting log sample test)

> `Annotate @Logging on the method/class to log the parameters/returns for the methods, by default`

```java
class ABC{
  @Logging
  TypeABC func(String str) { // line ~233
    // first line better right next
    //do some work
    return TypeABC
  }
}
 
class DEF {
  Object caller(){
    return func("str123");//line 456
  }
}
```

> log: ...ABC.class#func#233#|[{"str":"str123"}]

> log: ...ABC.class#func#233#R|[TypeABC->toJson]

Sample:
> 2025-08-21 01:08:33.791|main-http-nio-80-exec-1-40-5|5Lwgxyfqe3xaDnv23BEXPlhOdMJ1Lldj|4-1|JSON|UserServiceImpl.java#io.steplogs.example.web.service.rpc.UserServiceImpl#save#60#|[{"user":{"name":"br\*\*\*ng","id":0,"driverLisenceId":"\*\*\*\*\*\*","age":1001}}]

> 2025-08-21 01:08:33.792|main-http-nio-80-exec-1-40-5|5Lwgxyfqe3xaDnv23BEXPlhOdMJ1Lldj|4-2|JSON|UserServiceImpl.java#io.steplogs.example.web.service.rpc.UserServiceImpl#save#60#R|[{"name":"br\*\*\*ng","id":789,"driverLisenceId":"\*\*\*\*\*\*","age":1001}]


`Tips for interface it needs AOP or proxy like spring pointcut`


- 2, In method

> `Annotate @Logging with catchPackages/catchClasses on the method/class to log the parameters/returns for the methods.`

```java
class ABC{
  @Logging(catchPackages="io.steplogs.example.web") 
  TypeABC func(String str123) { // line 234
    //do some work
    Object obj = caller.call(str123); // line 278
    //do some work
    return TypeABC // line 299
  }
}

import io.steplogs.example.web;
class DEF {
  Object call(){
    return TypeABC.func("str456");//334
  }
}
```

> log: ...DEF.class#call#334|[{"str123":"str123"}]

> log: ...DEF.class#call#334R|[{"str456":"str456"}]

Sample:
> 2025-08-21 01:08:30.752|main-http-nio-80-exec-4-43-5|We56EsinM00UnynirAHrnp9XXsg3avlc|11|JSON|MedicService.java#io.steplogs.example.web.service.MedicService#lambda$0#38|[{"userId":123}]

> 2025-08-21 01:08:30.756|main-http-nio-80-exec-4-43-5|We56EsinM00UnynirAHrnp9XXsg3avlc|12|JSON|MedicService.java#io.steplogs.example.web.service.MedicService#lambda$0#38R|[{"name":"ra\*\*\*ly","id":123,"driverLisenceId":"\*\*\*\*\*\*","age":null}]


`Tips: take in mind of the sanitizer, in case SearchController requires name/driverLisenceId to be masked`
`Tips: line number 0 means no catcher class/package, or on interface`


## There are three ways to turn on the logging: ##

 * There are three ways to turn on the logging:
 * 1: load agent before any classes: 
 
 ```
	public static void main(String[] args) {
		//io.steplogs.logger.boostrap.LoggingInitiation.premain(null); // must load before everything. or add https://github.com/FrankNPC/steplogs-logging-examples/blob/main/steplogs-logging-integration-java-spring-example/src/main/resources/META-INF/spring.factories 
		new SpringApplicationBuilder(ServerBootApplication.class).run(args);
	}
```
 * 2: load the jar with javaagent: java -javaagent:steplogs-logger-1.0.1.jar= -jar your-app.jar
 * 3: add io.steplogs.logger.spring.LoggingInitiationSpringApplicationRunListener to META-INF/spring.factories
 
 * In general, above is enough,
 * X: It may not work on final methods, or interface with @Logging, or with JUnit, try proxy and AOP pointcut
 * Y: Annotate methods/classes/interfaces with @Logging, or manually add methods: io.steplogs.logger.boostrap.LoggingMethodIntercepter.addTargetMethods/addTargetMethod/addClasses
 * Z: Integrate steplogs-logger-spring-boot-starter with AutoConfigurationSteplogsLogger could work for AOP to proxy interfaces.

---

### PII or sensitive info protection ###

 - sanitizer : /TYPE/Step/[placeholder: *]/MASK(key1|key2)/[placeholder: KEY-base62]/AES(key1|key2)
 - start with /, not end with /; can place multiple groups; $ is to pass parameters.
 - Or, start without / to match specified method by @Logging;
 
> TYPE: in general it's JSON, could be ERROR or TEXT etc.

> Step: support wildcard match:
>> `/JSON/*Controller.java*controller.*Controller#*/*/MASK(encryptionKey)/*/MD5(sessionId|session_id)`

> placeholder: should be base 62 for [vector of MD5/SHA1/SHA256 and key for AES]; may leave it empty then it will use default *

> Currently supported

```java
		EMPTY, -- set the string value of the keys to empty string
		SKIP, -- skip the value of the key so the log doesn't print it.
		POPULAR, -- capture it then scan the entire log to replace all. can use with others: POPULAR(MASK(key1))
		AES, -- AES encryption.
		MD5, -- MD5(key1) -> XWvr4b7h0XBFB79Irz1ny; MD5$1(key1) -> XWvr4b7h0XBFB79Irz1ny-423; 423 is the length of the original text
		SHA1, -- similar with MD5
		SHA256, -- similar with MD5
		MASK, -- Mask the value of the key:
			 */MASK(key1) -> ***123456 -> *****; 
			 */MASK$3(key1) -> 12345678 ->123***678; 12345 -> 123***45
		DATE_FORMATER: -- yyyy-hh-mm HH:MM:ss.ssss/DATE_FORMATER(key); the placeholder is the formatter. See java.text.SimpleDateFormat
		FLOAT_FORMATER: -- #,##0.00/FLOAT_FORMATER(key); the placeholder is the formatter. See java.text.DecimalFormat
		DECIMAL_FORMATER: -- same with FLOAT_FORMATER
```

>> `For TYPE=TEXT: The param should be a regexp to capture text: MASK(license=[a-z0-9]+)`

>> `For TYPE=JSON/ERROR: the param should be the key names: MASK(bean/property/map_key_name)`


### Trace ###

It's critical to pass and get the HTTP_HEADER_STEP_LOG_ID from/to the prev/next service to form the trace, reset the log id(trace id+sequence id etc.) before or after a thread process. See LoggingHttpHeaderResponseAdvice and steplogs-logger-spring-boot-starter/README.md

configure steplogs.app-node.http-header-id-type to write HTTP_HEADER_STEP_TRACE_ID in HTTP response header, get it and access by the link: https://dev-portal.steplogs.io/trace.html?id=[X-Step-Trace-Id]; Or search by keywords through the portal.


### Upload logs ###
download and configure [steplogs-logging-agent](https://github.com/FrankNPC/steplogs-logging-examples/tree/main/steplogs-logging-agent) to upload the logs into steplogs.io for traces.

### Unit Test / QA improvement ###
the idea is to check if the values appear in the sample payload with the method calls' return. For some unit tests, we don't need to exam all of returned values and json-structure exactly matched. Therefore the sample must be a subset of the returned objects after the invoke. So we can manipulate unit tests by logs.

check out LocalCasesTest and LogLineInvokerHelper how do they run unit test by logs.
The test case file contains a pair(separated by \3\n) of log, parameter and return samples of the method as the input to invoke the method, and output to match the return of the method invoke.
Do this in spring will automatically bring up the methods from beans and run the invokes. Or checkout LogLineHelperTest.

`Tips: the tests won't run with sanitizers.`

> Full match

```java

	@Test
	public void test_case5() throws Exception {
		String fileName = "sample/test_case5.log";
		LogLineInvoke logLineInvoke = LogLineHelper.readLogLineFile(fileName);
		LogLineInvokerHelper.invokeLogLineInvokers(loggingMethodIntercepter, methodToBeans, logLineInvoke);
		Object[] retVals = LogLineInvokerHelper.parseReturnObject(logLineInvoke.getMethod(), logLineInvoke.getReturnSample().getPayload());
		Assertions.assertEquals(retVals[0], logLineInvoke.getReturnAndParameter()[0]);
	}
```

> contain subset

```java

	@Test
	public void test_case8() throws Exception {
		String fileName = "sample/test_case8.log";
		LogLineInvoke logLineInvoke = LogLineHelper.readLogLineFile(fileName);
		LogLineInvokerHelper.invokeLogLineInvokers(loggingMethodIntercepter, methodToBeans, logLineInvoke);
		Object[] retSampleVals = LogLineInvokerHelper.parseReturnObject(logLineInvoke.getMethod(), logLineInvoke.getReturnSample().getPayload());
		Assertions.assertTrue(LogLineInvokerHelper.containSubset(retSampleVals[0], logLineInvoke.getReturnAndParameter()[0]));
		Assertions.assertFalse(LogLineInvokerHelper.containSubset(logLineInvoke.getReturnAndParameter()[0], retSampleVals[0]));
	}
```




# For steplogs-logger-spring-boot-starter integration #

 - 1, introduce the jar, annotate beans with [@Logging](https://github.com/FrankNPC/steplogs-logger/blob/main/src/main/java/io/steplogs/logger/annotation/Logging.java)

```
		<dependency>
			<groupId>io.steplogs</groupId>
			<artifactId>steplogs-logger-spring-boot-starter</artifactId>
			<version>1.0-SNAPSHOT</version> <!--  new version https://repo1.maven.org/maven2/io/steplogs/steplogs-logger-spring-boot-starter -->
		</dependency>
```


 - 2, configuration. see the explains in src/*/resource/application.xml, configure logger and app-node.
    -  import AutoConfigurationSteplogsLogger.class to declare default Logging.

 - 3, configure the http client request to write HTTP_HEADER_STEP_LOG_ID(X-Step-Log-Id) header, so the next app/service/web-server can pick it up. See io.steplogs.logger.spring.LoggingHeaderClientHttpRequestInterceptor
 
 - 4, configure web server to pick up HTTP_HEADER_STEP_LOG_ID(X-Step-Log-Id) from the http request header. No need If with AutoConfigurationSteplogsLogger in spring web. 

 
#### see example [steplogs-logging-integration-java-spring-example](https://github.com/FrankNPC/steplogs-logging-examples/tree/main/steplogs-logging-integration-java-spring-example) ####


`Tip 1: Print X-Step-Trace-Id to the http response header might be helpful for debug, see LoggingHttpHeaderResponseAdvice`

`Tip 2: It's required to pass and read X-Step-Log-Id to/from the prev/next service so the traces can form as ` 
![Screenshot trace](./Screenshot-trace.png)


