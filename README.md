# Java-fp-demo

A rough plan about this demonstration:

"Constraints liberate, liberties constrain." - Runar Bjarnason

#### Introduction

* practical and easy approaches to fp
    - not too much about terminologies
* adopt philosophies
    - how we benefit from it?
    - how we apply to our daily work?
* not limited but mainly in java
    - some examples specifically for java7

#### Side-effect & State

* level of side-effect
* scope of state
* isolate effect
* effect propagation
* java exceptions
* stream
* lambda vs method reference
* closure

#### Immutability

* value vs variable
* value class / data object / case class
    - hashcode & equals
    - atomicity
        - self-check / structural enforcement
        - once constructed, ready to use
        - error atomicity
    - serialization/representation
    - builder pattern
    - factory pattern
* effective immutable
    - hide mutability
    - defensive copy
* event-log / state-snapshot
    - recover
* thread-safety
    - actor system
* performance concerns / memory model
    - local mutability
    - cpu cache
    - intern
    - reference locality
* state tree
    - reference equality
    - single entry 
    - lens

#### General Design

* pure function
* static function
* stateless
* pipeline vs streamline
* class creation vs maintenance
    - gc overhead
    - in-memory serialization
    - off heap cache
* class level dependencies
    - non-cyclic dependencies tree
* parallelism
    - high level abstraction
    - thread and execution context
    - non-block
* API
    - accessibility / visibility
    - method / function
* reactive
    - Responsive
    - Resilient
    - Elastic
    - Message Driven

#### Best practices

* Google guava
    - Collections2
    - FluentIterable
* Google AutoValue
* high order functions
    - map
    - flatMap
    - reduce
    - fold / recursion
* type-class
    - strategy pattern
    - visitor pattern

"Harness your code to make your future life easier." - Cause Cheng

_Examples and interactions are sparsed in topics above._

