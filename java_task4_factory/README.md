# Factory

- sources are located in `src/`

- tests are located in `test/`

- project uses [ant](https://ant.apache.org) to build and test

# Compile and build jar

```
$ ant jar
```
jar archive will be built in `build/jar/factory.jar`.

It can be run with
```
$ java -jar build/jar/factory.jar
```

Or it can be built and run with
```
$ ant run
```
# Run tests

```
$ ant test
...
  test:
     [junit] Running ru.nsu.g.mustafin.factory.details.CarTest
     [junit] Testsuite: ru.nsu.g.mustafin.factory.details.CarTest
     [junit] Tests run: 1, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.062 sec
     [junit] Tests run: 1, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.062 sec
     [junit] 
 BUILD SUCCESSFUL
 Total time: 4 seconds
```