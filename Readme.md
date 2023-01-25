#java, #selenium, #testng, #maven, #allure, #log4j, #docker, #docker-compose, #kubernetes, #jenkins

### Description
This project has been created just to write some ui automation with selenium for self-improvement and for education.
In the project in some places used different approaches, different methods to show usage that's why consistency between tests should not been expected. Some test cases can be changed, some verification can be added to some test cases also many other test cases can be added to this project.
There can be added api verifications to verify if request send correctly from ui and if response displayed correctly on ui response but in this project i did not add api verification.
Also, test data cleaning functions can be added after running test cases, but I did not add it because it refreshes database after restarting development app.


### Development Project
Test cases based on [Cypress-Realworld-App](https://github.com/cypress-io/cypress-realworld-app) development project.
To run development project:
Clone the github [Cypress-Realworld-App](https://github.com/cypress-io/cypress-realworld-app) and then run below commands.
```sh
 yarn install
 yarn dev
```
### Setup

##### Environment variables
  - REMOTE_HUB_URL  - It targets to remote selenium grid hub. It must be set to a valid HUB URL if tests will run in a remote server. 
  - APPLICATION_FE_URL - It targets to development app frontend URL. Default value is 'http://localhost:3000'
  - APPLICATION_BE_URL - It targets to development app backend URL. Default value is 'http://localhost:3001'

##### Run tests
To run test, above environment variables should be set and then run command.
Browser type can be given with BrowserName parameter, or it can be set on test.properties file if both not set then tests will run on local chrome browser.
```sh
 mvn clean test -D${BrowserName} -Dsurefire.suiteXmlFiles=src/test/java/suites/${suiteName}.xml
```

#### Browser Types
     - chrome (default)
     - firefox
     - opera
     - remote-chrome
     - remote-firefox
     - remote-edge

### Project Structure

#### Build Folder
It  contains a files to run test cases and files to create ci/cd jobs.

###### Dockerfile
It is used to run tests with docker. It should be build and then run the container with parameters

  - Build DockerFile
```sh
docker build -f build/Dockerfile -t selenium-automation-project .
```
  - Run container with environment variables and give volume path for output of test-reports and outputs folders, also TEST_SUITE_NAME(default: smoke) environment variable must be set name of suite file without its extension. REMOTE_HUB_URL(default: http://localhost:4444) must be set if tests will run on a remote server. Also, BROWSER(default: remote-chrome), APPLICATION_FE_URL(default: http://localhost:3000) and APPLICATION_BE_URL(default: http://localhost:3001) environment variables must be set.
Example:
```sh
docker run --rm \
 -e BROWSER=remote-chrome \
 -e TEST_SUITE_NAME=smoke \
 -e REMOTE_HUB_URL=$REMOTE_HUB_URL \
 -e APPLICATION_FE_URL=$APPLICATION_FE_URL \
 -e APPLICATION_BE_URL=$APPLICATION_BE_URL \
 -v output_path/reports:/app/test-output \
 -v  output_path/logs:/app/outputs \
 selenium-automation-project
````

###### Dockercompose.yml
It is used to create a selenium grid environment. It will start a selenium hub, chrome,firefox and edge nodes then it will connect nodes to selenium hub. 
Selenium hub will be available on port 4444, chrome vnc server will be available 9001-9002 (depends on replicas count), firefox vnc server will be available 9011-9012 (depends on replicas count), edge vnc server will be available 9021-9022 (depends on replicas count). Nodes can be watch with a vnc viewer.
Ports and replica counts can be changed.
  - Run Dockercompose.yml
```sh
docker-compose -f build/Dockercompose.yml up
```

###### kubernetes.yml
It is used to create a selenium grid environment on a kubernetes cluster.  It will start a selenium hub, chrome nodes then it will connect nodes to selenium hub. Other kind of nodes can be added in same way firefox, edge etc.
Selenium ui can be reached by ingress, on this file it is selenium.ercansencanoglu.com but it must be modified

- Run kubernetes.yml
```sh
kubectl apply -f build/kubernetes/yml
```

###### vnc_ingress.sh
It has been created to export drivers vnc with ingress on kubernetes. So it will be available to watch what is happening on chrome nodes. It will be available to reach the pod by http://selenium.ercansencanoglu.com/driver/chrome/{portHash}

###### Jenkinsfile
There are 2 ways to create a jenkins job for this project. First way is to run with docker and another way is with jenkins file. Testng and allure report plugins should be added on jenkins. Three types of report have been added to Jenkins just to see different options of reports.
  - It will enough to run init_docker.bash file on a freestyle jenkins job on execute shell build step. But reports and variables steps must be configured on the jenkins job.
  - It can be also create with Jenkinsfile, but maven plugin must be installed and configured as mvn on jenkins.

### main package
It contains classes to help create and run tests cases. Requests package store the classes to connect to backend to create test data for tests.

### test package
It contains test listeners, locate classes, page classes, suites and test cases

### Reports
Under test-ouput folder, there can be seen different kind of reports.
outputs folder store the logs and screenshots.
Allure should be installed locally to see allure reports on browser. below command should be run after installing allure.
```sh
allure serve test-output/allure-results 
````

 - TestNG reports and html file
 - JUnit reports
 - Allure report