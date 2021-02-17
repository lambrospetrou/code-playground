import kotlinext.js.jsObject
import org.w3c.dom.Element

import preact.h
import preact.render

fun renderPreact(container: Element) {
    render(h(type = "div", props = jsObject {
        className = "preact-div-class"
    }, children = "hello from Preact!"), container)
}
