/*
 * Copyright (c) 2025-2026 Team Paradise - FRC 1165 (https://github.com/TeamParadise)
 *
 * Use of this source code is governed by the MIT License, which can be found in the LICENSE file at
 * the root directory of this project.
 */

package com.team1165.robot.subsystems.shooter.flywheel;

import com.ctre.phoenix6.configs.MotionMagicConfigs;
import com.ctre.phoenix6.configs.Slot0Configs;
import com.team1165.util.io.dualroller.DualRollerIO;
import com.team1165.util.io.dualroller.DualRollerIOInputsAutoLogged;
import com.team1165.util.statemachine.v1.OverridableStateMachine;
import com.team1165.util.tunables.TunableMotionProfile;
import com.team1165.util.tunables.TunablePIDF;
import org.littletonrobotics.junction.Logger;

/** State-machine-based Flywheel subsystem, powered by two motors. */
public class Flywheel extends OverridableStateMachine<FlywheelState> {

  private final DualRollerIO io;
  private final DualRollerIOInputsAutoLogged inputs = new DualRollerIOInputsAutoLogged();
  private final TunablePIDF pidf;
  private final TunableMotionProfile motionProfile;

  public Flywheel(DualRollerIO io, Slot0Configs configs, MotionMagicConfigs motionMagicConfigs) {
    // For now the Idle state in the enum will be 0, but it will change as building progresses
    super(FlywheelState.IDLE);

    this.io = io;
    this.pidf = new TunablePIDF(name + "FLywheel/PIDF", configs);
    this.motionProfile = new TunableMotionProfile(name + "Pivot/MotionProfile", motionMagicConfigs);
  }

  /**
   * Returns the output current of the primary flywheel motor.
   *
   * @return The output current in amps.
   */
  public double getOutputCurrent() {
    return inputs.primaryMotor.getOutputCurrentAmps();
  }

  /**
   * Returns the velocity of the primary flywheel motor.
   *
   * @return The velocity of the primary motor.
   */
  public double getVelocity() {
    return inputs.primaryMotor.getVelocity();
  }

  @Override
  protected void update() {
    io.updateInputs(inputs);
    if (pidf.hasChanged(hashCode())) io.setPIDF(pidf.getSlot0Configs());
    if (motionProfile.hasChanged(hashCode())) io.setMotionProfiling(motionProfile.getConfigs());
    Logger.processInputs(name, inputs);
  }

  @Override
  protected void transition() {
    switch (getCurrentState()) {
      case IDLE -> io.stop();
      case TRACKING ->
          // TODO: Implement distance-based speed calculation with other shooter components
          io.runVolts(getCurrentState().getDualRollerVoltage());
      case FIXED -> io.runVolts(getCurrentState().getDualRollerVoltage());
    }
  }
}
