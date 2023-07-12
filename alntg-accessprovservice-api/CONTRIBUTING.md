This file contains guidelines that should be followed when making any
changes to the repository. All text before the first level 1 (L1) header
will be ignored by the Pull request guidelines add-on.






# Guidelines for bug fix

Please follow these guidelines when doing a pull request reviews.

(Some text can go here to introduce the reader to this topic. All text
between a level 1 heading and a level 2 heading will show up in the
dialog in the pull request, but will not be actionable.)

## Is the bug changed to any of the fixed child status

QA should not be given any bug in inprogress status. Please make sure bug is changed to dev-validate / dev-verified / invalid or other status

## Is there any case which is not fixed and a limitation

QA should be explained what are the limitations and why they can not be fixed. Please update the bug with detailed document / information.


## Is the configuration document attached to the jira

Please make sure all the configurations are attached to the jira

## Is the bug verified in dev staging

Please confirm the bug is verified

## Are the impact areas updated in Jira

Please update the impact areas so that QA can validate those


## If bug has been reopened due to lack of config documents or proper comments

Dont mark it as “WorksForMe” if comments were missing.


## Are the test cases updated / added

Any code change / new code being added must accompany a test case. If the change is added for an external facing call - example a REST call being called by some other app - then make sure to add a new test case file for that app (if it doesnt exist already)
If you are fixing a bug, you can add a test case with its jira id in the test case name. Feel free to create new test case java file if you like to group them or organize them

# Guidelines for new Story review

## KT / Demo of functionality Provided

Please attach the kt location (google drive link) in the jira

## Config documeent attached
Please make sure all the configurations are attached to the jira

## Test case reviewed

Please make sure the test cases are reviewed by you for this story


# Code Review Checklist
## Checklist for Developers
*	My code compiles	
*	My code has been developer-tested and includes unit tests	
*	My code includes javadoc where appropriate	
*	My code is tidy (indentation, line length, no commented-out code, no spelling mistakes, etc)	
*	I have considered proper use of exceptions	
*	I have made appropriate use of logging	
*	I have eliminated unused imports	
*	I have eliminated Eclipse warnings	
*	I have considered possible NPEs	
*	The code follows the Coding Standards	
*	Are there any leftover stubs or test routines in the code?	
*	Are there any hardcoded, development only things still in the code?	
*	Was performance considered?	
*	Was security considered?	
*	Does the code release resources? (HTTP connections, DB connection, files, etc)	
*	Corner cases well documented or any workaround for a known limitation of the frameworks
*	Can any code be replaced by calls to external reusable components or library functions?	
*	Thread safety and possible deadlocks

## Checklist for Reviewers

*	Comments are comprehensible and add something to the maintainability of the code	
*	Comments are neither too numerous nor verbose	
*	Types have been generalized where possible	
*	Parameterized types have been used appropriately	
*	Exceptions have been used appropriately	
*	Repetitive code has been factored out	
*	Frameworks have been used appropriately – methods have all been defined appropriately	
*	Command classes have been designed to undertake one task only	
*	JSPs do not contain business logic	
*	Unit tests are present and correct	
*	Common errors have been checked for	
*	Potential threading issues have been eliminated where possible	
*	Any security concerns have been addressed	
*	Performance was considered	
*	The functionality fits the current design/architecture	
*	The code is unit testable	
*	The code does not use unjustifiable static methods/blocks	
*	The code complies to coding standards	
*	Logging used appropriately (proper logging level and details)	
*	NPEs and AIOBs

## Documentation
*	All methods are commented in clear language. If it is unclear to the reader, it is unclear to the user.
*	Describe behavior for known input corner-cases.
*	Complex algorithms should be explained with references. For example, document the reference that identifies the equation, formula, or pattern. In all cases, examine the algorithm and determine if it can be simplified.
*	Code that depends on non-obvious behavior in external frameworks is documented with reference to external documentation. 
*	Confirm that the code does not depend on a bug in an external framework which may be fixed later, and result in an error condition. If you find a bug in an external library, open an issue, and document it in the code as necessary.
Testing
*	Unit tests are added for each code path, and behavior. This can be facilitated by tools like Sonar, and Cobertura.
*	Unit tests must cover error conditions and invalid parameter cases.
*	Check for possible null pointers are always checked before use.
*	Array indices are always checked to avoid ArrayIndexOfBounds exceptions.
*	Do not write a new algorithm for code that is already implemented in an existing public framework API, and tested.
*	Ensure that the code fixes the issue, or implements the requirement, and that the unit test confirms it. If the unit test confirms a fix for issue, add the issue number to the documentation.
## Error Handling
*	Invalid parameter values are handled properly early in methods (Fast Fail).
*	NullPointerException conditions from method invocations are checked.
*	Consider using a general error handler to handle known error conditions.
*	An Error handler must clean up state and resources no matter where an error occurs.
*	Avoid using RuntimeException, or sub-classes to avoid making code changes to implement correct error handling.
*	Define and create custom Exception sub-classes to match your specific exception conditions. Document the exception in detail with example conditions so the developer understands the conditions for the exception.
*	(JDK 7+) Use try-with-resources. (JDK < 7) check to make sure resources are closed.
*	Don't pass the buck! Don't create classes which throw Exception rather than dealing with exception condition.
*	Don't swallow exceptions! For example catch (Exception ignored) {}. It should at least log the exception.
## Thread Safety
*	Global (static) variables are protected by locks, or locking sub-routines.
*	Objects accessed by multiple threads are accessed only through a lock, or synchronized methods.
*	Locks must be acquired and released in the right order to prevent deadlocks, even in error handling code.
Performance
*	Objects are duplicated only when necessary. If you must duplicate objects, consider implementing Clone and decide if deep cloning is necessary.
*	No busy-wait loops instead of proper thread synchronization methods. For example, avoid while(true){ ... sleep(10);...}
*	Avoid large objects in memory, or using String to hold large documents which should be handled with better tools. For example, don't read a large XML document into a String, or DOM.
*	Do not leave debugging code in production code.
*	Avoid System.out.println(); statements in code, or wrap them in a Boolean condition statement like if(DEBUG) {...}
*	"Optimization that makes code harder to read should only be implemented if a profiler or other tool has indicated that the routine stands to gain from optimization. These kinds of optimizations should be well documented and code that performs the same task should be preserved." - UNKNOWN.





