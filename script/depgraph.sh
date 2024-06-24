
cd $(dirname $0)/..
set -eo pipefail

(
echo 'digraph { rankdir="LR"'
ack import --java src/main\
  | tr / . \
  | sed 's/src.main.java.//' \
  | sed 's/.java//' \
  | sed 's/com.thoughtworks.tdd.//'g \
  | sed 's/static java.util.Collections.emptyList/java.util/' \
  | sed 's/import //' \
  | sed 's/;//' \
  | sed 's/\.[A-Z][a-zA-Z0-9]*//g' \
  | sed 's/:[0-9]*:/" -> "/' \
  | sed 's/^/"/' \
  | sed 's/$/"/' \
  | sort \
  | uniq

echo '}'
) > /tmp/cart.digraph
dot /tmp/cart.digraph -Tsvg > /tmp/cart.svg
open /tmp/cart.svg