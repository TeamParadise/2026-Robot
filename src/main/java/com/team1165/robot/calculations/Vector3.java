/*
 * Copyright (c) 2026 Team Paradise - FRC 1165 (https://github.com/TeamParadise)
 *
 * Use of this source code is governed by the MIT License, which can be found in the LICENSE file at
 * the root directory of this project.
 */

package com.team1165.robot.calculations;

public class Vector3 {
  public double x, y, z;

  public Vector3(double x, double y, double z) {
    this.x = x;
    this.y = y;
    this.z = z;
  }
  public Vector3(Vector2 vector) {
    this.x = vector.getX();
    this.y = vector.getY();
    this.z = 0.0;
  }

  public double getX() {
    return x;
  }

  public double getY() {
    return y;
  }

  public double getZ() {
    return z;
  }

  public void setX(double x) {
    this.x = x;
  }

  public void setY(double y) {
    this.y = y;
  }

  public void setZ(double z) {
    this.z = z;
  }

  public double magnitude() {
    return Math.sqrt(x * x + y * y + z * z);
  }

  public Vector3 normalize() {
    return new Vector3(x / magnitude(), y / magnitude(), z / magnitude());
  }

  public Vector3 add(Vector3 v) {
    return new Vector3(x + v.x, y + v.y, z + v.z);
  }

  public Vector3 minus(Vector3 v) {
    return new Vector3(x - v.x, y - v.y, z - v.z);
  }
}
