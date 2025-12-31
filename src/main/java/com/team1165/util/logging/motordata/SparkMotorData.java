/*
 * Copyright (c) 2025-2026 Team Paradise - FRC 1165 (https://github.com/TeamParadise)
 *
 * Use of this source code is governed by the MIT License, which can be found in the LICENSE file at
 * the root directory of this project.
 */

package com.team1165.util.logging.motordata;

import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkBase;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.config.SignalsConfig;
import com.revrobotics.spark.config.SparkFlexConfig;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.team1165.util.constants.AlertConstants;
import com.team1165.util.constants.CANConstants;
import com.team1165.util.constants.CANFrequency;
import com.team1165.util.vendor.rev.SparkConfig;
import com.team1165.util.vendor.rev.SparkModel;
import edu.wpi.first.math.filter.Debouncer;
import edu.wpi.first.wpilibj.Alert;
import edu.wpi.first.wpilibj.Alert.AlertType;

/**
 * {@link MotorData} class that uses a REV SPARK (MAX/FLEX) motor controller with a relative encoder
 * to log data.
 */
public class SparkMotorData extends MotorData {
  // SPARK and encoder to grab data from
  private final SparkBase spark;
  private final RelativeEncoder encoder;

  // Model to use for configuration if updating frequency
  private final SparkModel model;

  // Alerts to send if any issues arise with the SPARK or motor
  private final Alert connectedAlert;
  private final Alert faultAlert;

  /** Debouncer to avoid false disconnection alerts. */
  private final Debouncer connnectedDebouncer = new Debouncer(0.20);

  /**
   * Creates a new {@link SparkMotorData} using the specified constants.
   *
   * @param spark The {@link SparkBase} to log data from.
   * @param config The {@link SparkConfig} for this SPARK. Used for the name, model, and CAN ID.
   */
  public SparkMotorData(SparkBase spark, SparkConfig config) {
    // Get the SPARK and encoder to log data from
    this.spark = spark;
    this.encoder = spark.getEncoder();

    // Save SPARK model for updating frequency
    model = config.model();

    // Create alerts with the name and CAN ID of the SPARK
    connectedAlert =
        new Alert(
            AlertConstants.general,
            "SPARK \"" + config.name() + "\" (ID: " + config.canId() + ") is disconnected!",
            AlertType.kError);
    faultAlert =
        new Alert(
            AlertConstants.general,
            "SPARK \"" + config.name() + "\" (ID: " + config.canId() + ") has active faults!",
            AlertType.kError);
  }

  @Override
  void setFrequency(MotorField field) {
    // Get period to set from the CAN frequency
    int period = (int) (1000 / CANFrequency.FAST.getFrequency(CANConstants.rio));

    // Create SignalsConfig to be used
    SignalsConfig signalsConfig =
        switch (field) {
          case APPLIED_VOLTS ->
              new SignalsConfig().appliedOutputPeriodMs(period).busVoltagePeriodMs(period);
          case FAULT_ACTIVE, FAULTS -> new SignalsConfig().faultsPeriodMs(period);
          case MOTOR_TEMP -> new SignalsConfig().motorTemperaturePeriodMs(period);
          case OUTPUT_CURRENT -> new SignalsConfig().outputCurrentPeriodMs(period);
          case POSITION -> new SignalsConfig().primaryEncoderPositionPeriodMs(period);
          case SUPPLY_CURRENT ->
              new SignalsConfig().outputCurrentPeriodMs(period).appliedOutputPeriodMs(period);
          case VELOCITY -> new SignalsConfig().primaryEncoderVelocityPeriodMs(period);
          default -> new SignalsConfig();
        };

    // Configure the SPARK async to prevent blocking robot code
    spark.configureAsync(
        (model == SparkModel.SparkMax ? new SparkMaxConfig() : new SparkFlexConfig())
            .apply(signalsConfig),
        ResetMode.kNoResetSafeParameters,
        PersistMode.kNoPersistParameters);
  }
}
