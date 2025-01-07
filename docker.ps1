
#
docker build -t 127.0.0.1:5001/core:latest ./core
docker push 127.0.0.1:5001/core:latest

#
docker build -t 127.0.0.1:5001/node:latest ./node
docker push 127.0.0.1:5001/node:latest

#
docker build -t 127.0.0.1:5001/notifier:latest ./notifier
docker push 127.0.0.1:5001/notifier:latest

#
docker build -t 127.0.0.1:5001/gateway:latest ./gateway
docker push 127.0.0.1:5001/gateway:latest

#
docker build -t 127.0.0.1:5001/admin:latest ./admin
docker push 127.0.0.1:5001/admin:latest