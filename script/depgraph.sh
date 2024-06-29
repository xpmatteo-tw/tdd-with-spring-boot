
shorten_our_package_names() {
  sed 's/com.thoughtworks.tdd.//g'
}

shorten_library_package_names() {
  sed 's/\([a-z]*\.[a-z]*\)\.[a-z.]*/\1/'
}

convert_to_dot_notation() {
  awk '{print "\"" $1 "\" -> \"" $3 "\"" }'
}

cd $(dirname $0)/..
set -eo pipefail

if [ "$1" != "--skip-build" ]; then
  ./gradlew build -x test
fi

(
echo '
digraph { 
  rankdir="LR"
  node [style=filled,color="lightgray"]
  "cart.domain" [color="lightgreen"]
  "cart.service" [color="lightgreen"]
  "cart.web" [color="Burlywood"]
  "cart.db" [color="Burlywood"]
  "cart" [label="main", color="lightblue"]
'
jdeps build/libs/cart-0.0.1-SNAPSHOT-plain.jar \
  | tail -n +3 \
  | shorten_our_package_names \
  | shorten_library_package_names \
  | convert_to_dot_notation  \
  | sort \
  | uniq \
  | cat
echo "}"
) > /tmp/cart.dot

dot /tmp/cart.dot -Tsvg > /tmp/cart.svg
open /tmp/cart.svg
