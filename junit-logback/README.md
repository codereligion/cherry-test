# ![cherry-logo](https://raw.githubusercontent.com/codereligion/cherry/master/small-cherry.png) JUnit Logback [![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.codereligion/codereligion-cherry-junit-logback/badge.svg?style=plastic)](https://maven-badges.herokuapp.com/maven-central/com.codereligion/codereligion-cherry-junit-logback)

This module contains a JUnit rule to record logback logging events and it works tightly together with [hamcrest-logback](https://github.com/codereligion/cherry-test/tree/master/hamcrest-logback)

## Usage
```java
@Rule
public LogRecorder logRecorder = LogRecorder.expectedLogs(new LogSpec(SomeType.class, Level.ERROR));

@Test
public void shouldLogWhenDependentComponentFails() {

  // given 
  someDependentComponent.failsToRun();

  // when
  someComponent.runsBusinessLogic();
  
  // then
  ILoggingEvent event = logRecorder.event();
  assertThat(event, hasLevel(Level.ERROR));
  assertThat(event, hasMessage("someDependentComponent failed"));
}
```
