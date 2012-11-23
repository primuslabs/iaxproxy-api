iaxproxy-api
============

This is the "reference" version of a RESTful API for the iaxproxy project.  The source is provided as a Netbeans 7.2 project, however, you should be able to build the source easily with another IDE.  A pre-build WAR file is also included.

Configuring:

The main configuration file for the API is in $catalina.home/conf/IAXProxyAPI.properties.  If catalina.home is not defined, the API will default to "/opt/tomcat".

The properties file supports the following settings:

redisHost - defaults to 127.0.0.1
service_log_path - defaults to logs/IAXProxyAPI.log
service_log_format - defaults to "%d{ISO8601} [%-5p] %m%n"
service_log_detail - defaults to DEBUG

Using:

The API exposes the following RESTFul interfaces:

/v1/list

This interface supports a GET operation and returns a JSON representation which lists all users currently provisioned in the database.  An example response is:

{
   "users":[
      {
         "iaxUsername":"4165551212"
      }
   ]
}

/v1/user/{username}

This interfaces supports GET, PUT, and DELETE operations.  The GET operation returns all information about a provisioned user, example:

{
   "iaxUsername":"4165551212",
   "iaxPassword":"mysecurepassword",
   "sipUsername":"4165551212",
   "sipPassword":"mysippassword",
   "sipAuthuser":"sipauthuser",
   "sipDomain":"iaxproxy.org",
   "sipProxy":"sipproxy.mydomain.tld",
   "uuid":"2073771b-8a62-45eb-9492-67dff2385a03"
}

The DELETE operation is used to remove a user and returns a result message, example:

{
   "success":"true",
   "errorMessage":""
}

The PUT operation is used to create users in the system.  The system expects input formatted like the GET response above and will respond with a status message just like the DELTE method.

TODO:

This is a reference API that should only be used for testing the iaxproxy solution.  In a real production environment you would want to add security, error handling, etc.


