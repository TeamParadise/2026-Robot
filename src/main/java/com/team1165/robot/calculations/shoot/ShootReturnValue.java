/*
 * Copyright (c) 2026 Team Paradise - FRC 1165 (https://github.com/TeamParadise)
 *
 * Use of this source code is governed by the MIT License, which can be found in the LICENSE file at
 * the root directory of this project.
 */

package com.team1165.robot.calculations.shoot;

public class ShootReturnValue {
  double shootAngle;
  double shootSpeed;
  double shootDirection;

  public ShootReturnValue(double shootAngle, double shootVelocity, double shootDirection) {
    this.shootAngle = shootAngle;
    this.shootSpeed = shootVelocity;
    this.shootDirection = shootDirection;
  }

  public double getShootAngle() {
    return shootAngle;
  }

  public double getShootSpeed() {
    return shootSpeed;
  }

  public double getShootDirection() {
    return shootDirection;
  }

  public void setShootAngle(double shootAngle) {
    this.shootAngle = shootAngle;
  }

  public void setShootSpeed(double shootSpeed) {
    this.shootSpeed = shootSpeed;
  }

  public void setShootDirection(double shootDirection) {
    this.shootDirection = shootDirection;
  }
}
