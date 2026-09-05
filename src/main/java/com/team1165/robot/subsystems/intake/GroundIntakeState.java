/*
 * Copyright (c) 2026 Team Paradise - FRC 1165 (https://github.com/TeamParadise)
 *
 * Use of this source code is governed by the MIT License, which can be found in the LICENSE file at
 * the root directory of this project.
 */

package com.team1165.robot.subsystems.intake;

import com.team1165.util.statemachine.v1.State;
import com.team1165.util.tunables.TunableNumber;

/** State for the {@link GroundIntake}, including both roller voltage and pivot positions. */
@SuppressWarnings("ImmutableEnumChecker")
public enum GroundIntakeState implements State {
  /** Hold intake against the bumper. */
  HOLD_DOWN(-1.0, 0),
  /** Hold intake against the bumper and run the roller. */
  HOLD_DOWN_AND_INTAKE(-1.0, 12.5),
  /** Don't run pivot or roller. */
  IDLE(0, 0),
  /** Run pivot down (out of robot). */
  MOVE_DOWN(-4.0, 0),
  /** Run pivot up (into robot). */
  MOVE_UP(4.0, 0),
  /** Run the roller in reverse. */
  REVERSE_ROLLER(3, -12.5);

  private final TunableNumber pivotVoltage;
  private final TunableNumber rollerVoltage;

  GroundIntakeState(double pivotVoltage, double rollerVoltage) {
    this.pivotVoltage =
        new TunableNumber(GroundIntake.class.getName() + "/Pivot/Voltages/" + name(), pivotVoltage);
    this.rollerVoltage =
        new TunableNumber(
            GroundIntake.class.getName() + "/Roller/Voltages/" + name(), rollerVoltage);
  }

  public double getPivotVoltage() {
    return pivotVoltage.get();
  }

  public double getRollerVoltage() {
    return rollerVoltage.get();
  }
}
