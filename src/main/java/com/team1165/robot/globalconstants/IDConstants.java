/*
 * Copyright (c) 2026 Team Paradise - FRC 1165 (https://github.com/TeamParadise)
 *
 * Use of this source code is governed by the MIT License, which can be found in the LICENSE file at
 * the root directory of this project.
 */

package com.team1165.robot.globalconstants;

import com.ctre.phoenix6.CANBus;
import com.team1165.util.constants.CANConstants;

/** Class containing constants for the CAN IDs of the robot. */
public class IDConstants {
  public static final class RIO {
    private RIO() {}

    public static final CANBus bus = CANConstants.rio;

    public static final int hood = 20;
    public static final int intakePivotPrimary = 55;
    public static final int intakePivotSecondary = 56;
    public static final int intakeRoller = 57;
    public static final int spindexer = 12;
    public static final int transfer = 5;
    // TODO: Replace with the CAN ID for agitator
    public static final int agitator = 0;
  }

  public static final class CANivore {
    private CANivore() {}

    public static final CANBus bus = new CANBus("canivore");

    public static final int flywheelPrimary = 13;
    public static final int flywheelSecondary = 14;
    public static final int turretMotor = 20;
  }
}
