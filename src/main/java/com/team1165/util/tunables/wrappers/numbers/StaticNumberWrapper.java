/*
 * Copyright (c) 2025-2026 Team Paradise - FRC 1165 (https://github.com/TeamParadise)
 *
 * Use of this source code is governed by the MIT License, which can be found in the LICENSE file at
 * the root directory of this project.
 */

package com.team1165.util.tunables.wrappers.numbers;

/** Class to wrap a static double value. */
@SuppressWarnings("ClassCanBeRecord")
public class StaticNumberWrapper implements NumberWrapper {
  private final double value;

  /**
   * Creates a {@link StaticNumberWrapper}.
   *
   * @param value The default value for the number.
   */
  public StaticNumberWrapper(double value) {
    this.value = value;
  }

  public double get() {
    return value;
  }
}
