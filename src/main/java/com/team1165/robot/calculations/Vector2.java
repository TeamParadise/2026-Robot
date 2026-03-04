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
  public Vector2(Vector3 vector)
  {
    this.x = vector.getX();
    this.y = vector.getY();
  }

  public double getX() { return x; }
  public double getY() { return y; }

  public void setX(double x) { this.x = x; }
  public void setY(double y) { this.y = y; }

  public double magnitude() { return Math.sqrt(x * x + y * y); }

  public double direction() { return Math.atan2(y, x); }

  public Vector2 normalize() { return new Vector2(x / magnitude(), y / magnitude()); }

  /** Adds the inputted vector to the parent of this method.
   * @param v The vector to add to the first vector.
   * @return The final sum vector
   * */
  public Vector2 add(Vector2 v) { return new Vector2(x + v.x, y + v.y); }

  /** Subtracts the inputted vector from the parent of this method.
   * @param v The vector to subtract from the first vector.
   * @return The final difference vector
   * */
  public Vector2 minus(Vector2 v) {
    return new Vector2(x - v.x, y - v.y);
  }
}
