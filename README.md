# CS308-GizmoBall

###Running the code
Currently the main method is found in MainInit, this also contains code to initialise and bind together the various model/view objects we're using at this point of testing.


###JSON Format Info
```
{
  "ReflectionCoefficient": 0.95, >>> This is the float of the reflection coefficient
  "ShapeColour": "blue", >>> This is a string used to identify a colour from the Color. library 
  "PhysicsDef": [ >>> This is an array storing the percentage based format used for shapes.
    ["Line",[12, 0], [88, 0]],
    ["Line",[12, 24], [88, 24]],
    ["Circle",[0, 0], 12],
    ["Circle",[76, 0], 12]

  ],
  "VisualDef": [ >>> As above
    ["Square", [24, 0], [76, 24]],
    ["Circle", [0, 0], 12],
    ["Circle", [76, 0], 12]
  ]
}
```

####A short note on physicsdef and visualdef

Doing it this way allows us to easily add new gizmos comprised of basic shapes easily. The values used are percentage based but it's easier to treat it as regular units. In that even if a gizmo is 2x2 squares wide and tall, (0, 0) will always refer to the top left corner, (50, 50) will always refer to the absolute middle and (100, 100) will always refer to the bottom right corner.

####Formats

#####The formats available for PhysicsDef are:
- Line - String representation of the MIT physics object LineSegment
- Circle - String representation of the MIT physics object Circle

#####The formats available for VisualDef
- Square - String representation of a Rectangle shape
- Circle - String representation of a Ellipses2d shape/circle
- Triangle - String representation of a Polygon shape/triangle

####Explanation of the formats used

The standard format is `[String, [xCoordinate, yCoordinate], other]` where `other` varies depending on what the shape requires, currently `other` can be `[width, height]` or `radius`

`["Circle", [76, 0], 12]` > In this example this defines a circle object (parseable as physics or visual) with the top left corner starting at 76 across the x and 0 down on the y, and it has a radius of 12

`["Square", [24, 0], [76, 24]]` > In this example this defines a square visual object with the top left corner of the square at x 24, y 0 and the width being 76 and the height being 24

####Conversion of an object from stored values to screenspace

TODO

