
#
kubectl apply -f ./services/core/infrastructure/target/classes/META-INF/fabric8/projects.graxon.io-v1.yml

#
helm upgrade --install graxon ./helm --install --namespace graxon
