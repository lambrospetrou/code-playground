package main

import (
	"encoding/json"
	"fmt"
	"log"
	"os/exec"

	"rogchap.com/v8go"
)

func repeatInt(a []int, cnt int) []int {
	for cnt > 0 {
		a = append(a, a...)
		cnt--
	}
	return a
}

func generateScript(cLen int) string {
	src := `
	const f = (a, b, c) => {
		return a.reduce((c, acc) => acc + c, b) + c.length;
	};
	result = f(%+v, %+v, %+v);
	result`
	aJSON, _ := json.Marshal(repeatInt([]int{1, 2, 3, 4, 5}, cLen))
	bJSON, _ := json.Marshal(10)
	cJSON, _ := json.Marshal("hello")
	srcReplaced := fmt.Sprintf(src, string(aJSON), string(bJSON), string(cJSON))
	// fmt.Println(">", srcReplaced, "<")
	return srcReplaced
}

func v8isolates(script string, isolateOpt ...*v8go.Isolate) string {
	var isolate *v8go.Isolate
	if len(isolateOpt) > 0 {
		isolate = isolateOpt[0]
	}
	ctx, _ := v8go.NewContext(isolate)
	defer ctx.Close()
	output, e := ctx.RunScript(script, "function.js")
	if e != nil {
		log.Fatalf("Error: %+v\n", e)
	}
	return output.String()
}

func node(script string) string {
	output, e := exec.Command("node", "-e", script).Output()
	if e != nil {
		log.Fatalf("Error: %+v\n", e)
	}
	return string(output)
}

func main() {
	script := generateScript(2)

	fmt.Println("Using rogchap.com/v8go")
	fmt.Println(v8isolates(script))

	fmt.Println("Using node subprocess")
	fmt.Println(node(script + "\nconsole.log(result);"))
}
