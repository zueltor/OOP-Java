# Phrases-java

- sources are located in `src/`

- tests are located in `test/`

- project uses [ant](https://ant.apache.org) to build and test

# Compile and build jar

```
$ ant jar
```
jar archive will be built in `build/jar/phrases.jar`.

It can be run with
```
$ java -jar build/jar/phrases.jar
```

# Run tests

```
$ ant test
...
test:
        [junit] Running ru.nsu.g.mustafin.phrases.ParserTest
        [junit] Testsuite: ru.nsu.g.mustafin.phrases.ParserTest
        [junit] Tests run: 1, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.033 sec
        [junit] Tests run: 1, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.033 sec
        [junit] 
        [junit] Running ru.nsu.g.mustafin.phrases.PhrasesTest
        [junit] Testsuite: ru.nsu.g.mustafin.phrases.PhrasesTest
        [junit] Tests run: 2, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.042 sec
        [junit] Tests run: 2, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.042 sec
        [junit] 

BUILD SUCCESSFUL
 Total time: 3 seconds
```
