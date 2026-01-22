/*
 * Copyright (c) 2026 Team Paradise - FRC 1165 (https://github.com/TeamParadise)
 *
 * Use of this source code is governed by the MIT License, which can be found in the LICENSE file at
 * the root directory of this project.
 */

package com.team1165.robot.subsystems.roller.spindexer;

import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkBaseConfig;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.team1165.robot.subsystems.roller.io.RollerIO.RollerIOInputs;
import com.team1165.util.statemachine.v1.OverridableStateMachine;
import com.team1165.robot.subsystems.roller.io.RollerIO;
import com.team1165.util.statemachine.v2.StateUtils;
import com.team1165.robot.subsystems.roller.io.RollerIOSpark;
import com.team1165.util.vendor.rev.SparkConfig;
import com.team1165.util.vendor.rev.SparkModel;
import edu.wpi.first.wpilibj.Alert;
import edu.wpi.first.wpilibj.Alert.AlertType;



public class Spindexer extends OverridableStateMachine<SpindexerState> {

  private final RollerIO io;
  private final RollerIOInputs inputs = new RollerIOInputs();

  private final EnumMap<SpindexerState, LoggedTunableNumber> tunableMap =
      StateUtils.createTunableNumberMap(name + "/Voltages", SpindexerState.class);

  public Spindexer(RollerIO io) {
    super(SpindexerState.OFF);
    this.io = io;
  }

  public double getCurrent() {
    return inputs.primaryMotor.getOutputCurrentAmps();
  }

  @Override
  protected void update() {
    io.updateInputs(inputs);
  }

  @Override
  protected void transition() {
    io.runVolts(tunableMap.get(getCurrentState()).get());
  }

}