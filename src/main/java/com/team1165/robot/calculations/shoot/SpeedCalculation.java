/*
 * Copyright (c) 2026 Team Paradise - FRC 1165 (https://github.com/TeamParadise)
 *
 * Use of this source code is governed by the MIT License, which can be found in the LICENSE file at
 * the root directory of this project.
 */

package com.team1165.robot.calculations.shoot;

import com.team1165.robot.calculations.Constants;

public class SpeedCalculation {

  private static final double g = Constants.gFeet;

  static double calculate(double shootAngle, double height, double range) {
    return Math.sqrt( (g * range * range) / ( 2 * Math.pow(Math.cos(shootAngle), 2) * (range * Math.tan(shootAngle) + height) ) );
  }

}
