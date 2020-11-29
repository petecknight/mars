Feature: Controlling Martian Robots On Mars From Planet Earth

  Scenario: Robot reports position when instructed to move within the Mars boundry
    Given I have a robot positioned at 1 1 facing East on Mars with a boundry set as 5 3
    When I instruct the robot to move RFRFRFRF and report its final position
    Then the robot reports the expected final grid position

  Scenario: Robot reports position as lost when instructed to move outside the Mars boundry
    Given I have a robot positioned at 3 2 facing North on Mars with a boundry set as 5 3
    When I instruct the robot to move FRRFLLFFRRFLL and report its final position
    Then the robot reports as lost and its last known grid position and orientation before falling

  Scenario: Robot ignores instruction to move outside the Mars boundry from a scented grid point
    Given I have a robot positioned at 0 3 facing West on Mars with a boundry set as 5 3
    And I have a doomed robot positioned at 3 2 facing North
    When I instruct the robot to move LLFFFLFLFL following the path of doomed robot FRRFLLFFRRFLL which has fallen
    Then the robot reports the expected final grid position and orientation