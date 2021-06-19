# ZeLiba (The library)

A handy DSL-ish helper to make the comparison more readable.

[![Build Status](https://www.travis-ci.org/dehasi/zeliba.svg?branch=master)](https://www.travis-ci.org/dehasi/zeliba)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/me.dehasi/zeliba/badge.svg)](https://maven-badges.herokuapp.com/maven-central/me.dehasi/zeliba)
[![Test Coverage](https://img.shields.io/codecov/c/github/dehasi/zeliba/master)](https://codecov.io/github/dehasi/zeliba?branch=master)
![GitHub code size in bytes](https://img.shields.io/github/languages/code-size/dehasi/zeliba)

Zeliba provides a fluent API to write a comparison (for `Comparable<T>`) and does other checks.

- [Motivation](#Motivation)
- [Examples](#Examples)
  - [TheComparable](#TheComparable)
  - [TheChronoLocalDate](#TheChronoLocalDate)
  - [TheChronoLocalDateTime](#TheChronoLocalDateTime)
  - [TheObject](#TheObject)
  - [TheString](#TheString)
    - [isEmpty](#isEmpty)
    - [isBlank](#isBlank)
    - [substring](#substring)
  - [TheCollection](#TheCollection)
  - [TheMap](#TheMap)
    - [contains](#contains)
    - [Optional get](#Optional-get)
  - [When](#When)
    - [is](#is)
    - [isNot](#isNot)
    - [and](#and)
    - [or](#or)
    - [then](#then)
    - [orElse](#orElse)
    - [orElseThrow](#orElseThrow)
    - [asOptional](#asOptional)
    - [Complex example](#Complex-example)
  - [When2](#When2)
- [License](#License)
- [Installation](#Installation)
  - [Maven](#Maven)
  - [Gradle](#Gradle)
- [Contribution](#Contribution)

## Motivation
Zeliba main points are the following:
* Provide a fluent API to write a comparison (for `Comparable<T>`)
* Make `if`-checks better align with English grammar
* Provide pattern matching for `Java 8`

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
which also makes you do calculations. I.e. “if a collection is empty” transforms into 
`collection.isEmpty()`, but “if a collection is *not* empty” transforms into `!collection.isEmpty()` 
which is read as “not the collection is empty”. It is obviously grammatically incorrect. 
Util methods like `if(isNotEmpty(collection))` do a great job but still remain grammatically incorrect.
 We don’t say “if is not an empty collection”. 
 Zeliba provides the same methods but also gives you a fluent API to write grammatically correct
  code.  
See examples. [TheObject](#TheObject), [TheCollection](#TheCollection), [TheMap](#TheMap)

### Pattern matching
Inspired by [when](https://kotlinlang.org/docs/reference/control-flow.html#when-expression) from `Kotlin`.  
Since [Java 12](https://openjdk.java.net/jeps/325) `case`-expressions were extended. But `Java 8` is still
widely used and it's nice to have some fluent API which is more useful than `case` for pattern matching.
Zeliba provides some pattern-matching features.  
See [When](#When)

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
   
if (the(someDate).isNotAfter(otherDate)) {
    ...
}

if (the(otherDate).isBeforeOrEqual(someDate)) {
    ...
}
   
if (the(someDate).isNotBefore(otherDate)) {
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

if (the(someDate).isNotAfter(otherDateTime)) {
    ...
}

if (the(otherDate).isBeforeOrEqual(someDateTime)) {
    ...
}

if (the(someDate).isNotBefore(otherDateTime)) {
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

### TheString

Fluent checks for empty/blank + avoiding `NPE`


####isEmpty


```Java
String str1 = null;
String str2 = "abcd";

if (the(str1).isEmpty()) { ... } // returns false
if (the(str2).isNotEmpty()) { ... } // returns true
```


#### isBlank
```Java
String str1 = null;
String str2 = "abcd";

if (the(str1).isBlank()) { ... } // returns true
if (the(str2).isNotBlank()) { ... } // returns false
```


#### substring
Max possible substring

```java
String str = "abcd"

String s = the(str).substring(2, 50); // returns "cd"
String s = the(str).substring(-2, 2); // returns "ab"
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
Pair<?,?> pair = ... // Apache Commons Pair<> or any Map.Entry<>

if (the(map).isNotEmpty()) {
    ...
}
```
#### contains
Fluent `contains` checks to check if a map contains the particular entry. 
Or if the particular key has the particular value.
```java
if (the(map).contains(pair)) {
    ...
}

if (the(map).contains(key, value)) {
    ...
}

if (the(map).contains(entry(key, value))) {
    ...
}
```
#### Optional `get`
`Map.get(key)` returns `null` if there is no value. TheMap allows to a map return an `Optional<>`.
```java
Optional<?> value = the(map).get(key)
```

### When

Pattern-ish matching in pure `Java 8`

```java
int value = ...

String result = when(value)
    .is(1).then("+")
    .is(0).then("zero")
    .is(-1).then("-")
    .orElse("?"); 
```

The `when` returns value from the first matched predicate.

```java
int value = 42

String result = when(value)
    .is(42).then("first_42") //result=first_42
    .is(42).then("second_42")
    .orElse("?"); 
```

#### is
The `is` part accepts `Predicate` or a value which be compared as `Objects.equals`

```java
String result = when(value)
    .is(v -> v > 0).then("+")
    .is(0).then("zero") // Objects.equals(0, value)
    .is(v -> v < 0).then("-")
    .orElse("?"); 
```
#### isNot
There is an opposise predicate `isNot`
```java
String result = when(value)
    .isNot(42).then("not 42")
    .orElse("42 for sure"); 
```

#### and

To make a conjunction of few `is`-predicates, `and` can be used.
```java
int value = 5;

String result = when(value)
    .is(v -> v > 0).and(v-> v < 3).then("(0..3)")
    .is(v -> v > 3).and(v-> v < 7).then("(3..7)")
    .orElse("?");
```
#### or

To make a disjunction of few `is`-predicates, `or` can be used.
```java
int value = 5;

String result = when(value)
    .is(0).or(2).or(4).then("0 or 2 or 4")
    .is(1).or(3).or(5).then("1 or 3 or 5")
    .orElse("?");
```

`or` and `and` can be used together

```java
int value = 5;

String result = when(value)
    .is(1).or(2).then("< 3")
    .is(v -> v > 6).and(v -> v < 10).or(5).then("(6;10) or 5")
    .is(v -> v > 0).and(v -> v < 5)
        .or(v -> v > 5).and(v -> v < 10).then("(0;5) or (5;10)")
    .orElse("?");
```

#### then
`then` part accepts a value, `Supplier` or `Function`.
The function accepts the initial value.

```java
int value = ...

String result = when(value)
    .is(1).then("+")
    .is(0).then(() -> "zero")
    .is(v -> v < 0).then(val -> String.valueOf(Math.abs(val))) // string of abs(value)
    .orElse("?"); 
```

It is also possible to throw an exception from `then` part

```java
int value = ...

String result = when(value)
    .is(1).then("+")
    .is(0).then(() -> {
        throw new RuntimeException();
    })
    .orElse("?"); 
```

#### orElse
`orElse` accepts the same parameters as [then](#then)
```java
String result = when(value)
    .is(1).then("1")
    .orElse("not 1");
```

```java
String result = when(value)
    .is(1).then("1")
    .orElse(this::method); // method will be called only if value is not 1
```

```java
String result = when(value)
    .is(1).then("1")
    .orElse(val -> String.valueOf(Math.abs(val))); 
```

#### orElseThrow
By default `orElseThrow` throws `IllegalStateException` with default message.
`orElseThrow` accepts a `String` to set an exception message, or `Supplier` to throw a custom one.

```java
String result = when(value)
    .is(1).then("1")
    .orElseThrow(); // IllegalStateException with default message
```

```java
String result = when(value)
    .is(1).then("1")
    .orElseThrow("Some valuable message");
```

```java
String result = when(value)
    .is(1).then("1")
    .orElseThrow(RuntimeException::new); 
```

#### asOptional

If the absence of the result is normal flow. `Optional<>` can be used as a return value.

```java
int value = 1;

Optional<String> result = when(value)
    .is(0).then("0")
    .is(1).then("1")
    .asOptional(); // Optional.of("1")

Optional<String> result = when(value)
    .is(0).then("0")
    .is(2).then("2")
    .asOptional(); // Optional.empty()
```


#### Complex example
```java
String result = when(value)
    .is(i -> i < 0).then(i -> String.format("negative %s", -i))
    .is(0).then("zero")
    .is(1).then(() -> String.format("positive %s", value))
    .is(100_500).then(() -> {
            throw new RuntimeException();
    })
    .isNot(42).then("not 42")
    .orElseThrow("Custom exception message");
```

### When2

It's possible to make matching with two variables
```java

int x = 1;
int y = -2;

String result = when(x, y)
    .is(0, 0).then("zero")
    .is(p -> p > 0, p -> p > 0).then("I Quadrant")
    .is(p -> p < 0, p -> p > 0).then("II Quadrant")
    .is(p -> p < 0, p -> p < 0).then("III Quadrant")
    .is(p -> p > 0, p -> p < 0).then("IV Quadrant")
    .orElse("??");
```

`and` is also supported

```java
int x = 1, y = 1;
String result = when(x, y)
    .is((v1, v2) -> v1 + v2 < 0).and((v1, v2) -> v1 + v2 > -10).then("x+y=(-10..0)")
    .is((v1, v2) -> v1 + v2 > -0).and((v1, v2) -> v1 + v2 < 10).then("x+y=(0..10)")
    .is((v1, v2) -> v1 + v2 > 10).and((v1, v2) -> v1 + v2 < 20).then("x+y=(10..20)")
    .orElseThrow();
```

`or` and `and` can be used together

```java

String result = when(x, y)
    .is(2, 2).or(3, 3).or(4, 4).then("2-3-4")
    .isNot(1, 1).and(p -> p > 0, p -> p > 0).then("not 1, > 0")
    .is(1, 2).or(2, 1).or(1, 1).then("1 or 2")
    .orElseThrow();
```

## License

This project is licensed under [Apache License, version 2.0](https://www.apache.org/licenses/LICENSE-2.0)

## Installation

Releases are available in [Maven Central](https://repo1.maven.org/maven2/me/dehasi/zeliba/)

### Maven

Add this snippet to the pom.xml `dependencies` section:

```xml
<dependency>
    <groupId>me.dehasi</groupId>
    <artifactId>zeliba</artifactId>
    <version>2020.03.08</version>
</dependency>
```

### Gradle 

Add this snippet to the build.gradle `dependencies` section:

```groovy
implementation 'me.dehasi:zeliba:2020.03.08'
```

## Contribution
Feel free to share your ideas via issues and pull-requests.
