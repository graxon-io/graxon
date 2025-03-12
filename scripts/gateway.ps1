gateway.ps1kubectl scale deployment graxon-gateway --replicas=0 -n graxon

#
docker build -t 127.0.0.1:5001/gateway:latest ./support/gateway
docker push 127.0.0.1:5001/gateway:latest

#
kubectl scale deployment graxon-gateway --replicas=1 -n graxon