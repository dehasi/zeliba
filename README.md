# ZeLiba (The library)

A handy DSL-ish helper to make the comparison more readable.

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
if (a(val1).isGreaterThan(val2)) {
    ...
}

if (the(val2).isLessThan(val1)) {
    ...
}
```


Fluent interval checks `a <= b <= c`

```java
if (the(someValue).isInTheInterval().fromIncluded(val1).toIncluded(val2)) {
    //...
}
```

### TheChronoLocalDate<T>

Also there are extensions to compare dates

```java
LocalDate someDate = ...
LocalDate otherDate = ...


if (an(otherDate).isAfterOrEqual(someDate)) {
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

if (an(otherObject).isNotEqualTo(someObject)) {
    ...
}

if (the(someObject).isNotNull()) {
    ...
}

if (a(someObject).isNull()) {
    ...
}
```


### TheCollection

Check if collection is not empty

```java
List<?> list = ...
Set<?>  otherSet = ...

if (the(list).isNotEmpty()) {
    ...
}

if (an(otherSet).isNullOrEmpty()) {
    ...
}
```
