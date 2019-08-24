Node.js example with ClojureScript with [shadow-cljs](https://github.com/thheller/shadow-cljs)
----

- [Shadow-cljs](http://shadow-cljs.org/)
- [Integrate with NPM modules](https://shadow-cljs.github.io/docs/UsersGuide.html#npm)
- [ClojureScript cheatsheet](https://cljs.info/cheatsheet/)

### Develop

Install all dependencies.

```bash
npm run install
```

Watch compile with with hot reloading, using the `browser` target as example.

```
npm run watch:browser
```

Start program and connect reload server:

```bash
npm run start:browser
```

### Production release

Build production builds for each target.

```bash
# Build all targets
npm run release
# or
npm run release:app
npm run release:library
npm run release:browser
```

#### Browser

```
npm run release:browser
```

This will generate a file inside `build/browser/browser-main.js`, and copy a static `index.html` which loads the script.

The `browser-main.js` file contains your JavaScript logic, so copy that into your server and load it into your main website HTML.

- [Browser target](https://shadow-cljs.github.io/docs/UsersGuide.html#target-browser)

#### Node application

```
npm run release:app
```

Compiles the Node app, `:app` target, to `build/node-app.js`.

- [Node application target](https://shadow-cljs.github.io/docs/UsersGuide.html#target-node-script)

#### Library

Apart from the main Node script/app generated, we can also generate a library in the standard `commonjs` format that can be used in other Node/JavaScript projects, or as a handler in AWS Lambda.

```
npm run release:library
```

- [Node library target](https://shadow-cljs.github.io/docs/UsersGuide.html#target-node-library)
- [Multiple named exports](https://shadow-cljs.github.io/docs/UsersGuide.html#_multiple_static_named_exports)

### REPL

Start a REPL connected to current running program, `app` for the `:build-id`:

```bash
npx shadow-cljs cljs-repl app
```

### Run a script on the command line before building

Using [Lumo-cljs](https://github.com/anmonteiro/lumo) allows us to run scripts before fully building with `shadow-cljs` to test things out.

The `lumo -m <ns-namespace>` command tries to execute the `-main` function inside the given `<ns-namespace>`.

```
npx lumo -c "src" -m "server.main"
```

### License

MIT
