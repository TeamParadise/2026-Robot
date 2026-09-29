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
  public static final InterpolatingTreeMap<Double, ShooterParameters> lut =
      new InterpolatingTreeMap<>(InverseInterpolator.forDouble(), ShooterParameters.interpolator());

  static {
    lut.put(2.17, new ShooterParameters(4150 / 60.0, 0.0, 0.0));
    lut.put(2.638, new ShooterParameters(4150 / 60.0, 0.5, 0.0));
    lut.put(3.05, new ShooterParameters(4350 / 60.0, 0.75, 0.0));
    lut.put(3.8, new ShooterParameters(4400 / 60.0, 1.0, 0.0));
    lut.put(4.2, new ShooterParameters(4750 / 60.0, 1.25, 0.0));
    lut.put(4.6, new ShooterParameters(4850 / 60.0, 1.35, 0.0));
    lut.put(5.1, new ShooterParameters(4950 / 60.0, 1.45, 0.0));
  }

  public record ShooterParameters(double rps, double angle, double tof) {
    public static Interpolator<ShooterParameters> interpolator() {
      return (start, end, t) ->
          new ShooterParameters(
              MathUtil.interpolate(start.rps, end.rps, t),
              MathUtil.interpolate(start.angle, end.angle, t),
              MathUtil.interpolate(start.tof, end.tof, t));
    }
  }
  ;
}
