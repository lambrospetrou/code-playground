import com.lambrospetrou.hello.Hello
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.util.concurrent.atomic.AtomicLong
import kotlin.test.Test
import kotlin.test.assertEquals

class HelloTest {
    @Test
    fun whenWorld() {
        assertEquals("hello, world", Hello().world())
    }

    @Test
    fun whenWorld2() {
        assertEquals("hello, world 2", Hello().world2())
    }

    @Test
    fun testCoroutinesMultiConsumers() {
        runBlocking {
            println("Hello,")
            val times = 1_000_000

            val channel = Channel<Int>(times)
            launch {
                repeat(times) {
                    if (it % (times / 10) == 0) println("$it")
                    channel.send(it)
                }
                channel.close()
            }

            val atomicLong = AtomicLong(0)
            runBlocking {
                repeat(4) {
                    launch {
                        var total = 0L
                        for (x in channel) {
                            total += x
                        }
                        atomicLong.getAndUpdate { a -> a + total }
                    }
                }
            }

            println("total sum: $atomicLong")
            assertEquals(499999500000, atomicLong.get())
        }
        println(" world!")
    }
}
