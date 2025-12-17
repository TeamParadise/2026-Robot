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
import edu.wpi.first.math.controller.SimpleMotorFeedforward;

/**
 * Class for a set of tunable PID and feedforward values, which can be adjusted in real time while
 * the robot is running, if {@link TuningManager} is enabled, or return a static value otherwise.
 */
public class TunablePIDF extends TunablePID {
  private NumberWrapper kS;
  private NumberWrapper kV;
  private NumberWrapper kA;
  private NumberWrapper kG;

  /**
   * Creates a new {@link TunablePIDF}.
   *
   * @param key The key to put the values under in the "Tuning" key on the dashboard.
   * @param kP The default value for kP (proportional gain).
   * @param kI The default value for kI (integral gain).
   * @param kD The default value for kD (derivative gain).
   * @param kS The default value for kS (static feedforward gain).
   * @param kV The default value for kV (velocity feedforward gain).
   * @param kA The default value for kA (acceleration feedforward gain).
   * @param kG The default value for kG (gravity feedforward/feedback gain).
   */
  public TunablePIDF(String key, double kP, double kI, double kD, double kS,  double kV, double kA, double kG) {
    super(key, kP, kI, kD);
    // Create initial NumberWrappers
    updateTuningMode(kS, kV, kA, kG);
  }

  /**
   * Creates a new {@link TunablePIDF}.
   *
   * @param key The key to put the values under in the "Tuning" key on the dashboard.
   * @param controller The {@link PIDController} to get the default values from.
   * @param feedforward The {@link SimpleMotorFeedforward} to get the default values from.
   */
  public TunablePIDF(String key, PIDController controller, SimpleMotorFeedforward feedforward) {
    this(key, controller.getP(), controller.getI(), controller.getD(), feedforward.getKs(), feedforward.getKv(), feedforward.getKa(), 0.0);
  }

  /**
   * Creates a new {@link TunablePIDF}.
   *
   * @param key The key to put the values under in the "Tuning" key on the dashboard.
   * @param gains The {@link Slot0Configs} to get the default values from.
   */
  public TunablePIDF(String key, Slot0Configs gains) {
    this(key, gains.kP, gains.kI, gains.kD, gains.kS, gains.kV, gains.kA, gains.kG);
  }

  /**
   * Creates a new {@link TunablePIDF}.
   *
   * @param key The key to put the values under in the "Tuning" key on the dashboard.
   * @param gains The {@link SlotConfigs} to get the default values from.
   */
  public TunablePIDF(String key, SlotConfigs gains) {
    this(key, gains.kP, gains.kI, gains.kD, gains.kS, gains.kV, gains.kA, gains.kG);
  }

  /** Returns the current {@code kS} (static feedforward gain) value. */
  public double getS() {
    return kS.get();
  }

  /** Returns the current {@code kV} (velocity feedforward gain) value. */
  public double getV() {
    return kV.get();
  }

  /** Returns the current {@code kA} (acceleration feedforward gain) value. */
  public double getA() {
    return kA.get();
  }

  /** Returns the current {@code kG} (gravity feedforward/feedback gain) value. */
  public double getG() {
    return kG.get();
  }

  /** Returns a new {@link Slot0Configs} with the current PID and feedforward values. */
  public Slot0Configs getSlot0Configs() {
    return super.getSlot0Configs().withKS(kS.get()).withKV(kV.get()).withKA(kA.get()).withKG(kG.get());
  }

  /** Returns a new {@link SlotConfigs} with the current PID and feedforward values. */
  public SlotConfigs getSlotConfigs() {
    return super.getSlotConfigs().withKS(kS.get()).withKV(kV.get()).withKA(kA.get()).withKG(kG.get());
  }

  /**
   * Returns whether any PID or feedforward values have changed since the last check.
   *
   * @param id Unique identifier for the caller to avoid conflicts when shared between multiple
   *     objects. The recommended approach is to pass the result of {@code hashCode()}.
   */
  public boolean hasChanged(int id) {
    return super.hasChanged(id) || kS.hasChanged(id) || kV.hasChanged(id) || kA.hasChanged(id) || kG.hasChanged(id);
  }

  /**
   * Updates the tuning mode status using the status from {@link TuningManager}.
   *
   * @param kS The new kS value to use (static feedforward gain).
   * @param kV The new kV value to use (velocity feedforward gain).
   * @param kA The new kA value to use (acceleration feedforward gain).
   * @param kG The new kG value to use (gravity feedforward/feedback gain).
   */
  private void updateTuningMode(double kS, double kV, double kA, double kG) {
    if (TuningManager.get()) {
      this.kS = new LoggedNumberWrapper(key + "/kS", kS);
      this.kV = new LoggedNumberWrapper(key + "/kV", kV);
      this.kA = new LoggedNumberWrapper(key + "/kA", kA);
      this.kG = new LoggedNumberWrapper(key + "/kG", kG);
    } else {
      this.kS = new StaticNumberWrapper(kS);
      this.kV = new StaticNumberWrapper(kV);
      this.kA = new StaticNumberWrapper(kA);
      this.kG = new StaticNumberWrapper(kG);
    }
  }

  void updateTuningMode() {
    // Update base PID values then feedforward values
    super.updateTuningMode();
    updateTuningMode(kS.get(), kV.get(), kA.get(), kG.get());
  }
}
