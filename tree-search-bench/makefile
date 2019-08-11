.PHONY: js clean run

default: clean js rb

all: clean js rb

clean:
	rm -rf build/ && mkdir -p build/

js: clean
	cd typescript && npm run build
	cp typescript/deepest-node.js build/

rb: clean
	cp deepest-node.rb build/

run:
	rm -rf output/ && mkdir -p output
	./run-all.rb

generate:
	node --max-old-space-size=8192 -e "console.log(JSON.stringify(require('./generate-tree').genBinary(5, 1, 1), null, ' '))" > test-files/xsmall.json
	node --max-old-space-size=8192 -e "console.log(JSON.stringify(require('./generate-tree').genBinary(15, 1, 1), null, ' '))" > test-files/small.json
	node --max-old-space-size=8192 -e "console.log(JSON.stringify(require('./generate-tree').genBinary(20, 0.9, 0.8), null, ' '))" > test-files/medium.json
	node --max-old-space-size=8192 -e "console.log(JSON.stringify(require('./generate-tree').genBinary(25, 0.9, 0.8), null, ' '))" > test-files/large.json
	node --max-old-space-size=8192 -e "console.log(JSON.stringify(require('./generate-tree').genBinary(30, 0.9, 0.8), null, ' '))" > test-files/xlarge.json
