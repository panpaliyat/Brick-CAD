# Brick - CAD

Brick CAD is a CAD system for designing bricks. It has all of the same basic features as a CAD system for designing jumbo jets, but 
without the troubling complexity of the underlying model and the resulting product. A brick is a 3D object which has height, width, 
and length. User can choose to view a brick from side, top, front and dimension view. User can edit height, width and length of the brick. 
User can save/saveAs the working model to the file system and can open an existing model from the file system. These operations are 
demonstrated below using use case diagrams. 

![alt text][use_case1]

[use_case1]: /images/use_case1.png "Use Case 1"


![alt text][use_case2]

[use_case2]: /images/use_case2.png "Use Case 2"


Brick CAD is customization of MVC Framework. Below class diagram shows the MVC framework and its customization.

![alt text][MVC_Framework]

[MVC_Framework]: /images/MVC_Framework.png "MVC Framework"


To provide undo/redo functionality as an in-built functionality (part of MVC package) Memento design pattern is used. 
Below is the class diagram which demonstrate the memento design pattern in brick cad system

![alt text][Memento]

[Memento]: /images/Memento.png "Memento"

This is how the overall structure of the system and sample screens of the running system

![alt text][MVCApp]

[MVCApp]: /images/MVCApp.png "Class Diagram"

![alt text][sample_screen]

[sample_screen]: /images/sample_screen.png "Running screenshots"