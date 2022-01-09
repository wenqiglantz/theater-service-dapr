kubectl apply `
    -f ./dapr-config.yaml `
    -f ./zipkin.yaml `
    -f ./pubsub-mqtt.yaml `
    -f ./theater-service.yaml