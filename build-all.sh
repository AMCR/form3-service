#!/usr/bin/env bash -x

export APP_NAME=form3-service
export APP_VERSION=1.0-SNAPSHOT
export ENVIRONMENT=ct

# Build and Test
mvn clean test package -pl service

# Build Docker image
docker build -t ${APP_NAME}:${APP_VERSION} --build-arg APP_NAME=${APP_NAME} --build-arg APP_VERSION=${APP_VERSION} .

# Prepare Functional Tests
docker-compose up -d
export DOCKER_PORT=$(echo $(docker-compose port service 8080) |  awk -F: '{print $2}')

## Wait until the service is up
sleep 10

# Run Functional Tests
mvn -DDOCKER_PORT=${DOCKER_PORT} clean test -pl functional-tests

docker-compose down
