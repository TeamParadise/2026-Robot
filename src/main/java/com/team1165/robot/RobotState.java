/*
 * Copyright (c) 2026 Team Paradise - FRC 1165 (https://github.com/TeamParadise)
 *
 * Use of this source code is governed by the MIT License, which can be found in the LICENSE file at
 * the root directory of this project.
 */

package com.team1165.robot;

import com.team1165.robot.subsystems.drive.Drive;
import com.team1165.robot.subsystems.shooter.turret.Turret;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Transform2d;
import edu.wpi.first.math.util.Units;

public class RobotState {
  private Drive drive;
  private Turret turret;
  private static Pose2d odometryPose = Pose2d.kZero;
  private static double turretRotation = 0.0;

  RobotState(Drive drive, Turret turret) {
    this.drive = drive;
  }

  void update() {
    odometryPose = drive.getPose();
    turretRotation = turret.getPosition();
  }

  public static Pose2d getTurretPose() {
    return odometryPose
        .plus(new Transform2d(0.0, 0.192024, Rotation2d.kZero))
        .rotateBy(new Rotation2d(Units.rotationsToRadians(turretRotation)));
  }
}
