/*
 * Copyright (c) 2026 Team Paradise - FRC 1165 (https://github.com/TeamParadise)
 *
 * Use of this source code is governed by the MIT License, which can be found in the LICENSE file at
 * the root directory of this project.
 */

package com.team1165.robot;

import com.ctre.phoenix6.configs.Slot0Configs;
import com.team1165.robot.subsystems.groundintake.GroundIntake;
import com.team1165.robot.subsystems.groundintake.GroundIntakeConstants;
import com.team1165.robot.subsystems.groundintake.GroundIntakeState;
import com.team1165.robot.subsystems.groundintake.io.PivotIO;
import com.team1165.robot.subsystems.groundintake.io.PivotIOSpark;
import com.team1165.util.constants.RobotMode;
import com.team1165.util.io.roller.RollerIO;
import com.team1165.util.io.roller.RollerIOSpark;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;

public class RobotContainer {
  // Subsystems. im scared

  private final GroundIntake groundIntake;
  private final CommandXboxController driverController = new CommandXboxController(0);

  public RobotContainer() {
    switch (RobotMode.get()) {
      case REAL -> {
        groundIntake =
            new GroundIntake(
                new RollerIOSpark(GroundIntakeConstants.rollerMotorConfig),
                new PivotIOSpark(GroundIntakeConstants.pivotMotorConfig),
                GroundIntakeConstants.pivotMotorPIDConfig);
      }
      default -> {
        groundIntake = new GroundIntake(new RollerIO() {}, new PivotIO() {}, new Slot0Configs() {});
      }
    }
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
  }
}
