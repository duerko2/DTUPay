#!/bin/bash
set -e

pushd messaging-utilities-3.4
mvn clean
popd 

pushd token-service
mvn clean
popd 

pushd account-registration-service
mvn clean
popd 
