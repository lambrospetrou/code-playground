@file:JsModule("preact")
@file:JsNonModule
package preact

import org.w3c.dom.Element

external fun render(vnode: Any, container: Element): Unit
external fun h(type: String, props: dynamic, children: Any?): dynamic
