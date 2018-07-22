#AgriTasks Calculator (coding test)


##Goal
Implement an expression evaluator which supports only addition and multiplication in Java (preferably), or another language.
The expression can have only digits, plus and multiplication symbols, and optional white-space.
Optionally consider optimizing the algorithm for better performance.
For example, calculate the result of “5 * 4 + 12”.

##Algorithm description
Two result accumulators S and M are used: S stores result of addition and M - the one of multiplication. By the end S would contain the result of passed in expression evaluation.