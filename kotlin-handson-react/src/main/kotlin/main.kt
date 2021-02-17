import kotlinext.js.jsObject
import kotlinx.browser.document
import kotlinx.browser.window
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.await
import kotlinx.coroutines.launch
import kotlinx.css.*
import react.dom.render

suspend fun fetchVideo(id: Int): Pair<Video, dynamic> {
    val promise = window.fetch("https://my-json-server.typicode.com/kotlin-hands-on/kotlinconf-json/videos/$id").await()
    val json = promise.json().await()
    return Pair(json.unsafeCast<Video>(), json!!)
}

fun main() {
    GlobalScope.launch {
        val (videoData, json) = fetchVideo(1)
        console.info("unsafeCast: ${videoData.title} - ${videoData.videoUrl}")
        console.info("dynamic: ${json.title} - ${json.videoUrl}")
    }

    render(document.getElementById("root")) {
        child(App::class) {

        }
    }

    renderPreact(document.querySelector("#preact-app")!!)

    val styles = CSSBuilder(allowClasses = true).apply {
        kotlinx.css.body {
            margin(0.px)
            padding(0.px)
        }
        rule(".global-css-class") {
            backgroundColor = Color.aqua
            color = Color("#920000")
        }
    }
    styled.injectGlobal(styles.toString())
}
