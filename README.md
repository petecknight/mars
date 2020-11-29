# mars
- SpringBoot Commandline Runner Application
- Please enter input data in the console
- To exit hit <ENTER> again
- Invalid input will error and Usage displayed

```
Usage:

    [line 1] input upper-right coordinates (max 50 x 50) for Mars grid, separated by white space, followed by <ENTER> key - example: 5 3 \n
    [line 2] input robot initial position and orientation (NESW) on Mars, separated by white space, followed by <ENTER> key - example: 1 1 E \n
    [line 3] input operator instructions (max 99 instructions) followed by <ENTER> key - example: RFRFRFRF \n
    hit <ENTER> key to send instructions and solicit robot report\n
    [line 4] input next robot position and orientation <ENTER>\n
    [line 5] next operator instructions <ENTER>\n
    hit <ENTER> for robot report\n
    repeat line 4 and 5 or hit <ENTER> to exit
```
## to run cukes
- Right-click resources/features/mars.feature and select run in your IDE for end to end blackbox tests against the binary