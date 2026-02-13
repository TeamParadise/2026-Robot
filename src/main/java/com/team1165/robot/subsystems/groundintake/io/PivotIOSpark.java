/*
 * Copyright (c) 2026 Team Paradise - FRC 1165 (https://github.com/TeamParadise)
 *
 * Use of this source code is governed by the MIT License, which can be found in the LICENSE file at
 * the root directory of this project.
 */

package com.team1165.robot.subsystems.groundintake.io;

import static com.team1165.robot.subsystems.groundintake.GroundIntakeConstants.kD;
import static com.team1165.robot.subsystems.groundintake.GroundIntakeConstants.kI;
import static com.team1165.robot.subsystems.groundintake.GroundIntakeConstants.kP;
import static com.team1165.robot.subsystems.groundintake.GroundIntakeConstants.pivotMotorBaseConfig;

import com.ctre.phoenix6.configs.Slot0Configs;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.revrobotics.PersistMode;
import com.revrobotics.ResetMode;
import com.revrobotics.spark.SparkBase;
import com.revrobotics.spark.SparkBase.ControlType;
import com.revrobotics.spark.SparkClosedLoopController;
import com.revrobotics.spark.config.SparkBaseConfig;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.config.SparkFlexConfig;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.team1165.util.logging.motordata.SparkMotorData;
import com.team1165.util.vendor.rev.SparkConfig;
import com.team1165.util.vendor.rev.SparkUtils;

public class PivotIOSpark implements PivotIO {

  private final SparkBase pivotMotor;
  private final SparkBaseConfig pivotConfigurashun;
  private final SparkMotorData pivotMotorData;
  private final SparkClosedLoopController pivotController;


  public PivotIOSpark(SparkConfig pivotConfig) {
    pivotMotor = SparkUtils.createNewSpark(pivotConfig);

    pivotConfigurashun = pivotConfig.configuration();

    pivotMotorData = new SparkMotorData(pivotMotor, pivotConfig);

    pivotController = pivotMotor.getClosedLoopController();
  }


  @Override
  public void updatePivotInputs(PivotIOInputs inputs) {
    pivotMotorData.update();

    inputs.pivotMotor = pivotMotorData;
  }

  @Override
  public void runPivotVolts(double voltage) {
    pivotMotor.setVoltage(voltage);
  }

  @Override
  public void runPivotPosition(double pivotPosition) { pivotController.setSetpoint(pivotPosition, ControlType.kPosition);}

  @Override
  public void setPivotPID(Slot0Configs configs) {
    new Thread(
            () -> {
              SparkBaseConfig tempConfig = new SparkMaxConfig();
              tempConfig
                .closedLoop
                  .p(configs.kP)
                  .i(configs.kI)
                  .d(configs.kD)
                .feedForward
                  .kS(configs.kS)
                  .kA(configs.kA)
                  .kV(configs.kV);
              pivotMotor.configure(
                  tempConfig, ResetMode.kNoResetSafeParameters, PersistMode.kNoPersistParameters);
            })
        .start();
  }

  @Override
  public void resetPivot() { pivotMotor.getEncoder().setPosition(0); }

  @Override
  public void stop() {
    pivotMotor.set(0);
  }

  @Override
  public void setBrakeMode(boolean enabled) {
    new Thread(
        () -> {
          pivotMotor.configure(
            pivotConfigurashun.idleMode(enabled ? IdleMode.kBrake : IdleMode.kCoast),
            ResetMode.kNoResetSafeParameters,
            PersistMode.kNoPersistParameters);
        })
        .start();
  }
}
