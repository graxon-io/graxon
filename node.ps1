kubectl scale deployment node-graxon-node --replicas=0 -n graxon
helm upgrade --install node ./helm/node --install --namespace graxon
