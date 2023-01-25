FROM maven:3.8.7-openjdk-18-slim AS build

ENV TEST_SUITE_NAME="smoke"
ENV BROWSER="remote-chrome"
ENV REMOTE_HUB_URL=http://localhost:4444
ENV APPLICATION_FE_URL=http://localhost:3000
ENV APPLICATION_BE_URL=http://localhost:3001

# Set the working directory
WORKDIR /app

# Copy the pom.xml file
COPY pom.xml .

# Copy the rest of the project files
COPY src/ src/

# Build the project
RUN mvn clean package -DskipTests

# Run the tests
CMD ["sh","-c", "mvn test -DBrowserName=${BROWSER} -Dsurefire.suiteXmlFiles=src/test/java/suites/${TEST_SUITE_NAME}.xml"]

# docker run --rm \
#                -e TEST_SUITE_NAME=smoke \
#                -e REMOTE_HUB_URL=http://85.90.244.177:4444 \
#                -e APPLICATION_FE_URL=http://85.90.244.177:3000 \
#                -e APPLICATION_BE_URL=http://85.90.244.177:3001 \
#                imageName
