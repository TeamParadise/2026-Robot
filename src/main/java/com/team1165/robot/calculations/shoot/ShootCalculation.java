/*
 * Copyright (c) 2026 Team Paradise - FRC 1165 (https://github.com/TeamParadise)
 *
 * Use of this source code is governed by the MIT License, which can be found in the LICENSE file at
 * the root directory of this project.
 */

package com.team1165.robot.calculations.shoot;

import com.team1165.robot.calculations.PhysicsConstants;
import com.team1165.robot.calculations.Vector2;
import com.team1165.robot.calculations.Vector3;
import com.team1165.robot.globalconstants.FieldConstants.Hub;
import com.team1165.robot.subsystems.drive.Drive;
import java.util.OptionalDouble;

public final class ShootCalculation {

  private static final double g = PhysicsConstants.gFeetPerSecond;

  private ShootCalculation() {}

  /**
   * Calculates the angle relative to the horizon to shoot the ball
   *
   * @param height The height of the output of the shooter
   * @param dHeight The desired height for the vertex of the path of the ball
   * @param xyVelocity The velocity of the ball on the xy plane
   * @return The angle to shoot the ball as a double in radians
   */
  public static double calculateAngle(double height, double dHeight, double xyVelocity) {
    return Math.atan2(Math.sqrt(-2 * g * (dHeight - height)), xyVelocity);
  }

  /**
   * Converts shootAngle in radians to a double 0 to 1, where 1 is one full rotation (2 pi)
   *
   * @return A usable value for motor position
   */
  public static double calculateHoodAngle(double shootAngle) {
    return shootAngle / (2 * Math.PI);
  }

  /**
   * Calculates the exit speed of the fuel
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
  public static OptionalDouble calculateMotorVoltage(double ballSpeed) {
    return OptionalDouble.of((ballSpeed + 1.49632) / 2.699);
  }

  /**
   * Calculates the vector at which a ball should be shot in order to make it into the hub.
   *
   * @param drive The {@link Drive} for the robot. Used to calculate the speed and position.
   * @return The {@link Vector2} along the XY plane in which the ball should be shot.
   */
  public static Vector2 calculateShootVector(Drive drive) {
    Vector2 robotPosition = new Vector2(drive.getPose().getX(), drive.getPose().getY());

    Vector3 hubPosition = new Vector3(Hub.topCenterPoint.getX(), Hub.topCenterPoint.getY(), 6.0);
    Vector2 hubPosition2D = new Vector2(hubPosition);

    Vector2 robotSpeed =
        new Vector2(
            drive.getSpeeds().vxMetersPerSecond * 3.28084,
            drive.getSpeeds().vyMetersPerSecond * 3.28084);
    Vector2 vecToHub = robotPosition.minus(hubPosition2D);
    Vector2 shootVector = vecToHub.minus(robotSpeed);

    return shootVector;
  }
}
