/*
 * Copyright (c) 2026 Team Paradise - FRC 1165 (https://github.com/TeamParadise)
 *
 * Use of this source code is governed by the MIT License, which can be found in the LICENSE file at
 * the root directory of this project.
 */

package com.team1165.robot;

import com.team1165.robot.subsystems.turret.Turret;
import com.team1165.robot.subsystems.turret.TurretConstants;
import com.team1165.robot.subsystems.turret.TurretState;
import com.team1165.robot.subsystems.turret.io.TurretIO;
import com.team1165.robot.subsystems.turret.io.TurretIOSpark;
import com.team1165.util.constants.RobotMode;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;

public class RobotContainer {
  // Subsystems. im scared

  private final Turret turret;
  private final CommandXboxController driverController = new CommandXboxController(0);

  public RobotContainer() {
    switch (RobotMode.get()) {
      case REAL -> {
        turret = new Turret(new TurretIOSpark(TurretConstants.turretMotorConfig));
      }
      default -> {
        // start scremaing bc i havent coded this part yet
        turret = new Turret(new TurretIO() {});
      }
    }
  }

  private void configureButtonBindings() {
    driverController
        .x()
        .onTrue(turret.overrideState(TurretState.IDLE).withName("Controller - X - Idle State"));
    driverController
        .y()
        .onTrue(
            turret
                .overrideState(TurretState.IDLE)
                .withName("Controller - Y - it rotates clockwise"));
  }
}
