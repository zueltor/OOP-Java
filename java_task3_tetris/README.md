# Tetris-java MVC

- sources are located in `src/`

- tests are located in `test/`

- project uses [ant](https://ant.apache.org) to build and test

# Compile and build jar

```
$ ant guijar
$ ant clijar
```
jar archive will be built in `build/jar/guitetris.jar` and `build/jar/clitetris.jar`.

It can be run with
```
$ java -jar build/jar/guitetris.jar
$ java -jar build/jar/clitetris.jar
```

# Run tests

```
$ ant test
...
 test:
     [junit] Running ru.nsu.g.mustafin.tetris.model.ModelTest
     [junit] Testsuite: ru.nsu.g.mustafin.tetris.model.ModelTest
     [junit] Tests run: 2, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.063 sec
     [junit] Tests run: 2, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.063 sec
     [junit] 

BUILD SUCCESSFUL
Total time: 4 seconds
```
