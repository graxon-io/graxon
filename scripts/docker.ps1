
#
docker build -t 127.0.0.1:5001/wait-for-service:latest ./utils/wait-for-service
docker push 127.0.0.1:5001/wait-for-service:latest

#
docker build -t 127.0.0.1:5001/projects:latest ./services/projects
docker push 127.0.0.1:5001/projects:latest

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