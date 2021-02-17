import kotlinx.browser.document
import kotlinext.js.jsObject
import kotlinx.html.classes
import kotlinx.html.div
import kotlinx.html.p
import kotlinx.html.stream.appendHTML

import preact.h
import preact.render

fun main() {

    val htmlContent = buildString {
        appendHTML(false).div("outer-container") {
            div {
                classes = setOf("boom-css another-one")
                p {
                    +"Hello world"
                }
            }
        }
    }

    document.body?.querySelector("#raw-html")?.innerHTML = htmlContent

    render(h(type = "div", props = jsObject {
        className = "preact-div-class"
    }, children = "hello from Preact!"), document.querySelector("#app")!!)
}
