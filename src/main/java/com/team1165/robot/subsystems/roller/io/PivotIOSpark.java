/*
 * Copyright (c) 2026 Team Paradise - FRC 1165 (https://github.com/TeamParadise)
 *
 * Use of this source code is governed by the MIT License, which can be found in the LICENSE file at
 * the root directory of this project.
 */

package com.team1165.robot.subsystems.roller.io;

import com.revrobotics.spark.SparkBase;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.config.SparkBaseConfig;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.team1165.robot.subsystems.roller.io.RollerIO.RollerIOInputs;
import com.team1165.util.logging.motordata.SparkMotorData;
import com.team1165.util.vendor.rev.SparkConfig;
import com.team1165.util.vendor.rev.SparkUtils;

public class PivotIOSpark implements PivotIO {

  private final SparkBase pivotMotor;
  private final SparkBaseConfig pivotConfigurashun;

  private final SparkMotorData pivotMotorData;


  public PivotIOSpark(SparkConfig pivotConfig) {
    pivotMotor = SparkUtils.createNewSpark(pivotConfig);

    pivotConfigurashun = pivotConfig.configuration();

    pivotMotorData = new SparkMotorData(pivotMotor, pivotConfig);
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
  public void stop() {
    pivotMotor.set(0);
    //    secondaryMotor.set(0);
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
