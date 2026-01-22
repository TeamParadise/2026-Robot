/*
 * Copyright (c) 2026 Team Paradise - FRC 1165 (https://github.com/TeamParadise)
 *
 * Use of this source code is governed by the MIT License, which can be found in the LICENSE file at
 * the root directory of this project.
 */

package com.team1165.robot;

import static com.team1165.util.constants.RobotMode.Mode.REAL;

import com.team1165.robot.subsystems.roller.groundintake.GroundIntake;
import com.team1165.robot.subsystems.roller.groundintake.GroundIntakeConstants;
import com.team1165.robot.subsystems.roller.groundintake.GroundIntakeState;
import com.team1165.robot.subsystems.roller.io.RollerIO;
import com.team1165.robot.subsystems.roller.io.RollerIOSpark;
import com.team1165.util.constants.RobotMode;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;

public class RobotContainer {
  // Subsystems. im scared

  private final GroundIntake groundIntake;
  private final CommandXboxController driverController = new CommandXboxController(0);

  public RobotContainer() {
    switch (RobotMode.get()) {
      case REAL -> {
        groundIntake =
            new GroundIntake(new RollerIOSpark(GroundIntakeConstants.primaryMotorConfig));
      }
      default -> {
        // start scremaing bc i havent coded this part yet
        groundIntake = new GroundIntake(new RollerIO() {});
      }
    }
  }

  private void configureButtonBindings() {
    driverController
        .a()
        .onTrue(
            groundIntake
                .overrideState(GroundIntakeState.ON)
                .alongWith(groundIntake.overrideState(GroundIntakeState.ON))
                .withName("Controller - A - Full Power"));
  }
}
