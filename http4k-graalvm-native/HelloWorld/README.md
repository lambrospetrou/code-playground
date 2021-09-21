# HelloWorld

## Package
```
./gradlew shadowJar
```

To run the fat-Jar:
```
java -XX:ActiveProcessorCount=2 -Xmx32M -jar build/libs/HelloWorld.jar
```

- `-XX:ActiveProcessorCount` modifies the number of processors to be returned by `Runtime.getRuntime().availableProcessors()`.
- `-Xmx32M` sets the maximum HEAP size to 32Mb. Note, that this is not the total memory, just the heap.

## GraalVM Native Image

(Once) Build the Docker image that contains the GraalVM runtime:
```
./gradlew buildNativeImageDocker
```

Run this to build the native binary of the fat-Jar:
```
./gradlew nativeImageDocker
```

Run this to build the native binary of the fat-Jar on a Mac:
```
./gradlew nativeImage
```
(For this to work you need to install GraalVM and `native-image`, ideally with SDK-man.)

Strip the binary even more (`brew install upx`):
```
upx -7 -k build/libs/HelloWorld-native
```

### Tips

- https://blogs.oracle.com/javamagazine/post/pedal-to-the-metal-high-performance-java-with-graalvm-native-image
- https://www.graalvm.org/reference-manual/native-image/MemoryManagement/
- Use `-XX:+PrintGC -XX:+PrintGCTimeStamps` to get GC information. 

## Example runs

Start the servers:
```shell
# terminal 1
PORT=9001 ./build/libs/HelloWorld-native -XX:ActiveProcessorCount=4

# terminal 2
PORT=9000 java -XX:ActiveProcessorCount=4 -jar build/libs/HelloWorld.jar
```

Benchmark:
```
➜  HelloWorld git:(master) ✗ wrk -t2 -c100 -d10s --latency --timeout 1s http://localhost:9001/ping
Running 10s test @ http://localhost:9001/ping
  2 threads and 100 connections
  Thread Stats   Avg      Stdev     Max   +/- Stdev
    Latency     1.58ms  341.79us   5.52ms   76.78%
    Req/Sec    31.45k     4.73k   40.38k    65.84%
  Latency Distribution
     50%    1.49ms
     75%    1.78ms
     90%    2.03ms
     99%    2.67ms
  631830 requests in 10.10s, 82.55MB read
Requests/sec:  62547.18
Transfer/sec:      8.17MB
➜  HelloWorld git:(master) ✗ wrk -t2 -c100 -d10s --latency --timeout 1s http://localhost:9000/ping
Running 10s test @ http://localhost:9000/ping
  2 threads and 100 connections
  Thread Stats   Avg      Stdev     Max   +/- Stdev
    Latency     1.26ms  247.94us  17.20ms   92.46%
    Req/Sec    39.59k     3.26k   43.56k    88.12%
  Latency Distribution
     50%    1.20ms
     75%    1.25ms
     90%    1.43ms
     99%    2.09ms
  795747 requests in 10.10s, 108.52MB read
Requests/sec:  78769.49
Transfer/sec:     10.74MB
➜  HelloWorld git:(master) ✗ ps -eo pid,rss,%mem,%cpu,command | grep HelloWorld | grep -v "grep"
90176 285248  0.9   0.0 ./build/libs/HelloWorld-native -XX:ActiveProcessorCount=4
90372 661100  2.0   0.0 java -XX:ActiveProcessorCount=4 -jar build/libs/HelloWorld.jar
```
