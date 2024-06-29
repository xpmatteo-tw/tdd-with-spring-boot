#!/bin/bash
#
# This will start Postgres in a docker container
# If the db is already running, it will do nothing
#

cd "$(dirname "$0")/../.."
set -e


name="cart-postgres"
#image="postgres:13.1-alpine"
image="postgres:16.3-alpine3.20"

if docker start $name 2> /dev/null; then
  echo "Container $name already running"
else
  echo "Starting image $image"
  docker run -d -t --name $name -e POSTGRES_PASSWORD=postgres -p 5432:5432 $image
fi
