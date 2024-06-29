#!/bin/bash
#
# This will stop the Postgres container
#

cd "$(dirname "$0")/../.."
set -e


name="cart-postgres"

docker stop $name
