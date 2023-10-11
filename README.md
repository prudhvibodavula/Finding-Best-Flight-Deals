✈️

## Set Up Instructions
``` 

Open spring initializer
select the type of project 
add the dependencies 
download the project as jar file
open it in the IDE
```

## API Details``


Actual api to fetch relevant job details - 
curl --location --request POST 'http://localhost:8080/v1/dealsfilter' \
--header 'Content-Type: application/json' \
--data-raw '{
    
}'


## Ontology
1. The ontologies in their current states represent the processing of the viable data, hence some critical fields are missing in certain Object Properties and Data Properties due to it being an experimental data setup with less number of entries. If used on a file with 100,000 datasets or more, the future implementation OWL File should be used.
2. The future implementation OWL File contains the base ontology that is used for an ideal dataset and should be replicated to maximize the relevancy of the filters.


## FUSEKI Server Installation

Go to AWS and Create an EC2 instance in three separate physical locations

Use all Default values until you get to step 6: Configure Security Group.

At this point you should select `add-rule` button to add a rule with the follow parameters
```

Type = custom TCP
  Protocol = TCP
  Port Range = 3030
  Source = 0.0.0.0/0
  Description = Opens the default Fuseki SPARQL endpoint port


```
At the end click the `Review and Launch` button that appears 

You will then be given the option to create a .pem key. We created a random key for each instance

Next in the .ssh/config file we created the following file template with our specified values for each instance

```
Host fusekiserver 
  HostName ec2-yadda-yadda.amazonaws.com
  User ec2-user
  IdentityFile /path/to/aws_key_pair.pem

```

We create the following for ec-2 instance

We then run the following command `sudo chmod 600 /path/to/aws_key_pair.pem` to give us access to shell into the server

Next we run the following command to shell into our EC2 instance `ssh fusekiserver` for example.

Next we install the latest version of java with the following command `sudo amazon-linux-extras install java-openjdk11`

Next we install fuseki in a directory called `~/opt/fuseki` 

```
$ mkdir opt
$ cd opt
$ mkdir fuseki
$ cd fuseki
$ wget http://mirror.reverse.net/pub/apache/jena/binaries/apache-jena-fuseki-4.6.1.tar.gz
$ tar -xvzf apache-jena-fuseki-4.6.1.tar.gz
$ rm apache-jena-fuseki-4.6.1.tar.gz
$ cd apache-jena-fuseki-4.6.1
# For convenience, we set an environment variable....
$ export FUSEKI_BASE=$(pwd)


```

Now you can run the following command on each individual EC2 instance to start up the fuseki server as an in-memory database as well as specify the dataset.
Below represents the following commands to run fuseki server on our EC2 instances

```
./fuseki-server --mem /airlinesdeals

```

Next install `apache-jena-4.6.1.zip` file from `https://jena.apache.org/download/` and add the jar files from the lib directory into SpringBoot and excluding
`log4j-slf4j-impl-2.14.1.jar`in pom.xml as this created conflicts with our repo 

Doing so will allow you to create federated queries in SpringBoot

You are now able to access your EC2 instances on the web and specifically the fuseki server and add the ontologies into the specified dataset 
for the backend to make federated queries on


## FrontEnd
Frontend requirements:
Versions of the following to install 
`axios: 0.24.0
react: 17.0.2
Node: 16.4.0`
Install Visual Studio Code
To run the frontend we can use the command `npm install and npm start` from the UI_Implementation directory.
