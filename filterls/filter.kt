fun isValid(s: String): Boolean {
    val segments = s.split(" ", limit = 3)
    if (segments.size < 2) {
        return false
    }
    return segments[1].toIntOrNull()?.let { it > 10 } ?: false
}

fun main(argv: Array<String>) {
    val out = java.io.BufferedWriter(java.io.PrintWriter(System.`out`))

    if (argv.isNotEmpty()) {
        java.io.File(argv[0]).forEachLine {
            if (isValid(it)) { out.write(it); out.newLine() }
        }
    } else {
        //while (true) {
        //    val line = readLine() ?:return
        //    if (isValid(line)) { println(line) }
        //}
        val bufin = java.io.BufferedReader(java.io.InputStreamReader(System.`in`))
        while (true) {
            val line = bufin.readLine() ?: break
            if (isValid(line)) { out.write(line); out.newLine() }
        }
    }

    out.flush()
}
