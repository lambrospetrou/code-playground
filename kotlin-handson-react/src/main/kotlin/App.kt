import kotlinx.css.Color
import kotlinx.css.backgroundColor
import kotlinx.html.InputType
import kotlinx.html.classes
import react.RBuilder
import react.RComponent
import react.RProps
import react.RState
import react.dom.*
import styled.css
import styled.styledDiv

@JsExport
data class Video(val id: Int, val title: String, val speaker: String, val videoUrl: String)

@JsExport
class App : RComponent<RProps, RState>() {
    override fun RBuilder.render() {
        val unwatchedVideos = listOf(
            Video(1, "Building and breaking things", "John Doe", "https://youtu.be/PsaFVLr8t4E"),
            Video(2, "The development process", "Jane Smith", "https://youtu.be/PsaFVLr8t4E"),
            Video(3, "The Web 7.0", "Matt Miller", "https://youtu.be/PsaFVLr8t4E")
        )

        val watchedVideos = listOf(
            Video(4, "Mouseless development", "Tom Jerry", "https://youtu.be/PsaFVLr8t4E")
        )

        h1 {
            +"Hello, React+Kotlin/JS!"
            attrs {
                classes = setOf("header-h1")
            }
        }
        img {
            attrs {
                src = "https://via.placeholder.com/640x360.png?text=Video+Player+Placeholder"
            }
        }
        input {
            attrs {
                type = InputType.text
                defaultValue = ""
                placeholder = "Type something..."
            }
        }
        videoListImplicit(this) {
            videos = unwatchedVideos
        }
        h2(classes = "header-h2 class2") {
            +"Watched Videos"
        }
        videoList {
            videos = watchedVideos
            classes = "global-css-class"
        }

        styledDiv {
            css { backgroundColor = Color.aquamarine; }
            +"Using styledDiv"
        }
    }
}
