/*
 * Copyright (c) 2026 Team Paradise - FRC 1165 (https://github.com/TeamParadise)
 *
 * Use of this source code is governed by the MIT License, which can be found in the LICENSE file at
 * the root directory of this project.
 */

package com.team1165.robot;

import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkBaseConfig;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.team1165.robot.subsystems.hood.HoodSubsystem;
import com.team1165.robot.subsystems.roller.groundintake.GroundIntake;
import com.team1165.robot.subsystems.roller.groundintake.GroundIntakeConstants;
import com.team1165.robot.subsystems.roller.groundintake.GroundIntakeState;
import com.team1165.robot.subsystems.roller.io.PivotIO;
import com.team1165.robot.subsystems.roller.io.PivotIOSpark;
import com.team1165.robot.subsystems.roller.io.RollerIO;
import com.team1165.robot.subsystems.roller.io.RollerIOSpark;
import com.team1165.util.constants.RobotMode;
import com.team1165.util.vendor.rev.SparkConfig;
import com.team1165.util.vendor.rev.SparkModel;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;

public class RobotContainer {
  // Subsystems. im scared

  private final GroundIntake groundIntake;
  private final HoodSubsystem hoodSubsystem;
  private final CommandXboxController driverController = new CommandXboxController(0);

  public RobotContainer() {
    switch (RobotMode.get()) {
      case REAL -> {
        groundIntake =
            new GroundIntake(new RollerIOSpark(GroundIntakeConstants.rollerMotorConfig), new PivotIOSpark(GroundIntakeConstants.pivotMotorConfig));
      }
      default -> {
        // start scremaing bc i havent coded this part yet
        groundIntake = new GroundIntake(new RollerIO() {}, new PivotIO() {});
      }
    }

    hoodSubsystem = new HoodSubsystem(
        SparkConfig.sparkMax(
            "Hood Motor",
            2,
            MotorType.kBrushless,
            new SparkMaxConfig()));
  }

  private void configureButtonBindings() {
    driverController
        .a()
        .onTrue(
            groundIntake
                .overrideState(GroundIntakeState.IDLE)
                .withName("Controller - A - Idle State"));
    driverController
        .b()
        .onTrue(
            groundIntake
                .overrideState(GroundIntakeState.DEPLOY)
                .withName("Controller - B - blahblahblor as my friend myles would say"));
    driverController
        .x()
        .onTrue(
            hoodSubsystem
                .setAngle(60));

  }
}
