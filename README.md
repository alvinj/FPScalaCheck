# ScalaCheck

This repository includes the source code for the 
[ScalaCheck](https://www.scalacheck.org/) lesson from my book, 
[Learning Functional Programming in 
Scala](https://alvinalexander.com/scala/learning-functional-programming-in-scala-book).


## Code

Here’s a brief description of the files in this project:

- `ListUtils` includes two functions that I test in the project:
    - `dropAllButFirst`, as shown in the book
    - `dropFirstMatch`, not shown in the book
- `MathUtils` contains the function `increaseRandomly`, which is used in the first ScalaCheck lesson
- `DropAllButFirstSpec_IntLists` is the property test class from the second ScalaCheck lesson in the book.
- `DropAllButFirstSpec_PizzaToppings` shows a property test class for `dropAllButFirst` that tests against a `List[Topping]`. It also shows a `Gen[List[Topping]]` generator.
- `DropFirstMatchTests` contains a set of ScalaTest unit tests that test the function, `dropFirstMatch`
- `IncreaseRandomlySpec` is the property test class that tests the `increaseRandomly` function


## Author

Alvin Alexander    
http://alvinalexander.com


