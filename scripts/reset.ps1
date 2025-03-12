#
kubectl scale deployment graxon-admin --replicas=0 -n graxon
kubectl scale deployment graxon-gateway --replicas=0 -n graxon
kubectl scale deployment graxon-projects --replicas=0 -n graxon
kubectl scale deployment graxon-core --replicas=0 -n graxon
kubectl scale deployment graxon-notifier --replicas=0 -n graxon

#
kubectl scale deployment graxon-admin --replicas=1 -n graxon
kubectl scale deployment graxon-gateway --replicas=1 -n graxon
kubectl scale deployment graxon-projects --replicas=1 -n graxon
kubectl scale deployment graxon-core --replicas=1 -n graxon
kubectl scale deployment graxon-notifier --replicas=1 -n graxon