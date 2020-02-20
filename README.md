# ZeLiba (The library)

A handy DSL-ish helper to make the comparison more readable.

[![Build Status](https://www.travis-ci.org/dehasi/zeliba.svg?branch=master)](https://www.travis-ci.org/dehasi/zeliba)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/me.dehasi/zeliba/badge.svg)](https://maven-badges.herokuapp.com/maven-central/me.dehasi/zeliba)
[![Test Coverage](https://img.shields.io/codecov/c/github/dehasi/zeliba/master)](https://codecov.io/github/dehasi/zeliba?branch=master)

```xml
<dependency>
    <groupId>me.dehasi</groupId>
    <artifactId>zeliba</artifactId>
    <version>2020.02.20</version>
</dependency>

```

## Examples

### Comparable<T>

Let's assume we have two comparable objects.
```java
BigDecimal val1 = ...
BigDecimal val2 = ...
```

Usually we check `val1 > val2` like

```java
if (val1.compareTo(val2) > 0) {
    ...
}
```

But with `TheComparable` it's easier to read

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

### TheChronoLocalDate<T>

Also there are extensions to compare dates

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

Check if collection is not empty

```java
List<?> list = ...
Set<?>  otherSet = ...
Map<?,?>  map = ...

if (the(list).isNotEmpty()) {
    ...
}

if (the(otherSet).isNullOrEmpty()) {
    ...
}

if (the(map).isNotEmpty()) {
    ...
}
```
