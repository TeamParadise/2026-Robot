/*
 * Copyright (c) 2026 Team Paradise - FRC 1165 (https://github.com/TeamParadise)
 *
 * Use of this source code is governed by the MIT License, which can be found in the LICENSE file at
 * the root directory of this project.
 */

package com.team1165.robot.globalconstants;


/**
 * Class containing constants for the CAN bus(ses) of the robot, mainly consisting of the IDs for
 * all the different mechanisms.
 */
public class CANConstants {
  public static final class IDs {
    public static final class RIO {
      public static final double intakePivotPrimary = 1;
      public static final double intakePivotSecondary = 2;
      public static final double intakeRoller = 3;
    }
  }
}
