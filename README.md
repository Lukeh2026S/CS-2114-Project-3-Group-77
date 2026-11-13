# CS-2114-Project-3-Group-77
This project is an airplane ticket and boarding simulator.

How to run:
To run this project, download all of the files together. Then run the main branch to see and example of the view and enter your name when prompted. It should output your name and a predetermined seat number and char. To run the tests run the tests.java file.

Reflection — 

What changed from your specification? Classes, method signatures, where validation lives, test cases — what did the spec say, what did you build instead, and why?
1. For the airplane class we added a couple more functions to help get information easier such as: isVipRow(), seatsInRow(), isValidSeat(), sectionFor(). These are all slightly more complex getter functions that were used as validation in airplane class. Although there is validation there User and Main will remain with their validation. We did this after a group discussion because it allowed for testing of airplane methods without higher class levels becuase of validation problems.
2. The ticket constructor has 5 parameters that are plane, name, seatNum, seatpos, and boardingGroup. We did this because there was no way to initialize the ticket instance without any input. This came about because I realized that there was no way to set the fields inside of it so I brought it up with the group and they agreed.
3. Boarding has the static queue class built inside of it. We decided this as a group because we did not want to add another file just for a queue implementation because it would complicate it.

Did the model really stand alone? Was there anything you had to move across the model/view line to make it run, or anything in the model that still knows about the view?
Yes all of the logic can work without the view. The things that cross between model and view are the user's name and the seat chart after the ticket is bought for them.

What happened to your unknowns? Of the things you listed in your scope as not knowing how to do, which did you figure out, and which are still open?
The thing we listed on our scope is we don't know how to implement the bots to fill up the plane. We are still working on this aspect and need to figure it out.

What doesn't work yet? MVP features, bad-input cases, or tests that are missing or failing.
The seat selection doesn't work yet because we aren't sure how to parse the input and reject the bad input. Test cases for other non queue methods are not implemented. 


Next Steps — 

What is left to finish the MVP? The remaining features and fixes, in the order you will do them.
Implement the User class and all validation that lives there. Implement the main menus and other menus so the view can be popluated. The Ticket class needs to interact with the view.

Your change feature. Deliverable 4 requires you to add one non-trivial feature that was not in your MVP. Name it, then predict which classes it will touch (model, view, controller) and which ones your design says should not need to change. You will compare this prediction to what actually happened.
One change that we implemented that varied from the MVP is we changed the way the plane is displayed. Instead of it displaying all of the text, we made a string builder and had a string made in a method that returns the map and another function prints it to the terminal. This was caused by group discussion because the way we implemented it

Which stretch goals will you attempt? The ones from your scope you are committing to, and the ones you are dropping.
We will attempt the strech goal of saving tickets to a txt file. We also have implemented the different sized planes but have not built them into the view so that needs more work. We are also committing to have a main menu where you select the flight you want then get to choose other options. We are dropping the stretch goal of having a place to input discount codes.

What new tests will you need? The methods and bad-input cases the added work must cover.
We will need tests for the user class and airplane class methods. The user and main will throw exception that can be caught for validation. This will be helpful for junit testing because we can catch the exceptions and read them.

Who does what? Which team member owns each remaining item.
Luke: Work on the user class.
Jake: Work on the rest of the tests.
Abdullah: Work on the main menu.


Attribution — 

Luke - helped with all files and wrote the queue, reviewed and revised other classes.

Abdullah - helped write main and part of Airplane class and revised tests.

Jake - wrote test classes and reviewed code.

We did not use Gen AI in out project.