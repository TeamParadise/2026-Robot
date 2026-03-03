/*
 * Copyright (c) 2026 Team Paradise - FRC 1165 (https://github.com/TeamParadise)
 *
 * Use of this source code is governed by the MIT License, which can be found in the LICENSE file at
 * the root directory of this project.
 */

package com.team1165.robot.subsystems.shooter.turret.io;

import org.littletonrobotics.junction.AutoLog;

public interface EncoderIO {
  /** Class used to store the IO values of the encoder of a turret. */
  @AutoLog
  class EncoderIOInputs {
    public double absolutePosition = 0.0;
    public double relativePosition = 0.0;
  }

  /**
   * Updates a {@link EncoderIOInputs} instance with the latest updates from this {@link EncoderIO}.
   *
   * @param inputs A {@link EncoderIOInputs} instance to update.
   */
  default void updateInputs(EncoderIOInputs inputs) {}

  /**
   * Set the relative position of the encoder to the provided position.
   *
   * @param position The position to set the relative position to.
   */
  default void setRelativePosition(double position) {}
}
