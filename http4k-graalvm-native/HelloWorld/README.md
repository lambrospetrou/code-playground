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

### Tips

- https://blogs.oracle.com/javamagazine/post/pedal-to-the-metal-high-performance-java-with-graalvm-native-image
- https://www.graalvm.org/reference-manual/native-image/MemoryManagement/
- Use `-XX:+PrintGC -XX:+PrintGCTimeStamps` to get GC information. 
