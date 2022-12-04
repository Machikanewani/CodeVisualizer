# CodeVisualizer
Shows java program's Class Blueprint Graph and Pyramid Graph by collecting its metrics e.g. LOC, Cyclo.

#### To generate project pyramid graph, type the following command in Terminal:

`gradle run --args="-p [root file's path]"`

Pyramid Graph generated would be like this:

<img src="https://user-images.githubusercontent.com/85107247/205480932-2a6e2092-e510-4acb-af3d-627b524ee238.png" width="500px">

*Cyclo*: The number of linearly-independent paths through an operation.  
*LOC*: The number of lines of code of an operation, including blank lines and comments.  
*NOM*: The number of methods of a class.  
*NOC*: The number of classes of a package.  
*NOP*: THe number of packages of a project.  
*AHH*: Average Hierarchy Height.  
*ANDC*: Average Number of Derived Classes.  


Actually generated pyramid graph is:

<img width="500" alt="actual" src="https://user-images.githubusercontent.com/85107247/205481902-6dc73d66-6412-4144-b6f5-08a3b2df3649.png">

#### To generate class blueprint graph, type the following command in Terminal:

`gradle run --args="-b [class1's path] [class2's path] ..."`

Actually generated blueprint graph is:

<img width="500" alt="image" src="https://user-images.githubusercontent.com/85107247/205482993-6b0e1ed1-c566-4ffa-a3cc-66426eea2579.png">



