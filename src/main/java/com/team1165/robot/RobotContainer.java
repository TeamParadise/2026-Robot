/*
 * Copyright (c) 2026 Team Paradise - FRC 1165 (https://github.com/TeamParadise)
 *
 * Use of this source code is governed by the MIT License, which can be found in the LICENSE file at
 * the root directory of this project.
 */

package com.team1165.robot;

import static com.team1165.util.constants.RobotMode.Mode.REAL;
import static edu.wpi.first.units.Units.Amps;
import static edu.wpi.first.units.Units.Degrees;

import com.team1165.robot.subsystems.roller.groundintake.GroundIntakeConstants;
import com.team1165.robot.subsystems.roller.groundintake.GroundIntakeState;
import com.team1165.robot.subsystems.roller.groundintake.GroundIntake;
import com.team1165.robot.subsystems.roller.io.RollerIOSpark;
import com.team1165.util.TeleopDashboard;
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
            new GroundIntake(
                new RollerIOSpark(
                    GroundIntakeConstants.primaryMotorConfig));
        }
      default -> {
        // start scremaing bc i havent coded this part yet
      }
    }
  }

  private void configureButtonBindings() {
    driverController
        .a()
        .onTrue(GroundIntake.ON());
  }
}
