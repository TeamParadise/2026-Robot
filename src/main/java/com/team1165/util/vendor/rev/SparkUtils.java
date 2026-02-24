/*
 * Copyright (c) 2025-2026 Team Paradise - FRC 1165 (https://github.com/TeamParadise)
 *
 * Use of this source code is governed by the MIT License, which can be found in the LICENSE file at
 * the root directory of this project.
 */

package com.team1165.util.vendor.rev;

import com.ctre.phoenix6.configs.MotionMagicConfigs;
import com.ctre.phoenix6.configs.Slot0Configs;
import com.ctre.phoenix6.signals.GravityTypeValue;
import com.revrobotics.PersistMode;
import com.revrobotics.REVLibError;
import com.revrobotics.ResetMode;
import com.revrobotics.spark.SparkBase;
import com.revrobotics.spark.SparkFlex;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.ClosedLoopConfig;
import com.team1165.util.constants.AlertConstants;
import edu.wpi.first.wpilibj.Alert;
import edu.wpi.first.wpilibj.Alert.AlertType;
import java.util.function.BooleanSupplier;
import java.util.function.DoubleSupplier;
import java.util.function.Supplier;

/** Class containing various utilities to interface with SPARK motor controllers. */
public final class SparkUtils {
  /** Private constructor to prevent instantiation. */
  private SparkUtils() {}

  /**
   * Creates and configures a REV SPARK ({@link SparkBase}) motor controller with the provided
   * config.
   *
   * @param config The full config for the SPARK motor controller.
   * @return The new {@link SparkBase} created.
   */
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

    return spark;
  }

  /**
   * Create a {@link ClosedLoopConfig} from a {@link Slot0Configs}.
   *
   * @param gains The {@link Slot0Configs} to utilize as a base.
   * @return A {@link ClosedLoopConfig} with values from the gains.
   */
  public static ClosedLoopConfig createClosedLoopConfig(Slot0Configs gains) {
    var config = new ClosedLoopConfig();

    // Configure basic PID and feedforward
    config.pid(gains.kP, gains.kI, gains.kD).feedForward.sva(gains.kS, gains.kV, gains.kA);

    // Configure kG/kCosRatio
    if (gains.GravityType == GravityTypeValue.Elevator_Static) {
      config.feedForward.kG(gains.kG);
    } else {
      config.feedForward.kCos(gains.kG);
    }

    return config;
  }

  /**
   * Create a {@link ClosedLoopConfig} from a {@link Slot0Configs} and {@link MotionMagicConfigs}.
   *
   * @param gains The {@link Slot0Configs} to utilize as a base.
   * @param motionProfile The {@link MotionMagicConfigs} to utilize as a base.
   * @return A {@link ClosedLoopConfig} with values from the gains and profile.
   */
  public static ClosedLoopConfig createClosedLoopConfig(
      Slot0Configs gains, MotionMagicConfigs motionProfile) {
    var config = createClosedLoopConfig(gains);

    config
        .maxMotion
        .cruiseVelocity(motionProfile.MotionMagicCruiseVelocity)
        .maxAcceleration(motionProfile.MotionMagicAcceleration);

    return config;
  }

  // ifOkOrDefault() methods are used because of:
  // https://www.chiefdelphi.com/t/advantagekit-2024-log-replay-again/442968/193
  /**
   * Returns a value from a SPARK ({@link SparkBase}, or the default if the value is invalid.
   *
   * @param spark The {@link SparkBase} that the value is from.
   * @param supplier The value to attempt to get from the SPARK.
   * @param defaultValue The default value to be returned if the value is invalid.
   * @param errorMethod A method to call if an error is reported.
   * @return The value from the SPARK, or the default if the value is invalid.
   */
  public static boolean ifOkOrDefault(
      SparkBase spark, BooleanSupplier supplier, boolean defaultValue, Runnable errorMethod) {
    var value = supplier.getAsBoolean();
    if (spark.getLastError() == REVLibError.kOk) {
      return value;
    } else {
      errorMethod.run();
      return defaultValue;
    }
  }

  /**
   * Returns a value from a SPARK ({@link SparkBase}, or the default if the value is invalid.
   *
   * @param spark The {@link SparkBase} that the value is from.
   * @param supplier The value to attempt to get from the SPARK.
   * @param defaultValue The default value to be returned if the value is invalid.
   * @return The value from the SPARK, or the default if the value is invalid.
   */
  public static boolean ifOkOrDefault(
      SparkBase spark, BooleanSupplier supplier, boolean defaultValue) {
    var value = supplier.getAsBoolean();
    if (spark.getLastError() == REVLibError.kOk) {
      return value;
    } else {
      return defaultValue;
    }
  }

  /**
   * Returns a value from a SPARK ({@link SparkBase}, or the default if the value is invalid.
   *
   * @param spark The {@link SparkBase} that the value is from.
   * @param supplier The value to attempt to get from the SPARK.
   * @param defaultValue The default value to be returned if the value is invalid.
   * @param errorMethod A method to call if an error is reported.
   * @return The value from the SPARK, or the default if the value is invalid.
   */
  public static double ifOkOrDefault(
      SparkBase spark, DoubleSupplier supplier, double defaultValue, Runnable errorMethod) {
    var value = supplier.getAsDouble();
    if (spark.getLastError() == REVLibError.kOk) {
      return value;
    } else {
      errorMethod.run();
      return defaultValue;
    }
  }

  /**
   * Returns a value from a SPARK ({@link SparkBase}, or the default if the value is invalid.
   *
   * @param spark The {@link SparkBase} that the value is from.
   * @param supplier The value to attempt to get from the SPARK.
   * @param defaultValue The default value to be returned if the value is invalid.
   * @return The value from the SPARK, or the default if the value is invalid.
   */
  public static double ifOkOrDefault(
      SparkBase spark, DoubleSupplier supplier, double defaultValue) {
    var value = supplier.getAsDouble();
    if (spark.getLastError() == REVLibError.kOk) {
      return value;
    } else {
      return defaultValue;
    }
  }

  /**
   * Returns a value from a SPARK ({@link SparkBase}, or the default if the value is invalid.
   *
   * @param spark The {@link SparkBase} that the value is from.
   * @param supplier The value to attempt to get from the SPARK.
   * @param defaultValue The default value to be returned if the value is invalid.
   * @param errorMethod A method to call if an error is reported.
   * @return The value from the SPARK, or the default if the value is invalid.
   */
  public static <T> T ifOkOrDefault(
      SparkBase spark, Supplier<T> supplier, T defaultValue, Runnable errorMethod) {
    var value = supplier.get();
    if (spark.getLastError() == REVLibError.kOk) {
      return value;
    } else {
      errorMethod.run();
      return defaultValue;
    }
  }

  /**
   * Returns a value from a SPARK ({@link SparkBase}, or the default if the value is invalid.
   *
   * @param spark The {@link SparkBase} that the value is from.
   * @param supplier The value to attempt to get from the SPARK.
   * @param defaultValue The default value to be returned if the value is invalid.
   * @return The value from the SPARK, or the default if the value is invalid.
   */
  public static <T> T ifOkOrDefault(SparkBase spark, Supplier<T> supplier, T defaultValue) {
    var value = supplier.get();
    if (spark.getLastError() == REVLibError.kOk) {
      return value;
    } else {
      return defaultValue;
    }
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
