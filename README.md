# ZeLiba (The library)

A handy DSL-ish helper to make the comparison more readable.

[![Build Status](https://www.travis-ci.org/dehasi/zeliba.svg?branch=master)](https://www.travis-ci.org/dehasi/zeliba)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/me.dehasi/zeliba/badge.svg)](https://maven-badges.herokuapp.com/maven-central/me.dehasi/zeliba)
[![Test Coverage](https://img.shields.io/codecov/c/github/dehasi/zeliba/master)](https://codecov.io/github/dehasi/zeliba?branch=master)
![GitHub code size in bytes](https://img.shields.io/github/languages/code-size/dehasi/zeliba)
```xml
<dependency>
    <groupId>me.dehasi</groupId>
    <artifactId>zeliba</artifactId>
    <version>2020.02.20</version>
</dependency>

```



Zeliba is an open-source library 

It provides a fluent API to write comparison (via `Comparable<T>`) and do other checks.

Zeliba main points are the following:
* Provide a fluent API to write comparison (via `Comparable<T>`)
* Make `if`-checks better align with English grammar

Fluent compatible  
Java doesn't support operator overloading, you can’t write smth like `a > b` for objects,  as an alternative you can use  `Comparable<T>`. It makes its job, but it’s so convenient to use. Look  `a.compareTo(b) > ??`.
Every time you need to make small calculations in your head. It’s better (from readability POV) 
to write `a.isGreatherThan(b)`. Zeliba gives you the ability to do it.  
See examples [TheComparable](#TheComparable), [TheChronoLocalDate](#TheChronoLocalDate), [TheChonoDateTime](#TheChonoDateTime)

Better English  
Usually, util methods start with `is` prefix, but negations are covered via exclamation mark `!is`, 
which looks grammatically incorrect in code. I.e. “if a collection is empty” transforms into 
`collection.isEmpty()`, but “if a collection is *not* empty” transforms into `!collection.isEmpty()` 
which is read as “not the collection is empty” which is obviously grammatically incorrect. 
Util methods like `if(isNotEmpty(collection))` do a great job but still remain grammatically incorrect.
 We don’t say “if is not an empty collection”. 
 Zeliba provides the same util methods but also gives you a fluent API to write grammatically correct
  code.  
See examples. [TheObject](#TheObject], [TheCollection](#TheCollection), [TheMap](#TheMap)

## Examples

### TheComparable

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

### TheChronoLocalDate
   
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
   
### TheChronoLocalDateTime

Also there are extensions to compare dates

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

Grammatically correct fluent checks if collection is null or is not empty

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
