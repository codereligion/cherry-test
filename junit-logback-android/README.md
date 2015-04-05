# ![cherry-logo](https://raw.githubusercontent.com/codereligion/cherry/master/small-cherry.png) Hamcrest Logback for Andriod [![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.codereligion/codereligion-cherry-junit-logback-android/badge.svg?style=plastic)](https://maven-badges.herokuapp.com/maven-central/com.codereligion/codereligion-cherry-junit-logback-android)

This module contains a JUnit rule to record logback logging events and it works tightly together with [hamcrest-logback-android](https://github.com/codereligion/cherry-test/tree/master/hamcrest-logback-android)

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
