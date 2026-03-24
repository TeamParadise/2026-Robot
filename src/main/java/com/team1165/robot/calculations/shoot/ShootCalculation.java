/*
 * Copyright (c) 2026 Team Paradise - FRC 1165 (https://github.com/TeamParadise)
 *
 * Use of this source code is governed by the MIT License, which can be found in the LICENSE file at
 * the root directory of this project.
 */

package com.team1165.robot.calculations.shoot;

import com.team1165.robot.calculations.PhysicsConstants;
import com.team1165.robot.globalconstants.FieldConstants.Hub;
import com.team1165.robot.subsystems.drive.Drive;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.geometry.Translation3d;
import java.util.Optional;

public final class ShootCalculation {

  private static final double g = PhysicsConstants.gFeetPerSecond;

  private ShootCalculation() {}

  /**
   * Calculates the angle relative to the horizon to shoot the ball.
   *
   * @param height The height of the output of the shooter
   * @param dHeight The desired height for the vertex of the path of the ball
   * @param xyVelocity The velocity of the ball on the xy plane
   * @return The angle to shoot the ball in radians
   */
  public static double calculateAngle(double height, double dHeight, double xyVelocity) {
    return Math.atan2(Math.sqrt(-2 * g * (dHeight - height)), xyVelocity);
  }

  /**
   * Converts shootAngle in radians to a double 0 to 1, where 1 is one full rotation (2 pi).
   *
   * @return A usable value for motor position
   */
  public static double calculateHoodAngle(double shootAngle) {
    return shootAngle / (2 * Math.PI);
  }

  /**
   * Calculates the exit speed of the fuel.
   *
   * @param shootAngle The angle that the fuel is being shot at relative to the horizon in radians
   * @param height The height of the output of the shooter in feet
   * @param range The distance from the output of the shooter and the hub on the xy plane in feet
   * @return The exit speed of the fuel in feet per second.
   */
  public static double calculateBallSpeed(double shootAngle, double height, double range) {
    return Math.sqrt(
        (g * range * range)
            / (2 * Math.pow(Math.cos(shootAngle), 2) * (range * Math.tan(shootAngle) + height)));
  }

  /**
   * Converts the initial speed of the fuel to voltage usable by the flywheel motors.
   *
   * @param ballSpeed The initial speed of the ball in feet per second.
   * @return The voltage to set the flywheel motors to in volts.
   */
  public static double calculateMotorVoltage(double ballSpeed) {
    return (ballSpeed + 1.49632) / 2.699;
  }

  /**
   * Calculates the vector at which a ball should be shot in order to make it into the hub.
   *
   * @param drive The {@link Drive} for the robot. Used to calculate the speed and position.
   * @return An {@link Optional} containing the shoot vector, or empty if too close to hub.
   */
  public static Optional<Translation2d> calculateShootVector(Drive drive) {
    Translation2d robotPosition = drive.getPose().getTranslation();

    Translation2d hubPosition2D =
        new Translation2d(Hub.topCenterPoint.getX(), Hub.topCenterPoint.getY());

    Translation2d robotSpeed =
        new Translation2d(
            drive.getSpeeds().vxMetersPerSecond * ShooterConstants.METERS_TO_FEET,
            drive.getSpeeds().vyMetersPerSecond * ShooterConstants.METERS_TO_FEET);

    Translation2d vecToHub = robotPosition.minus(hubPosition2D);
    if (vecToHub.getNorm() <= ShooterConstants.MIN_SHOOT_DISTANCE_FEET) {
      return Optional.empty();
    }

    Translation2d shootVector = vecToHub.minus(robotSpeed);
    return Optional.of(shootVector);
  }

  /**
   * Calculates the necessary values required for Shoot On The Move.
   *
   * @param drive The {@link Drive} object for position and speed data.
   * @param turretHeight The height of the turret off the ground in feet.
   * @param desiredMaxPathHeight The desired maximum height of the ball's flight path in feet.
   * @return An {@link Optional} containing a {@link ShootReturnValue}, or empty if too close.
   */
  public static Optional<ShootReturnValue> calculateShoot(
      Drive drive, double turretHeight, double desiredMaxPathHeight) {
    Optional<Translation2d> shootVelocityXYOpt = calculateShootVector(drive);
    if (shootVelocityXYOpt.isEmpty()) {
      return Optional.empty();
    }

    Translation2d shootVelocityXY = shootVelocityXYOpt.get();
    ShootReturnValue shootReturnValue = new ShootReturnValue(1.0, 1.0, 1.0);

    shootReturnValue.setShootDirection(shootVelocityXY.getAngle().getRadians());

    double shootAngle =
        calculateAngle(turretHeight, desiredMaxPathHeight, shootVelocityXY.getNorm());
    shootReturnValue.setShootAngle(calculateHoodAngle(shootAngle));

    double shootVelocityZ = shootVelocityXY.getNorm() * Math.sin(shootAngle);
    Translation3d shootVelocity3D =
        new Translation3d(shootVelocityXY.getX(), shootVelocityXY.getY(), shootVelocityZ);
    shootReturnValue.setShootSpeed(shootVelocity3D.getNorm());

    return Optional.of(shootReturnValue);
  }
}
