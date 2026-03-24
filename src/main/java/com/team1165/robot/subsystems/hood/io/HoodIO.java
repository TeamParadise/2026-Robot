/*
 * Copyright (c) 2026 Team Paradise - FRC 1165 (https://github.com/TeamParadise)
 *
 * Use of this source code is governed by the MIT License, which can be found in the LICENSE file at
 * the root directory of this project.
 */

package com.team1165.robot.subsystems.hood.io;

import com.ctre.phoenix6.configs.Slot0Configs;
import com.team1165.util.logging.motordata.GenericMotorData;
import com.team1165.util.logging.motordata.MotorData;

public interface HoodIO {
  class HoodIOInputs {
    public MotorData motor = new GenericMotorData();
  }

  default void updateInputs(HoodIOInputs inputs) {}

  default void runVolts(double voltage) {}

  default void runPosition(double hoodPosition) {}

  default void setPID(Slot0Configs configs) {}

  default void reset() {}

  default void stop() {}

  default void setBrakeMode(boolean enabled) {}
}
