The logging agent is to keep scanning and uploading the log files to steplogs.

After launch the agent, it will scan all log files and upload.
Or each hour/30 seconds to scan log files that changed in recent 1 hour/30 seconds.


[Experimental] once the termination signal reaches the agent, it will scan all file and upload for a last try. 
> kill -SIGTERM <pid>

`Tips: clean up the log folder once they are uploaded; leave a few seconds after sending termination signal to the agent`


With spring's configuration, override it through:

> -Dspring.profiles.active=dev # required; dev: points to steplogs' dev, stg or qat should be in; prd: to steplogs' prd.

> steplogs.logging.server.ws-url=wss://dev-logging-ingest-server.steplogs.io/ #optional. prd: wss://prd-logging-ingest-server.steplogs.io/

> steplogs.logging.agent.app-key=gwI718pjfZO7LpGW1INf9ME0cnYnjeE9dfnaN4c2VqZgW4ce #required. active the application in portal then will see the app-key

> steplogs.logging.agent.host-id=please-change-to-your-unique-host-id #required. to help the log files' uniqueness. 

> steplogs.logging.agent.watch-dirs=./xyz-service/logs #required. point to the folder of the app/service

> steplogs.logging.agent.host-id=3600000 #optional. after the time, it won't retry files. [the client retries the file if session or file exceptions or errors occur.]

> steplogs.logging.agent.host-id=60000 #optional. after the time, the file to wait for new data writes will be removed from the queue. [The file watcher will always watch for the new data.]
      
