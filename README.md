# Scheme Interpreter

### Launch application
java -jar schemeInterpreter-1.0.jar

### Exit application
- (exit)

### Requirement
- java 1.16

## Syntax:

### initial character:
- `(`

### terminal character:
- `)`

### A function consists of:  
`(symbol [] [] )`
Functions can have inner-functions: `(symbol [] (symbol [] []))`

### Follow-up characters (Symbol) with example
- Addition
  - `(+ 5 6 ...)`
- car - first item of cons or list
  - `(car x)` 
- cdr - second and all subsequent items in list / cons
  - `(cdr x)`
- Construct -> The cons function accepts any two values, not just a list for the second argument. The two values joined with cons are printed between parentheses, with a dot, because Lisp interpreters uses a . to visually separate the elements in the pair.
  - `(cons 3 5)`
- Define
  - `(define xy 5)`
- Display variable 
  - `(display xy ...)`
- Division 
  - `(/ 3 9 ...)`
- Greater
  - `(> 5 7)`
- GreaterEquals
  - `(>= 4 6)`
- IfStatement
  - `(if (= 4 6) #t #f)`
- lambda expression -> A lambda expression creates a function. In the simplest case, a lambda expression has the form
  - `((lambda (x y ...) (+ x y ...)) 2 3 ...)`
- List
  - `(list 4 5 6 ...)` 
- Lower
  - `(< 4 5)`
- LowerEquals
  - `(<= 3 4)`
- Multiplication
  - `(* 2 4 ...)`
- newline - Prints a new line
  - `(newline)`
- Numeric equal
  - `(= 4 5)`
- NumericNotEqual
  - `(!= 5 6)`
- set!
  - `(set! x 4)`
- String Equal
  - `(equal? "Hello" "World")`
- Subtraction
  - `(- 6 7 ...)`

## Issues and Todos
- Automatic loading of symbol classes after compilation is not possible. 
  The classpath is not recognised here (in a compiled environment)
  
- All strings, cons and lists are stored permanently (runtime, until java program is stopped), like defined variables. This could causes the application slow down over time. 
  Here, a solution must be thought of how values that are only needed for processing can be deleted afterwards.

- Comparing strings is very slow compared to comparing bits/bytes. Perhaps the application could be modified. 
  Since the speed of the application was not a criterion when creating it, this was neglected in version 1.0.
