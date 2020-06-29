# Java Chat

- sources are located in `src/`

- tests are located in `test/`

- project uses [ant](https://ant.apache.org) to build and test

# Compile and build jar

```
$ ant clientjar
$ ant serverjar
```
jar archives will be built in `build/jar/client.jar` and `build/jar/server.jar`.

It can be built and run with
```
$ ant clientrun
$ ant serverrun
```
To use Json serialization:
```
$ ant jclientrun
$ ant jserverrun
```

Commands to run without building:
```
$ java -jar build/jar/client.jar
$ java -jar build/jar/server.jar
$ java -jar build/jar/client.jar -j
$ java -jar build/jar/server.jar -j
```
# Run tests

```
$ ant test
...
 test:
     [junit] Running ru.nsu.g.mustafin.chat.utils.serializers.JSerializerTest
     [junit] Testsuite: ru.nsu.g.mustafin.chat.utils.serializers.JSerializerTest
     [junit] Tests run: 1, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.073 sec
     [junit] Tests run: 1, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.073 sec
     [junit] 
 BUILD SUCCESSFUL
 Total time: 4 seconds
```
