# Helloworld-java example

- sources are located in `src/`

- tests are located in `test/`

- project uses [ant](https://ant.apache.org) to build and test

# Compile and build jar

```
$ ant jar
```
jar archive will be built in `build/jar/helloworld.jar`.

It can be run with
```
$ java -jar build/jar/helloworld.jar
```

# Run tests

```
$ ant test
...
test:
    [junit] Running HelloTest
    [junit] Testsuite: HelloTest
    [junit] Tests run: 1, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.024 sec
    [junit] Tests run: 1, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.024 sec
    [junit] 

BUILD SUCCESSFUL
Total time: 0 seconds
```
