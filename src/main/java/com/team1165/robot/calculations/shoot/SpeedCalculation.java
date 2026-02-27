/*
 * Copyright (c) 2026 Team Paradise - FRC 1165 (https://github.com/TeamParadise)
 *
 * Use of this source code is governed by the MIT License, which can be found in the LICENSE file at
 * the root directory of this project.
 */

package com.team1165.robot.calculations.shoot;

import com.team1165.robot.calculations.PhysicsConstants;
import java.util.OptionalDouble;

public class SpeedCalculation {

  private static final double g = PhysicsConstants.gFeetPerSecond;

  /**
   * Returns the exit velocity the ball must have to conform to the given parameters
   *
   * @param shootAngle  The angle the ball will be shot at in <b>radians</b>
   * @param height      The initial height of the ball off the ground in <b>feet</b>
   * @param range       The desired range of the ball in <b>feet</b>
   * @return The necessary exit velocity of the ball in <b>feet per second</b>
   */
  static double calculateBallSpeed(double shootAngle, double height, double range) {
    return Math.sqrt( (g * range * range) / ( 2 * Math.pow(Math.cos(shootAngle), 2) * (range * Math.tan(shootAngle) + height) ) );
  }

  public static OptionalDouble calculateMotorVoltage(double ballSpeed) {
    return OptionalDouble.of(( ballSpeed + 1.49632 ) / 2.699 );
  }
}
