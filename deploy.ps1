kubectl scale deployment graxon-graxon-admin --replicas=0 -n graxon
kubectl scale deployment graxon-graxon-gateway --replicas=0 -n graxon
kubectl scale deployment graxon-graxon-core --replicas=0 -n graxon
kubectl scale deployment graxon-graxon-notifier --replicas=0 -n graxon

#
kubectl apply -f ./core/infrastructure/target/classes/META-INF/fabric8/projects.graxon.io-v1.yml
kubectl apply -f ./core/infrastructure/target/classes/META-INF/fabric8/clusters.graxon.io-v1.yml
kubectl apply -f ./core/infrastructure/target/classes/META-INF/fabric8/nodes.graxon.io-v1.yml

#
helm upgrade --install graxon ./helm/core --install --namespace graxon
