# ![cherry-logo](https://raw.githubusercontent.com/codereligion/cherry/master/small-cherry.png) Hamcrest [![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.codereligion/codereligion-cherry-hamcrest/badge.svg?style=plastic)](https://maven-badges.herokuapp.com/maven-central/com.codereligion/codereligion-cherry-hamcrest)

This module contains plain java extensions for Hamcrest.

## Usage

### Strings
```java
// contains assertion with varargs
assertThat("The bunny jumps over the fence", containsString("The %s jumps over the fence", "bunny"));
```
