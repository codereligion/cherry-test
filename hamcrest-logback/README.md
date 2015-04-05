# ![cherry-logo](https://raw.githubusercontent.com/codereligion/cherry/master/small-cherry.png) Hamcrest Logback [![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.codereligion/codereligion-cherry-hamcrest-logback/badge.svg?style=plastic)](https://maven-badges.herokuapp.com/maven-central/com.codereligion/codereligion-cherry-hamcrest-logback)

This module contains Hamcrest matchers for logback which works tightly together with [junit-logback](https://github.com/codereligion/cherry-test/tree/master/junit-logback). There are matchers for the following logging event properties:

* log level (```LoggingEventHasLevel```)
* message (```LoggingEventHasMessage```)
* associated throwable (```LoggingEventHasThrowable```)
* logger (```LoggingEventLoggedBy```)

Additionally there is a specific iterable matcher ```LoggingEventIterableHasItem``` which is typed to ```ILoggingEvent``` to avoid unnecessary generic annoyance and to bring some improved error descriptions.

* for a single event
* negation for a single event
* for an iterable of events

## Usage

### Level assertions
```java 
assertThat(event, hasLevel(Level.ERROR));
assertThat(event, doesNotHaveLevel(Level.ERROR));
assertThat(events, hasItem(withLevel(Level.ERROR)));
```
Example error output:
```
Expected: an ILoggingEvent with level: ERROR
     but: was ILoggingEvent{level=INFO, formattedMessage='some Message', loggedBy=SomeLogger, throwable=null}
```

### Message assertions
The matcher comes with static methods which allow to provider either a string matcher or directly a string with optional varargs to be used for formatting the the given string before matching.
```java
assertThat(event, hasMessage("some message"));
assertThat(event, hasMessage(startsWith("some message")));
assertThat(event, doesNotHaveMessage("some message"));
assertThat(event, doesNotHaveMessage(startsWith("some message")));
assertThat(events, hasItem(withMessage("some message")));
assertThat(events, hasItem(withMessage(startsWith("some message"))));
```
Example error output:
```
Expected: an ILoggingEvent with a formattedMessage matching: a string containing "some message"
     but: was ILoggingEvent{level=INFO, formattedMessage='oh no!', loggedBy=SomeLogger, throwable=null}
```

### Throwable assertions
```java
assertThat(event, hasThrowable(new RuntimeException("opsi!")));
assertThat(event, doesNotHaveThrowable(new RuntimeException("opsi!")));
assertThat(events, hasItem(withThrowable(new RuntimeException("opsi!"))));
```
Example error output:
```
Expected: an ILoggingEvent with a throwable matching: java.lang.RuntimeException{message='opsi!'} 
     but: was ILoggingEvent{level=ERROR, formattedMessage='some Message', loggedBy=SomeLogger, throwable=java.lang.RuntimeException{message='nope!'}}
```

### Logger assertions
```java
assertThat(event, wasLoggedBy("SomeLogger"));
assertThat(event, wasLoggedBy(SomeType.class));
assertThat(event, wasNotLoggedBy("SomeLogger"));
assertThat(event, wasNotLoggedBy(SomeType.class));
assertThat(events, hasItem(loggedBy("SomeLogger")));
assertThat(events, hasItem(loggedBy(SomeType.class)));
```
Example error output:
```
Expected: an ILoggingEvent logged by: SomeLogger 
     but: was ILoggingEvent{level=ERROR, formattedMessage='some Message', loggedBy=SomeOtherLogger, throwable=null}
```

### Iterable assertions
```java
assertThat(events, hasItem(loggedBy("SomeLogger")));
assertThat(events, hasNoItem(loggedBy("SomeLogger")));
```
Example error output:
```
Expected: an iterable containing an ILoggingEvent with level: ERROR 
     but: was [ILoggingEvent{level=INFO, formattedMessage='some Message', loggedBy=SomeLogger, throwable=null}]
```
