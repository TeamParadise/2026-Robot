/*
 * Copyright (c) 2026 Team Paradise - FRC 1165 (https://github.com/TeamParadise)
 *
 * Use of this source code is governed by the MIT License, which can be found in the LICENSE file at
 * the root directory of this project.
 */

package com.team1165.robot.commands;

import com.team1165.robot.calculations.shoot.ShootCalculation;
import com.team1165.robot.calculations.shoot.ShootReturnValue;
import com.team1165.robot.calculations.shoot.ShooterConstants;
import com.team1165.robot.subsystems.drive.Drive;
import com.team1165.robot.subsystems.hood.Hood;
import com.team1165.robot.subsystems.hood.HoodState;
import com.team1165.robot.subsystems.shooter.flywheel.Flywheel;
import com.team1165.robot.subsystems.shooter.flywheel.FlywheelState;
import com.team1165.robot.subsystems.shooter.turret.Turret;
import com.team1165.robot.subsystems.shooter.turret.TurretState;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import java.util.Optional;
import org.littletonrobotics.junction.Logger;

/** Factory for Shoot On The Move commands. */
public final class ShootOnTheMoveCommands {

  private ShootOnTheMoveCommands() {}

  /** Creates a SOTM tracking command with default parameters. */
  public static Command trackTarget(Drive drive, Turret turret, Hood hood, Flywheel flywheel) {
    return trackTarget(
        drive,
        turret,
        hood,
        flywheel,
        ShooterConstants.DEFAULT_TURRET_HEIGHT_FEET,
        ShooterConstants.DEFAULT_MAX_PATH_HEIGHT_FEET);
  }

  /** Creates a SOTM tracking command with custom turret height and max path height. */
  public static Command trackTarget(
      Drive drive,
      Turret turret,
      Hood hood,
      Flywheel flywheel,
      double turretHeight,
      double desiredMaxPathHeight) {

    return Commands.run(
            () -> {
              Optional<ShootReturnValue> solutionOpt =
                  ShootCalculation.calculateShoot(drive, turretHeight, desiredMaxPathHeight);

              if (solutionOpt.isPresent()) {
                ShootReturnValue solution = solutionOpt.get();

                double turretTarget = solution.getShootDirection() / (2 * Math.PI);
                turret.setSotmTargetPosition(turretTarget);
                hood.setSotmTargetPosition(solution.getShootAngle());

                Logger.recordOutput("SOTM/Valid", true);
                Logger.recordOutput("SOTM/TurretTarget", turretTarget);
                Logger.recordOutput("SOTM/HoodTarget", solution.getShootAngle());
                Logger.recordOutput("SOTM/ShootSpeed", solution.getShootSpeed());
              } else {
                Logger.recordOutput("SOTM/Valid", false);
              }
            },
            turret,
            hood,
            flywheel)
        .beforeStarting(() -> Logger.recordOutput("SOTM/Active", true))
        .finallyDo(() -> Logger.recordOutput("SOTM/Active", false))
        .withName("ShootOnTheMove-TrackTarget");
  }

  /** Creates a SOTM tracking command that also overrides subsystem states. */
  public static Command trackTargetWithOverrides(
      Drive drive, Turret turret, Hood hood, Flywheel flywheel) {
    return Commands.parallel(
            turret.overrideState(TurretState.SOTM_TRACKING),
            hood.overrideState(HoodState.TRACKING),
            flywheel.overrideState(FlywheelState.TRACKING),
            trackTarget(drive, turret, hood, flywheel))
        .withName("ShootOnTheMove-TrackWithOverrides");
  }
}
