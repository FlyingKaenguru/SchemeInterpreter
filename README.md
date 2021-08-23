# Scheme Interpreter

## Syntax:

### initial character:
- "
- (
- ''

### Follow-up characters (Symbol)
- define
- quote
- if
- lambda
- set!
- let
- (
- eq?
-     +
-     -
-     /
-     *
- =
- <
-     >
- !=
- ~=
- cons => The cons function accepts any two values, not just a list for the second argument. The two values joined with cons are printed between parentheses, with a dot, because Lisp interpreters uses a . to visually separate the elements in the pair.
- car  => first item 
- cdr  => second and all subsequent items in list

**Eine Funktion besteht aus: ( symbol [] [] )
Funktionen können Innere-Funktionen haben: (symbol ( symbol [] []) [])** 

## Ablauf: 
- Initiol Character betrachten
- Falls '(' --> Funktion
- Auf validität prüfen (stimmt die Anzahl der Klammern)
- In einer While-Schleife gefundene Innerfunctions bearbeiten
- Innerfunction mit Ergebnis auswechseln 
- Dies wird solange gemacht, bis nur noch 1 '(' und ')' vorhanden ist



null ()
true #t
false #f