
#
kubectl scale deployment graxon-core --replicas=0 -n graxon

#
docker build -t 127.0.0.1:5001/core:latest ./services/core
docker push 127.0.0.1:5001/core:latest

#
kubectl scale deployment graxon-core --replicas=1 -n graxon

