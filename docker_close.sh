# close all running containers

docker stop $(docker ps -aq)