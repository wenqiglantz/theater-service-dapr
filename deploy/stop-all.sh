#!/bin/bash

kubectl delete \
    -f ./theater-service.yaml \
    -f ./pubsub-mqtt.yaml \
    -f ./zipkin.yaml \
    -f ./dapr-config.yaml