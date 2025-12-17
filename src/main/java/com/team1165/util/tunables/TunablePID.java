/*
 * Copyright (c) 2025-2026 Team Paradise - FRC 1165 (https://github.com/TeamParadise)
 *
 * Use of this source code is governed by the MIT License, which can be found in the LICENSE file at
 * the root directory of this project.
 */

package com.team1165.util.tunables;

import com.ctre.phoenix6.configs.Slot0Configs;
import com.ctre.phoenix6.configs.SlotConfigs;
import com.team1165.util.tunables.wrappers.numbers.LoggedNumberWrapper;
import com.team1165.util.tunables.wrappers.numbers.NumberWrapper;
import com.team1165.util.tunables.wrappers.numbers.StaticNumberWrapper;
import edu.wpi.first.math.controller.PIDController;

/**
 * Class for a set of tunable PID values, which can be adjusted in real time while the robot is
 * running, if {@link TuningManager} is enabled, or return a static value otherwise.
 */
public class TunablePID extends Tunable {
  protected final String key;
  private NumberWrapper kP;
  private NumberWrapper kI;
  private NumberWrapper kD;

  /**
   * Creates a new {@link TunablePID}.
   *
   * @param key The key to put the values under in the "Tuning" key on the dashboard.
   * @param kP The default value for kP (proportional gain).
   * @param kI The default value for kI (integral gain).
   * @param kD The default value for kD (derivative gain).
   */
  public TunablePID(String key, double kP, double kI, double kD) {
    this.key = TuningManager.tuningKey + key;
    // Create initial NumberWrappers
    updateTuningMode(kP, kI, kD);
  }

  /**
   * Creates a new {@link TunablePID}.
   *
   * @param key The key to put the values under in the "Tuning" key on the dashboard.
   * @param controller The {@link PIDController} to get the default values from.
   */
  public TunablePID(String key, PIDController controller) {
    this(key, controller.getP(), controller.getI(), controller.getD());
  }

  /**
   * Creates a new {@link TunablePID}.
   *
   * @param key The key to put the values under in the "Tuning" key on the dashboard.
   * @param gains The {@link Slot0Configs} to get the default values from.
   */
  public TunablePID(String key, Slot0Configs gains) {
    this(key, gains.kP, gains.kI, gains.kD);
  }

  /**
   * Creates a new {@link TunablePID}.
   *
   * @param key The key to put the values under in the "Tuning" key on the dashboard.
   * @param gains The {@link SlotConfigs} to get the default values from.
   */
  public TunablePID(String key, SlotConfigs gains) {
    this(key, gains.kP, gains.kI, gains.kD);
  }

  /** Returns the current {@code kP} (proportional gain) value. */
  public double getP() {
    return kP.get();
  }

  /** Returns the current {@code kI} (integral gain) value. */
  public double getI() {
    return kI.get();
  }

  /** Returns the current {@code kD} (derivative gain) value. */
  public double getD() {
    return kD.get();
  }

  /** Returns a new {@link Slot0Configs} with the current PID values. */
  public Slot0Configs getSlot0Configs() {
    return new Slot0Configs().withKP(kP.get()).withKI(kI.get()).withKD(kD.get());
  }

  /** Returns a new {@link SlotConfigs} with the current PID values. */
  public SlotConfigs getSlotConfigs() {
    return new SlotConfigs().withKP(kP.get()).withKI(kI.get()).withKD(kD.get());
  }

  /**
   * Returns whether any PID values have changed since the last check.
   *
   * @param id Unique identifier for the caller to avoid conflicts when shared between multiple
   *     objects. The recommended approach is to pass the result of {@code hashCode()}.
   */
  public boolean hasChanged(int id) {
    return kP.hasChanged(id) || kI.hasChanged(id) || kD.hasChanged(id);
  }

  /**
   * Updates the tuning mode status using the status from {@link TuningManager}.
   *
   * @param kP The new kP value to use (proportional gain).
   * @param kI The new kI value to use (integral gain).
   * @param kD The new kD value to use (derivative gain).
   */
  private void updateTuningMode(double kP, double kI, double kD) {
    if (TuningManager.get()) {
      this.kP = new LoggedNumberWrapper(key + "/kP", kP);
      this.kI = new LoggedNumberWrapper(key + "/kI", kI);
      this.kD = new LoggedNumberWrapper(key + "/kD", kD);
    } else {
      this.kP = new StaticNumberWrapper(kP);
      this.kI = new StaticNumberWrapper(kI);
      this.kD = new StaticNumberWrapper(kD);
    }
  }

  @Override
  void updateTuningMode() {
    updateTuningMode(kP.get(), kI.get(), kD.get());
  }
}
