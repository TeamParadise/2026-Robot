/*
 * Copyright (c) 2026 Team Paradise - FRC 1165 (https://github.com/TeamParadise)
 *
 * Use of this source code is governed by the MIT License, which can be found in the LICENSE file at
 * the root directory of this project.
 */

package com.team1165.robot.calculations;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.interpolation.InterpolatingTreeMap;
import edu.wpi.first.math.interpolation.Interpolator;
import edu.wpi.first.math.interpolation.InverseInterpolator;

public class BasicShooterLUT {
  public static final InterpolatingTreeMap<Double, ShooterParameters> lut = new InterpolatingTreeMap<>(
      InverseInterpolator.forDouble(), ShooterParameters.interpolator());

  static {
    lut.put(0.0, new ShooterParameters(0.0, 0.0));
  }

  public record ShooterParameters(double rpm, double angle) {
    public static Interpolator<ShooterParameters> interpolator() {
      return (start, end, t) ->
          new ShooterParameters(
              MathUtil.interpolate(start.rpm, end.rpm, t),
              MathUtil.interpolate(start.angle, end.angle, t));
    }
  };
}
