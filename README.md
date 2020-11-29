# mars
- MARS is a SpringBoot Commandline Runner Application, to start please run the executable jar build/libs/mars-0.0.1-SNAPSHOT.jar or right-click in your IDE and select run on the main boot class: java/me/knight/mars/MarsApplication.java
- Please enter input data as detailed into the console window (see below for usage)
- Invalid input data will cause the application to exit and the usage (as below) displayed 
- To exit the application press <ENTER> again

```
Usage:
    [line 1] input upper-right coordinates (max 50 x 50) for Mars grid, separated by white space, followed by <ENTER> key - example: 5 3
    [line 2] input robot initial position and orientation (NESW) on Mars, separated by white space, followed by <ENTER> key - example: 1 1 E
    [line 3] input operator instructions (max 99 instructions) followed by <ENTER> key - example: RFRFRFRF
    press <ENTER> key to send instructions and solicit robot report
    [line 4] input next robot position and orientation <ENTER>
    [line 5] next operator instructions <ENTER>
    press <ENTER> for robot report
    repeat line 4 and 5 or hit <ENTER> again to exit
```

## to run end-to-end cucumber tests
- Right-click resources/features/mars.feature and select run in your IDE for end to end blackbox tests against the binary