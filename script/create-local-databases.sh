#!/usr/bin/env bash
#
# Run this script to re-initialize the local databases to pristine state
# WARNING Running this will wipe all data in the local databases!!!
#
# We assume the db container is running
#

# Stop at the first error
set -eo pipefail

# Execute in the top directory of the project
cd "$(dirname "$0")/.."

# Setting up a DB is 3 step process:
# 1. Create databases, users, roles and grants
# 2. Run migrations to create the schema
# 3. Run seed scripts to load initial data

container="cart-postgres"

## Step 1 : Create databases, users, roles and grants
docker cp -q src/sql/init-db.sql $container:/
docker exec $container psql postgres postgres -f init-db.sql #-v ON_ERROR_STOP=1

for user in cart_dev cart_test ; do
  ## Step 2 : Run migrations (In real work you should use Liquibase, Flyway or similar)
  for sqlFile in src/sql/migrations/*.sql; do
    echo "--- Executing migration for $user: $sqlFile"
    docker cp -q "$sqlFile" $container:/
    docker exec $container psql $user $user -f "$(basename $sqlFile)"
  done

  ## Step 3 : Seed-script
  for sqlFile in src/sql/seed/*.sql; do
    echo "--- Seeding data for $user: $sqlFile"
    docker cp -q "$sqlFile" $container:/
    docker exec $container psql $user $user -f "$(basename $sqlFile)"
  done
done
