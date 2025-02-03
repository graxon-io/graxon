#
kubectl scale deployment graxon-admin --replicas=0 -n graxon
kubectl scale deployment graxon-gateway --replicas=0 -n graxon
kubectl scale deployment graxon-core --replicas=0 -n graxon
kubectl scale deployment graxon-notifier --replicas=0 -n graxon

#
kubectl apply -f ./services/core/infrastructure/target/classes/META-INF/fabric8/projects.graxon.io-v1.yml

#
docker build -t 127.0.0.1:5001/core:latest ./services/core
docker push 127.0.0.1:5001/core:latest

#
docker build -t 127.0.0.1:5001/notifier:latest ./services/notifier
docker push 127.0.0.1:5001/notifier:latest

#
docker build -t 127.0.0.1:5001/gateway:latest ./support/gateway
docker push 127.0.0.1:5001/gateway:latest

#
docker build -t 127.0.0.1:5001/admin:latest ./support/admin
docker push 127.0.0.1:5001/admin:latest

#
helm upgrade --install graxon ./helm --install --namespace graxon
