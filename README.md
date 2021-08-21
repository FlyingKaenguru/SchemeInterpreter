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
- cons
- car
- cdr

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