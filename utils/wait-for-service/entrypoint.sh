#!/bin/sh

set -o errexit
set -o pipefail
set -o nounset


#
usage()
{
    cat << USAGE >&2
Usage:

  wait on services
    ./entrypoint.sh NAME:IP:PORT NAME:IP:PORT

Example:
    ./entrypoint.sh mongodb:mongodb-service:27017 mysql:mysql-headless:3306

USAGE
    exit 1
}

# example: wait_for_services mongodb:mongodb-service:27017 mysql:mysql-headless:3306
wait_for_services() {
  for var in "$@"
  do
    service_name=`echo $var | cut -d ':' -f1`
    service_ip=`echo $var | cut -d ':' -f2`
    service_port=`echo $var | cut -d ':' -f3`

    if [ -z $service_ip ] || [ -z $service_port ]; then
      echo "skipping wait for $service_name due to missing configs"
      exit 1
    else
      wait_for_tcp $service_name $service_ip $service_port
    fi
  done
}

# example: wait_for_tcp mongodb-service:27017
wait_for_tcp() {
  SERVICE=`echo $2 | grep ':' || echo $2:$3`
  until nc -vz $SERVICE > /dev/null; do
    >&2 echo "$1 is unavailable..."
    sleep 5
  done
  >&2 echo "$1 is up"
}

# ---------------------

# If no arguments, call the usage function and exit
if [ $# -eq 0 ]; then
    usage
fi

#
wait_for_services "$@"

#
exit 0