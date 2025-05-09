The logging agent is to keep scanning and uploading the log files to steplogs.

After launch the agent, it will scan all log files and upload.

[Experimental] once the termination signal reaches the agent, it will scan all file and upload for a last try. 
> kill -SIGTERM < pid >     --no force kill

`Tip: clean up the logs folder once they are uploaded; leave a few seconds after sending termination signal to the agent`

The jar is executable: 
> nohup ./openjdk-8/bin/java "-Dsteplogs.logging.server.ws-url=wss://dev-logging-ingest-server.steplogs.io/ -Dsteplogs.logging.agent.watch-dirs=./logs -jar steplogs-logging-agent-1.0.1.jar

The file name should be less than 256, not blank and not one of '"<>[]{}@&\

With spring's configuration, override it through:

> -Dsteplogs.logging.server.ws-url=wss://dev-logging-ingest-server.steplogs.io/ #required. prd: wss://prd-logging-ingest-server.steplogs.io/

> -Dsteplogs.logging.agent.app-key=gwI718pjfZO7LpGW1INf9ME0cnYnjeE9dfnaN4c2VqZgW4ce #required. active the application in portal then will see the app-key

> -Dsteplogs.logging.agent.host-id=please-change-to-your-unique-host-id #required. to help the log files' uniqueness. should be less than 256, not blank and '"<>[]{}@&\

> -Dsteplogs.logging.agent.watch-dirs=./xyz-service/logs #required. point to the folder of the app/service. The agent uses ./log as default log folder, can change by log4j configs.

> -Dsteplogs.logging.agent.retry-cycle-timeout=3600000 #optional. after the time, it won't retry files. [the agent retries the file if session or file exceptions or errors occur.]

> -Dsteplogs.logging.agent.file-idle-timeout=60000 #optional. after the time, the file to wait for new data writes will be removed from the queue. 
