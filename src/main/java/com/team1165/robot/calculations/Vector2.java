/*
 * Copyright (c) 2026 Team Paradise - FRC 1165 (https://github.com/TeamParadise)
 *
 * Use of this source code is governed by the MIT License, which can be found in the LICENSE file at
 * the root directory of this project.
 */

package com.team1165.robot.calculations;

public class Vector2 {
  public double x, y;
  public Vector2(double x, double y) {
    this.x = x;
    this.y = y;
  }

  public double getX() { return x; }
  public double getY() { return y; }

  public void setX(double x) { this.x = x; }
  public void setY(double y) { this.y = y; }

  public double magnitude() { return Math.sqrt(x*x + y*y); }
  public Vector2 normalize() { return new Vector2(x/magnitude(), y/magnitude()); }

}
