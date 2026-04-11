/*
 * Copyright (c) 2025-2026 Team Paradise - FRC 1165 (https://github.com/TeamParadise)
 *
 * Use of this source code is governed by the MIT License, which can be found in the LICENSE file at
 * the root directory of this project.
 */

package com.team1165.robot.subsystems.drive.constants;

import static edu.wpi.first.units.Units.Inches;
import static edu.wpi.first.units.Units.Pounds;
import static edu.wpi.first.units.Units.Seconds;

import com.ctre.phoenix6.configs.Slot0Configs;
import com.ctre.phoenix6.swerve.SwerveDrivetrainConstants;
import com.ctre.phoenix6.swerve.SwerveModuleConstants;
import edu.wpi.first.math.system.plant.DCMotor;

public class DriveConstants {
  /** Drivetrain constants (taken directly from TunerConstants). */
  public static final SwerveDrivetrainConstants drivetrainConstants =
      TunerConstants.DrivetrainConstants;

  /**
   * Array of module constants for easy creation of the Drive subsystem. Taken from TunerConstants.
   */
  private static final SwerveModuleConstants<?, ?, ?>[] moduleConstants = {
    TunerConstants.FrontLeft,
    TunerConstants.FrontRight,
    TunerConstants.BackLeft,
    TunerConstants.BackRight
  };

  /**
   * Method to get module constants, because otherwise they would be mutable (because of the array)
   */
  public static SwerveModuleConstants<?, ?, ?>[] getModuleConstants() {
    return moduleConstants;
  }

  /** Constants for autonomous driving and line up of the robot. */
  public static final class PathConstants {
    public static final Slot0Configs translation = new Slot0Configs().withKP(4);
    public static final Slot0Configs rotation = new Slot0Configs().withKP(7);
  }

  /**
   * Details about the robot used for accurate simulation. Some of these values are taken from the
   * RobotConfig.
   */
}
