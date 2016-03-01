# CS308-GizmoBall

###Running the code
Currently the main method is found in MainInit, this also contains code to initialise and bind together the various model/view objects we're using at this point of testing.

#Code overview

###Brief note on units.

The spec detailed that we should be using L values, we can easily represent L as a double in the range of 0.00 to 1.00, for example 1L is just 1.00, 2.5L is 2.5 and so forth. All model classes use L values by default, velocity is stored as L/s in a Vect. MIT physics objects are also stored using L's, this helps standardise the entire model and allows us to apply it to view as per the MVC idea.

###Representation of gizmos

Gizmos are represented as a class that extends the AbstractGizmo object. The AbstractGIzmo object stores various details about gizmos in general and can be extended to include whatever behaviour is desired, ie the Flipper is extended to modify at which point the Gizmo rotates.

(Notes about the gizmos, the stuff in getShape should probably moved to a new function called calculatePhysics or something and that function updates the physics and the shape. Then all getShape does it return the stored Shape object. Also angleVel and rotateAroundPoint should not be in AbstractGizmo, need to move them down to Flipper since overriding the rotateAroundPoint in flipper bugs out the flipper rotation a bit.)

But generally all Gizmos store:
- The x position, y position, width and height in L's
- The angle of the gizmo in degrees
- The reflection coefficient as specified in the spec
- A list of AbstractGizmo objects which essentially subscribe to this gizmo and get their doTrigger method called when this gizmos onHit method is called
- The gizmos colour, Shape object and name
- Also stores a list of MIT Circle objects and MIT LineSegments which make up the Gizmos physics shape

###How gizmos are drawn

Previously as gizmos stored their x/y/w/h as pixel values on the model we could just draw the shape directly and forget about it. But since the gizmos now have x/y/w/h in L values we need to actually scale this up to the full board size using AffineTransform and createTransformedShape and .scale. It's fairly simple, initially it's reading the x/y/w/h values as a double in the range of 0 to 20 as that's the size of our game board, to scale that up to an 800x800 pixel gameboard we need to get the scale factor which can be calculated as BOARDWIDTH(800) / XCELLS(20) = 40 which scales a value in the range of 0-20 up to 0-800 

###How the physics works

Previously the model physics and view were completely tied together, i changed this because i thought that was wrong and it also meant increasing the fps made the view actually appear faster than it would at a lower fps which isn't really right.

So now the new physics system has two settings, FRAME_RATE which is the visual frame rate in fps, PHYSICS_FRAME_RATE which is how many physics operations are done a second, MOVE_TIME is just a reciprocal of the physics framerate to convert it into a double which, at 1000 iterations a second, is how long one frame takes so that's the move time.

In RunListener previously we would just have one timer where we would call a physics update and then push that update to the client. Now the timer runs at whatever we specify the FRAME_RATE to be but on every iteration it calculates how many physical frames it needs to calculate for the time that's elapsed. So for example 
 - at a visual fps of 20, each visual frame is shown every 0.05 seconds, this means that for every visual frame we need to do PHYSICS_FRAME_RATE(1000) / FRAME_RATE(20) = 50 physical calculations for every visual frame update to keep the actual speed of the simulation consistent across visual frame rates
 - at a visual fps of 50, each visual frame is shown every 0.02 seconds, this means that for every visual frame we need to do PHYSICS_FRAME_RATE(1000) / FRAME_RATE(50) = 20 physical calculations for every visual frame update to keep the actual speed of the simulation consistent across visual frame rates
 - this makes sense because the faster we actually draw the frames the less physics calculations we have to do to keep it in sync across the range of fps'

###Triggering System (both physics and keypress)

####Physics

When the CollisionManager detects that a collision is going to happen it gets the CollisionDetails object which stores the Gizmo which is about to be hit. It then calls the onHit method on that Gizmo. By default if the onHit behaviour is not overridden it then iterates through all the listener gizmos and calls doTrigger on those gizmos which does behaviour like FLipper: flip, Absorber: shoot, etc.

for example (Flipper labelled F, SquareBumper labelled S, F is a listener of S (S contains a reference to F in its' listenerGizmos array)):

- Ball hits S
- COllisionManager detects this and calls onHit() on S
- S then iterates through all gizmoListeners and calls doTriggered on each of them
- doTrigger is called on F which sets the flipperMoving flag to true.
- The flipper then starts moving on every call to getSHape()

####Keypress

The ProjectManager stores a map of keyinfo to AbstractGizmos. Right now for the keyinfo i'm emulating a tuple by using the java Entry class but honestly this would be so much better with a small KeyInfo class, would also allow us to add equality and do .getContains on the map etc. But basically each entry is eg, keyInt is the int related to a specific key on the keyboard <("down"/"up", keyInt), SquareBumper/Gizmo>. 

In MagicKeyListener whenever a key is pressed or released it iterates through the map, checks to see if there's an entry where keyInt is equal to the pressed keyCode it then it calls doTrigger on the stored Gizmo at that entry. This then does whatever action is stored in doTrigger, as above for Absorber: shoot stored ball, for FLipper: start flipping
Similar to Physics in that it just simulates an onHit

