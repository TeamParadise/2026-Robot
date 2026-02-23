/*
 * Copyright (c) 2025-2026 Team Paradise - FRC 1165 (https://github.com/TeamParadise)
 *
 * Use of this source code is governed by the MIT License, which can be found in the LICENSE file at
 * the root directory of this project.
 */

package com.team1165.util.tunables;

import com.ctre.phoenix6.configs.MotionMagicConfigs;
import com.team1165.util.tunables.wrappers.numbers.LoggedNumberWrapper;
import com.team1165.util.tunables.wrappers.numbers.NumberWrapper;
import com.team1165.util.tunables.wrappers.numbers.StaticNumberWrapper;

/**
 * Class for a set of tunable motion profiling values, which can be adjusted in real time while the
 * robot is running, if {@link TuningManager} is enabled, or return a static value otherwise.
 */
public class TunableMotionProfile extends Tunable {
  protected final String key;
  private NumberWrapper cruiseVelocity;
  private NumberWrapper acceleration;
  private NumberWrapper jerk;
  private NumberWrapper exponentialV;
  private NumberWrapper exponentialA;

  /**
   * Creates a new {@link TunableMotionProfile}.
   *
   * @param key The key to put the values under in the "Tuning" key on the dashboard.
   * @param cruiseVelocity The default value for cruise velocity.
   * @param acceleration The default value for target acceleration.
   * @param jerk The default value for target jerk (acceleration derivative).
   * @param exponentialV The new target kV value to use.
   * @param exponentialA The new target kA value to use.
   */
  public TunableMotionProfile(
      String key,
      double cruiseVelocity,
      double acceleration,
      double jerk,
      double exponentialV,
      double exponentialA) {
    this.key = TuningManager.tuningKey + key;
    // Create initial NumberWrappers
    updateTuningMode(cruiseVelocity, acceleration, jerk, exponentialV, exponentialA);
  }

  /**
   * Creates a new {@link TunableMotionProfile}.
   *
   * @param key The key to put the values under in the "Tuning" key on the dashboard.
   * @param configs The {@link MotionMagicConfigs} to get the default values from.
   */
  public TunableMotionProfile(String key, MotionMagicConfigs configs) {
    this(
        key,
        configs.MotionMagicCruiseVelocity,
        configs.MotionMagicAcceleration,
        configs.MotionMagicJerk,
        configs.MotionMagicExpo_kV,
        configs.MotionMagicExpo_kA);
  }

  /** Returns the current cruise velocity value. */
  public double getCruiseVelocity() {
    return cruiseVelocity.get();
  }

  /** Returns the current target acceleration value. */
  public double getAcceleration() {
    return acceleration.get();
  }

  /** Returns the current target jerk (acceleration derivative). */
  public double getJerk() {
    return jerk.get();
  }

  /** Returns the current target kV value. */
  public double getExponentialV() {
    return exponentialV.get();
  }

  /** Returns the current target kA value. */
  public double getExponentialA() {
    return exponentialA.get();
  }

  /** Returns a new {@link MotionMagicConfigs} with the current motion profiling values. */
  public MotionMagicConfigs getConfigs() {
    return new MotionMagicConfigs()
        .withMotionMagicCruiseVelocity(cruiseVelocity.get())
        .withMotionMagicAcceleration(acceleration.get())
        .withMotionMagicJerk(jerk.get())
        .withMotionMagicExpo_kV(exponentialV.get())
        .withMotionMagicExpo_kA(exponentialA.get());
  }

  /**
   * Returns whether any motion profiling values have changed since the last check.
   *
   * @param id Unique identifier for the caller to avoid conflicts when shared between multiple
   *     objects. The recommended approach is to pass the result of {@code hashCode()}.
   */
  public boolean hasChanged(int id) {
    return cruiseVelocity.hasChanged(id)
        || acceleration.hasChanged(id)
        || jerk.hasChanged(id)
        || exponentialV.hasChanged(id)
        || exponentialA.hasChanged(id);
  }

  /**
   * Updates the tuning mode status using the status from {@link TuningManager}.
   *
   * @param cruiseVelocity The new cruise velocity value to use.
   * @param acceleration The new target acceleration value to use.
   * @param jerk The new target jerk (acceleration derivative) value to use.
   * @param exponentialV The new target kV value to use.
   * @param exponentialA The new target kA value to use.
   */
  private void updateTuningMode(
      double cruiseVelocity,
      double acceleration,
      double jerk,
      double exponentialV,
      double exponentialA) {
    if (TuningManager.get()) {
      this.cruiseVelocity = new LoggedNumberWrapper(key + "/CruiseVelocity", cruiseVelocity);
      this.acceleration = new LoggedNumberWrapper(key + "/Acceleration", acceleration);
      this.jerk = new LoggedNumberWrapper(key + "/Jerk", jerk);
      this.exponentialV = new LoggedNumberWrapper(key + "/kV", exponentialV);
      this.exponentialA = new LoggedNumberWrapper(key + "/kA", exponentialA);
    } else {
      this.cruiseVelocity = new StaticNumberWrapper(cruiseVelocity);
      this.acceleration = new StaticNumberWrapper(acceleration);
      this.jerk = new StaticNumberWrapper(jerk);
      this.exponentialV = new StaticNumberWrapper(exponentialV);
      this.exponentialA = new StaticNumberWrapper(exponentialA);
    }
  }

  @Override
  void updateTuningMode() {
    updateTuningMode(
        cruiseVelocity.get(),
        acceleration.get(),
        jerk.get(),
        exponentialV.get(),
        exponentialA.get());
  }
}
