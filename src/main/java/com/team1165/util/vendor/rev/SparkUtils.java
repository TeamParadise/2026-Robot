/*
 * Copyright (c) 2025-2026 Team Paradise - FRC 1165 (https://github.com/TeamParadise)
 *
 * Use of this source code is governed by the MIT License, which can be found in the LICENSE file at
 * the root directory of this project.
 */

package com.team1165.util.vendor.rev;

import com.revrobotics.REVLibError;
import com.revrobotics.spark.SparkBase;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkFlex;
import com.revrobotics.spark.SparkMax;
import com.team1165.util.constants.AlertConstants;
import edu.wpi.first.wpilibj.Alert;
import edu.wpi.first.wpilibj.Alert.AlertType;
import java.util.function.Supplier;

/** Class containing various utilities to interface with SPARK motor controllers. */
public class SparkUtils {
  /** Private constructor in order to prevent instantiation. */
  private SparkUtils() {}

  /**
   * Creates and configures a REV SPARK ({@link SparkBase}) motor controller with the provided
   * config.
   *
   * @param config The full config for the SPARK motor controller.
   * @return The new {@link SparkBase} created.
   */
  @SuppressWarnings("resource")
  public static SparkBase createNewSpark(SparkConfig config) {
    // Create the SPARK based on the model provided
    final var spark =
        config.model() == SparkModel.SparkFlex
            ? new SparkFlex(config.canId(), config.motorType())
            : new SparkMax(config.canId(), config.motorType());

    // Configure the SPARK
    boolean success =
        tryUntilOk(
            5,
            () ->
                spark.configure(
                    config.configuration(),
                    ResetMode.kResetSafeParameters,
                    PersistMode.kPersistParameters));

    // Alert if the configuration was never successful
    if (!success) {
      new Alert(
              AlertConstants.init,
              "SPARK \""
                  + config.name()
                  + "\" (ID: "
                  + config.canId()
                  + ") configuration has failed. Unexpected behavior may occur.",
              AlertType.kError)
          .set(true);
    }

    // TODO: Consider doing some sort of default configuration with encoder filtering

    return spark;
  }

  /**
   * Attempts to run the provided method/supplier until no error is produced.
   *
   * @param maxAttempts The maximum number of times to try before giving up.
   * @param method The method to run and check.
   * @return If the method was successful.
   */
  public static boolean tryUntilOk(int maxAttempts, Supplier<REVLibError> method) {
    for (int i = 0; i < maxAttempts; i++) {
      if (method.get() == REVLibError.kOk) return true;
    }
    return false;
  }
}
