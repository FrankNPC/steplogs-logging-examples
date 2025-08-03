The logging agent is to keep scanning and uploading local log files to steplogs portal server.

To download the executable jar: https://repo1.maven.org/maven2/io/steplogs/steplogs-logging-agent/

After launch the agent, it will scan all log files and upload.

[Experimental] once the termination signal reaches the agent, it will scan all file and upload for a last try. 
> kill -SIGTERM < pid >     --no force kill

`Tip: clean up the logs folder once they are uploaded; leave a few seconds after sending termination signal to the agent`

The jar is executable: 
> nohup ./openjdk-8/bin/java "-Dsteplogs.logging.agent.server-url=wss://dev-logging-ingest-server.steplogs.io/ -Dsteplogs.logging.agent.watch-dirs=./logs -jar steplogs-logging-agent-1.0.1.jar

The file name and host id should be less than 256, not blank and not one of '"<>[]{}@&\

[Experimental]To import logs from other logging files, configure steplogs.logging.agent.log-convertor. It converts to steplogs' style to be searched from portal.
    
More explains see src/test/resources/application.xml