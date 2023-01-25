#!/bin/bash

# remove old reports
echo "Removing old reports"
rm -rf test-output
rm -rf outputs

# Define the name of the Docker image
IMAGE_NAME="my-selenium-tests"

# Define the name of the container
CONTAINER_NAME="selenium-tests-container"

# Remove any previously running container with the same name
echo "Deleting old container"
docker rm -f $CONTAINER_NAME 2> /dev/null || true

# Build the Docker image
echo "Building image"
docker build -f build/Dockerfile -t $IMAGE_NAME .

if [ $? -ne 0 ]; then
echo "Error building the image, exiting..."
exit 1
fi

# Run the tests in a new container
echo "Running the test in a new container"
docker run \
       --name $CONTAINER_NAME \
       -e TEST_SUITE_NAME=${TEST_SUITE_NAME:-"smoke"} \
       -e BROWSER=${BROWSER:-"remote-chrome"} \
       -e REMOTE_HUB_URL=${REMOTE_HUB_URL:="http://selenium.ercansencanoglu.com:31466"} \
       -e APPLICATION_FE_URL=${APPLICATION_FE_URL:-"http://85.90.244.177:3000"} \
       -e APPLICATION_BE_URL=${APPLICATION_BE_URL:-"http://85.90.244.177:3001"} \
       $IMAGE_NAME
       #--rm
       #-v ${PWD}/test-output:/app/test-output \
       #-v ${PWD}/outputs:/app/outputs \
       #above lines can be used instead of docker cp, if this aproach will be used then --rm flag can be used instead of docker rm line below.

# Copy reports and other outputs folder
echo "Copying outputs"
docker cp $CONTAINER_NAME:/app/test-output test-output
docker cp $CONTAINER_NAME:/app/outputs outputs

# Remove the container and the image
echo "Removing container and image"
docker rm -f $CONTAINER_NAME 2> /dev/null || true
docker image rm -f $IMAGE_NAME 2> /dev/null || true
