/*
 * Copyright (c) 2026 Team Paradise - FRC 1165 (https://github.com/TeamParadise)
 *
 * Use of this source code is governed by the MIT License, which can be found in the LICENSE file at
 * the root directory of this project.
 */

package com.team1165.robot.subsystems.base.intake;

import com.team1165.util.statemachine.v1.State;
import com.team1165.util.tunables.TunableNumber;

/** State for the {@link GroundIntake}, including both roller voltage and pivot positions. */
public enum GroundIntakeState implements State {
  IDLE(0, 0),
  AGITATE(0, 0),
  AGITATE_AND_RUN(0,12 ),
  DEPLOY(0, 0),
  DEPLOY_AND_RUN(0, 12),
  DEPLOY_AND_REVERSE(0, -12),
  ;

  private final TunableNumber pivotPosition;
  private final TunableNumber rollerVoltage;

  GroundIntakeState(double pivotPosition, double rollerVoltage) {
    this.pivotPosition =
        new TunableNumber(
            GroundIntake.class.getName() + "/Pivot/Positions/" + name(), pivotPosition);
    this.rollerVoltage =
        new TunableNumber(
            GroundIntake.class.getName() + "/Roller/Voltages/" + name(), rollerVoltage);
  }

  public double getPivotPosition() {
    return pivotPosition.get();
  }

  public double getRollerVoltage() {
    return rollerVoltage.get();
  }
}
