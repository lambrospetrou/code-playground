Node.js example with ClojureScript with [shadow-cljs](https://github.com/thheller/shadow-cljs)
----

- [Shadow-cljs](http://shadow-cljs.org/)
- [Integrate with NPM modules](https://shadow-cljs.github.io/docs/UsersGuide.html#npm)
- [ClojureScript cheatsheet](https://cljs.info/cheatsheet/)

### Develop

Watch compile with with hot reloading:

```bash
npm run install
npm run watch
```

Start program and connect reload server:

```bash
npm start
```

### REPL

Start a REPL connected to current running program, `app` for the `:build-id`:

```bash
npx shadow-cljs cljs-repl app
```

### Build

```bash
npm run build
```

Compiles to `build/main.js`.

You may find more configurations on http://doc.shadow-cljs.org/ .

### License

MIT
