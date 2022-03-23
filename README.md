# Jackson record showcase

Project illustrating an issue with Jackson's XML related `@JacksonXmlElementWrapper` and `@JacksonXmlProperty` 
annotations when applied to java records. It's tracked by [Issue 517](https://github.com/FasterXML/jackson-dataformat-xml/issues/517)

The project consists of 2 tests, both attempting to deserialize the same XML string. One is using classes, and it 
passes. The other one is using records and fails.

You can run the tests by executing:

```shell
$ mvn clean install
```

The project is built with maven and is using Java 17 and Jackson version 2.13.2.
