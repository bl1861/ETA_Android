##Project-based Interview: Infection

#### Files:
* ```User.java```:  The model of user.
* ```Main.java```:  The main function. You should run Main to execute the program.
* ```Infection.java```: The class used to  start the Total Infection or Limited Infection.

#### Instruction:

* Total Infection:
	- Run the program using the command ```$./Main total 100```.  The first argument is the type of infection(```total``` or ```limited```). The second argument is the number of  users.

* Limited Infection:
	- Run the program using the command ```$./Main limited 100 10```.  The first argument is the type of infection(```total``` or ```limited```). The second argument is the number of  users. The third argument is limited number of infection.

* Implementation:
	- I built the relation of users as directed graph. The directed edge is from Coach to Student. Basically, I use BFS to solve both problems.
	-  In the problem of Limited Infection, my solution now is finding a component which has the closest number of nodes to the limited number but less than or equal to limited number. The searching process run in the increasing order of ```user.id```. 

 I am not sure if I correctly implement the solution, but I have tried to make one. The executions of both problems will print the relation of users and the infection's spread.  





