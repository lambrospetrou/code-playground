#!/usr/bin/env bash

# Use Clojure to build the ClojureScript function
clj -m cljs.main --target node --optimizations advanced -c hello-world.core
# Or use Lumo which is ontop of NodeJS so no need for Java/Clojure.
# Assumes `docker pull anmonteiro/lumo:latest`
#docker run -v `pwd`:`pwd` -w `pwd` --rm -it anmonteiro/lumo --classpath src ./build.cljs && chown -R `id -u`:`id -u` main.js

rm -rf build/ && mkdir -p build/
zip -j build/awslambda.zip build-tools/index.js out/main.js
