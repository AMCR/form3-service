#!/usr/bin/env bash -x

APP_NAME=form3-service
APP_VERSION=1.0-SNAPSHOT


mvn clean test package

docker build -t ${APP_NAME}:${APP_VERSION} --build-arg APP_NAME=${APP_NAME} --build-arg APP_VERSION=${APP_VERSION} .
