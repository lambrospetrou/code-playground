# Sample app to generate compile ClojureScript to NodeJS module

The output can be used in AWS Lambda, and in general as a Node.js module.

## Compile with Clojure

```bash
clj -m cljs.main --target node --optimizations advanced -c hello-world.core
```

The above generates the `out/` directory which will contain the `main.js` file.

### Compile with Lumo

An alternative way to compiling ClojureScript is using [Lumo](https://github.com/anmonteiro/lumo).

```
# Download Lumo Docker image or install it on your system
docker pull anmonteiro/lumo:latest

# Compile command
docker run -v `pwd`:`pwd` -w `pwd` --rm -it anmonteiro/lumo --classpath src ./build.cljs

# The Docker user by default is root so fix the ownership
sudo chown -R `id -u`:`id -u` main.js
```

This will compile the CLJS code and generate the `out/main.js`, as defined in `build.cljs`, which is the same as the one compiled by Clojure.

## Create the AWS Lambda bundle

```
./build-tools/lambda_bundle.sh
```

Creates the `build/` directory which will contain the lambda bundle zip.
