#!/bin/sh

set -e
echo "Started rebuilding aggregator-service."

mvn clean package -pl :avro-schemas,:proto-schemas,:aggregator

docker stop aggregator-service > /dev/null 2>&1 || true
code=$?
if [ $code -eq 0 ]; then
    echo "Container stopped."
    docker rm aggregator-service > /dev/null 2>&1 || true
    echo "Container removed."
else
    echo "Container not found."
fi

docker rmi aggregator-service > /dev/null 2>&1 || true
code=$?
if [ $code -eq 0 ]; then
    echo "Image removed."
else
    echo "Image not found."
fi

docker build -t aggregator-service -f ./telemetry/aggregator/Dockerfile ./telemetry/aggregator
docker compose -f ./docker/3-aggregator/docker-compose.yml up -d

echo "Finished rebuilding aggregator-service."