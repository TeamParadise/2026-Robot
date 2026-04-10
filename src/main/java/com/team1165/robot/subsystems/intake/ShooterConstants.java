/*
 * Copyright (c) 2026 Team Paradise - FRC 1165 (https://github.com/TeamParadise)
 *
 * Use of this source code is governed by the MIT License, which can be found in the LICENSE file at
 * the root directory of this project.
 */

package com.team1165.robot.subsystems.intake;

import com.team1165.robot.calculations.BasicShooterLUT.ShooterParameters;

public class ShooterConstants {
  private ShooterConstants() {}

  public static final ShooterParameters hub = new ShooterParameters(4000 / 60.0, 0.5);
  public static final ShooterParameters tower = new ShooterParameters(4500 / 60.0, 1.3);
  public static final ShooterParameters corner = new ShooterParameters(4700 / 60.0, 2.0);
  public static final ShooterParameters closePass = new ShooterParameters(5000 / 60.0, 3.0);
  public static final ShooterParameters farPass = new ShooterParameters(7000 / 60.0, 4.3);
}
