/*
 * Copyright (c) 2026 Team Paradise - FRC 1165 (https://github.com/TeamParadise)
 *
 * Use of this source code is governed by the MIT License, which can be found in the LICENSE file at
 * the root directory of this project.
 */

package com.team1165.robot.subsystems.turret.io;

import com.team1165.util.logging.motordata.GenericMotorData;
import com.team1165.util.logging.motordata.MotorData;
import org.littletonrobotics.junction.AutoLog;

public interface TurretIO {

  @AutoLog
  class TurretIOInputs {

    public MotorData turretMotor = new GenericMotorData();

    @Override
    public TurretIOInputs clone() {
      TurretIOInputs copy = new TurretIOInputs();
      copy.turretMotor = this.turretMotor;
      return copy;
    }
  }

  default void updateInputs(TurretIOInputs inputs) {}

  default void runVolts(double voltage) {}

  default void stop() {}

  default void setBrakeMode(boolean enabled) {}
}
