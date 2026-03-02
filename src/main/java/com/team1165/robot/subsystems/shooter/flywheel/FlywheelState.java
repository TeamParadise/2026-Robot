/*
 * Copyright (c) 2026 Team Paradise - FRC 1165 (https://github.com/TeamParadise)
 *
 * Use of this source code is governed by the MIT License, which can be found in the LICENSE file at
 * the root directory of this project.
 */

package com.team1165.robot.subsystems.shooter.flywheel;

import com.team1165.util.statemachine.v1.State;
import com.team1165.util.tunables.TunableNumber;

/** Possible states for the Flywheel subsystem. */
public enum FlywheelState implements State {
  /** Coast to zero — motors are idle with no active output. */
  IDLE(0.0),

  /**
   * Adapt flywheel speed based on the distance to the hub. The voltage value here is a placeholder
   * default; the actual setpoint will be computed dynamically once distance-based automation is
   * implemented alongside the turret and hood.
   */
  TRACKING(8.0),

  /**
   * Run the flywheel at a fixed voltage for shooting from a known, set distance away from the hub.
   * Acts as a reliable backup when tracking is unavailable or unnecessary.
   */
  FIXED(8.0);

  private final TunableNumber voltage;

  FlywheelState(double voltage) {
    this.voltage =
        new TunableNumber(Flywheel.class.getName() + "/DualRoller/Voltages/" + name(), voltage);
  }

  FlywheelState() {
    this(0.0);
  }

  public double getDualRollerVoltage() {
    return voltage.get();
  }
}
