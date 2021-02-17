import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlinx.html.id
import kotlinx.html.js.onClickFunction
import kotlinx.html.js.onMouseOverFunction
import org.w3c.dom.HTMLElement
import org.w3c.dom.events.Event
import react.*
import react.dom.p

external interface VideoListProps : RProps {
    var videos: List<Video>
    var classes: String?
}

external interface VideoListState : RState {
    var hoveredVideoId: String?
}

@JsExport
class VideoList : RComponent<VideoListProps, VideoListState>() {

    override fun VideoListState.init() {
        hoveredVideoId = "1000"
    }

    private val onMouseOverHandler: (Event) -> Unit = fun(event: Event) {
        println("Received event type: ${event.type}")
        val localHoveredVideoId = event.target?.let { (it as HTMLElement).getAttribute("id") } ?: "<unknown>"
        setState {
            hoveredVideoId = localHoveredVideoId
        }
    }

    override fun RBuilder.render() {
        console.info("hovered video: ${state.hoveredVideoId}")

        for (video in props.videos) {
            p(classes = props.classes ?: "") {
                key = video.id.toString()
                attrs {
                    id = "${video.id}"
                    onClickFunction = {
                        println("Received event type: ${it.type}")
                        MainScope().launch {
                            val (videoData, json) = fetchVideo(video.id)
                            console.info("video${video.id}: ${videoData.title} - ${videoData.videoUrl}")
                            console.info("video${video.id}: ${json.title} - ${json.videoUrl}")
                        }
                    }
                    onMouseOverFunction = onMouseOverHandler
                }
                +"${video.speaker}: ${video.title}"
            }
        }
    }
}

fun RBuilder.videoList(props: VideoListProps.() -> Unit): ReactElement {
    return child(VideoList::class) {
        this.attrs(props)
    }
}

fun videoListImplicit(rBuilder: RBuilder, props: VideoListProps.() -> Unit): ReactElement {
    return rBuilder.child(VideoList::class) {
        this.attrs(props)
    }
}
