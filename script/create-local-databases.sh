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

# Function that executes a sql script in the Postgres container
execute_sql() {
  local user="$1"
  local sqlFile="$2"
  docker cp -q "$sqlFile" $container:/
  docker exec $container psql $user $user -f "$(basename $sqlFile)"
}

## Step 1 : Create databases, users, roles and grants
execute_sql postgres src/main/sql/init-db.sql

for user in cart_dev cart_test ; do
  ## Step 2 : Run migrations (In real work you should use Liquibase, Flyway or similar)
  for sqlFile in src/main/sql/migrations/*.sql; do
    echo "--- Executing migration for $user: $sqlFile"
    execute_sql "$user" "$sqlFile"
  done

  ## Step 3 : Seed-script
  for sqlFile in src/main/sql/seed/*.sql; do
    echo "--- Seeding data for $user: $sqlFile"
    execute_sql "$user" "$sqlFile"
  done
done
