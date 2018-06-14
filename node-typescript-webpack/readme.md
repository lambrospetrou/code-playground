# Node applications written in Typescript (e.g. AWS Lambda)

## Install:

	npm install

### Build minified:

	npm run release

Outputs to `build/bundle.js`

Test that the output can be imported by other modules (i.e. AWS Lambda):

```
node -e "const b = require('./build/bundle.js'); console.log(b); b.handler()"
```
