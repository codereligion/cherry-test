# ![cherry-logo](https://raw.githubusercontent.com/codereligion/cherry/master/small-cherry.png) JUnit Joda-Time [![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.codereligion/codereligion-cherry-junit-joda-time/badge.svg?style=plastic)](https://maven-badges.herokuapp.com/maven-central/com.codereligion/codereligion-cherry-junit-joda-time)

This module contains a JUnit rule which allows to manipulate the time used by Joda-Time classes when it tries to get the "current time".

## Usage
```java
@Rule
public TimeMachine timeMachine = new TimeMachine();

@Test
public void testSomeCodeWhichDependsOnJodaTimeNow() {

  // given
  final DateTime now = someTimeThatMakesSenseInThisUsecase();
  timeMachine.goToAndStayAt(now);
  
  // when
  SomeDto someDto = someComponent.runsBusinessLogic();
  
  // then
  assertThat(someDto.getTime(), is(now.plusMinutes(5))); 
}
```
