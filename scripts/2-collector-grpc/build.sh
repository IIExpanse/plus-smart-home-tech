#!/bin/sh

set -e
echo "Started rebuilding collector-service."

mvn clean package -pl :avro-schemas,:proto-schemas,:collector

docker stop collector-service > /dev/null 2>&1 || true
code=$?
if [ $code -eq 0 ]; then
    echo "Container stopped."
    docker rm collector-service > /dev/null 2>&1 || true
    echo "Container removed."
else
    echo "Container not found."
fi

docker rmi collector-service > /dev/null 2>&1 || true
code=$?
if [ $code -eq 0 ]; then
    echo "Image removed."
else
    echo "Image not found."
fi

docker build -t collector-service -f ./telemetry/collector/Dockerfile ./telemetry/collector
docker compose -f ./docker/2-collector-grpc/docker-compose.yml up -d

echo "Finished rebuilding collector-service."