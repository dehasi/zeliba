# ZeLiba (The library)

A handy DSL-ish helper to make the comparison more readable.

[![Build Status](https://www.travis-ci.org/dehasi/zeliba.svg?branch=master)](https://www.travis-ci.org/dehasi/zeliba)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/me.dehasi/zeliba/badge.svg)](https://maven-badges.herokuapp.com/maven-central/me.dehasi/zeliba)
[![Test Coverage](https://img.shields.io/codecov/c/github/dehasi/zeliba/master)](https://codecov.io/github/dehasi/zeliba?branch=master)
![GitHub code size in bytes](https://img.shields.io/github/languages/code-size/dehasi/zeliba)

Zeliba provides a fluent API to write comparison (for `Comparable<T>`) and does other checks.

- [Motivation](#Motivation)
- [Examples](#Examples)
  - [TheComparable](#TheComparable)
  - [TheChronoLocalDate](#TheChronoLocalDate)
  -  [TheChonoDateTime](#TheChonoDateTime)
  - [TheObject](#TheObject)
  - [TheCollection](#TheCollection)
  - [TheMap](#TheMap)
- [License](#License)
- [Installation](#Installation)
  - [Maven](#Maven)
  - [Gradle](#Gradle)
- [Contribution](#Contribution)

## Motivation
Zeliba main points are the following:
* Provide a fluent API to write a comparison (for `Comparable<T>`)
* Make `if`-checks better align with English grammar

Inspired by [AssertJ](https://joel-costigliola.github.io/assertj/)

### Fluent compatible  
Java doesn't support operator overloading, you can’t write something like `a > b` for objects,
as an alternative you can use  `Comparable<T>`. It makes its job, but it is not very convenient to use.   
Look  `a.compareTo(b) > ??`.
Every time you need to make small calculations in your head. It’s better (from readability POV) 
to write `a.isGreatherThan(b)`. Zeliba gives you the ability to do it.  
See examples [TheComparable](#TheComparable), [TheChronoLocalDate](#TheChronoLocalDate), [TheChonoDateTime](#TheChonoDateTime)

### Better English  
Usually, util methods start with `is` prefix (like `isEmpty`), but negations are covered via exclamation mark `!is`, 
which looks grammatically incorrect in code. I.e. “if a collection is empty” transforms into 
`collection.isEmpty()`, but “if a collection is *not* empty” transforms into `!collection.isEmpty()` 
which is read as “not the collection is empty”. It is obviously grammatically incorrect. 
Util methods like `if(isNotEmpty(collection))` do a great job but still remain grammatically incorrect.
 We don’t say “if is not an empty collection”. 
 Zeliba provides the same util methods but also gives you a fluent API to write grammatically correct
  code.  
See examples. [TheObject](#TheObject), [TheCollection](#TheCollection), [TheMap](#TheMap)

## Examples

The examples reflect the master branch.

### TheComparable

Let's assume we have two comparable objects.
```java
BigDecimal val1 = ...
BigDecimal val2 = ...
```

Usually we check `val1 > val2` like `if (val1.compareTo(val2) > 0)`

But with `TheComparable` it's much easier to read

```java
if (the(val1).isGreaterThan(val2)) {
    ...
}

if (the(val2).isLessThan(val1)) {
    ...
}
```


Fluent interval checks 

`val1 <= value <= val2`

```java
if (the(value).isInTheInterval().fromIncluded(val1).toIncluded(val2)) {
    //...
}
```

`val1 < value < val2`

```java
if (the(value).isInTheInterval().fromExcluded(val1).toExcluded(val2)) {
    //...
}
```


`val1 < value <= val2`

```java
if (the(value).isInTheInterval().fromExcluded(val1).toIncluded(val2)) {
    //...
}
```

### TheChronoLocalDate
   
Also, there are extensions to compare dates
   
   ```java
LocalDate someDate = ...
LocalDate otherDate = ...

if (the(otherDate).isAfterOrEqual(someDate)) {
    ...
}
   
if (the(someDate).isBeforeOrEqual(otherDate)) {
    ...
}
   ```
   
### TheChronoLocalDateTime

The same for `DateTime`

```java
LocalDateTime someDateTime = ...
LocalDateTime otherDateTime = ...

if (the(otherDate).isAfterOrEqual(someDateTime)) {
    ...
}

if (the(someDate).isBeforeOrEqual(otherDateTime)) {
    ...
}
```

### TheObject

Fluent null and not equals checks

```java
Object someObject = ...
Object otherObject = ...

if (the(otherObject).isNotEqualTo(someObject)) {
    ...
}

if (the(someObject).isNotNull()) {
    ...
}

if (the(someObject).isNull()) {
    ...
}
```


### TheCollection

Grammatically correct fluent checks if a collection is null or is not empty

```java
List<?> list = ...
Set<?>  otherSet = ...

if (the(list).isNotEmpty()) {
    ...
}

if (the(otherSet).isNullOrEmpty()) {
    ...
}
```

### TheMap

```java
Map<?,?>  map = ...

if (the(map).isNotEmpty()) {
    ...
}
```

### License

This project is licensed under [Apache License, version 2.0](https://www.apache.org/licenses/LICENSE-2.0)

### Installation

Releases are available in [Maven Central](https://repo1.maven.org/maven2/me/dehasi/zeliba/)

#### Maven

Add this snippet to the pom.xml `dependencies` section:

```xml
<dependency>
    <groupId>me.dehasi</groupId>
    <artifactId>zeliba</artifactId>
    <version>2020.02.21</version>
</dependency>
```

#### Gradle 

Add this snippet to the build.gradle `dependencies` section:

```groovy
implementation 'me.dehasi:zeliba:2020.02.21'
```

### Contribution
Feel free to share your ideas via issues and pull-requests.
