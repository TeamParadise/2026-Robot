/*
 * Copyright (c) 2025-2026 Team Paradise - FRC 1165 (https://github.com/TeamParadise)
 *
 * Use of this source code is governed by the MIT License, which can be found in the LICENSE file at
 * the root directory of this project.
 */

package com.team1165.util.vendor.ctre;

import com.ctre.phoenix6.BaseStatusSignal;
import com.ctre.phoenix6.CANBus;
import com.ctre.phoenix6.StatusCode;
import com.team1165.util.constants.CANFrequency;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Supplier;
import java.util.stream.Stream;

/** Class containing various utilities to work with {@link BaseStatusSignal} objects. */
public final class PhoenixSignalUtils {
  /** Class that will link together all registered signals on a specific CAN bus. */
  private static final class BusSignals {
    private final CANBus canBus;
    private final ArrayList<BaseStatusSignal> signals = new ArrayList<>();

    /**
     * Constructs a new {@link BusSignals}.
     *
     * @param canBus The {@link CANBus} to link with this instance.
     * @param initialSignals The initial {@link BaseStatusSignal} objects to register with this
     *     instance.
     */
    BusSignals(CANBus canBus, BaseStatusSignal... initialSignals) {
      this.canBus = canBus;
      registerSignals(initialSignals);
    }

    /** Refreshes all signals registered with this instance. */
    void refreshAll() {
      BaseStatusSignal.refreshAll(signals);
    }

    /**
     * Registers the provided signals for synchronized refresh through {@link #refreshAll()}.
     *
     * @param newSignals The {@link BaseStatusSignal} objects to be registered with this instance.
     */
    void registerSignals(BaseStatusSignal... newSignals) {
      // Prevent duplicates
      for (BaseStatusSignal signal : newSignals) {
        if (!signals.contains(signal)) {
          signals.add(signal);
        }
      }

      // Trim to size to decrease memory usage
      this.signals.trimToSize();
    }
  }

  /** Array of all {@link BusSignals} created by registering signals. */
  private static BusSignals[] busSignals = new BusSignals[0];

  /** Private constructor in order to prevent instantiation. */
  private PhoenixSignalUtils() {}

  /** Refreshes all signals registered through this class. */
  public static void refreshAll() {
    for (BusSignals signals : busSignals) {
      signals.refreshAll();
    }
  }

  /**
   * Registers the provided signals for synchronized refresh through {@link #refreshAll()}.
   *
   * @param canBus The {@link CANBus} all the provided signals are located on.
   * @param newSignals The {@link BaseStatusSignal} objects to be registered.
   */
  public static void registerSignals(CANBus canBus, BaseStatusSignal... newSignals) {
    // Go through existing BusSignals and see if any already exist with the provided CAN bus
    for (BusSignals signals : busSignals) {
      if (signals.canBus.equals(canBus)) {
        signals.registerSignals(newSignals);
        return;
      }
    }

    // If none exist with the provided CAN bus, create a new one and add it to the existing array
    BusSignals newBusSignals = new BusSignals(canBus, newSignals);
    busSignals =
        Stream.concat(Arrays.stream(busSignals), Stream.of(newBusSignals))
            .toArray(BusSignals[]::new);
  }

  /**
   * Applies the provided update frequency to the provided signals.
   *
   * @param canBus The {@link CANBus} that the signals are located on.
   * @param frequency The {@link CANFrequency} that the signals should be updated at.
   * @param keepHigherFrequency If true, keep an existing applied frequency if it's higher than the
   *     one trying to be set, otherwise, override it.
   * @param signals The {@link BaseStatusSignal} objects to apply the update frequency to.
   */
  public static void setUpdateFrequency(
      CANBus canBus,
      CANFrequency frequency,
      boolean keepHigherFrequency,
      BaseStatusSignal... signals) {
    double frequencyToSet = frequency.getFrequency(canBus);

    // If set to keep the higher frequency, check each status signal and it's current frequency
    if (keepHigherFrequency) {
      for (BaseStatusSignal signal : signals) {
        if (signal.getAppliedUpdateFrequency() < frequencyToSet) {
          signal.setUpdateFrequency(frequencyToSet);
        }
      }
    } else {
      BaseStatusSignal.setUpdateFrequencyForAll(frequencyToSet, signals);
    }
  }

  /**
   * Applies the provided update frequency to the provided signals.
   *
   * <p>By default, if a signal has an existing signal frequency that is higher than the one trying
   * to be applied, the higher frequency will be kept. To override this, use {@link
   * #setUpdateFrequency(CANBus, CANFrequency, boolean, BaseStatusSignal...)}.
   *
   * @param canBus The {@link CANBus} that the signals are located on.
   * @param frequency The {@link CANFrequency} that the signals should be updated at.
   * @param signals The {@link BaseStatusSignal} objects to apply the update frequency to.
   */
  public static void setUpdateFrequency(
      CANBus canBus, CANFrequency frequency, BaseStatusSignal... signals) {
    setUpdateFrequency(canBus, frequency, true, signals);
  }

  /**
   * Applies the provided update frequency to the provided signals and registers the provided
   * signals for synchronized refresh through {@link #refreshAll()}.
   *
   * @param canBus The {@link CANBus} that the signals are located on.
   * @param frequency The {@link CANFrequency} that the signals should be updated at.
   * @param keepHigherFrequency If true, keep an existing applied frequency if it's higher than the
   *     one trying to be set, otherwise, override it.
   * @param signals The {@link BaseStatusSignal} objects to apply the update frequency to and
   *     register.
   */
  public static void setFrequencyAndRegister(
      CANBus canBus,
      CANFrequency frequency,
      boolean keepHigherFrequency,
      BaseStatusSignal... signals) {
    setUpdateFrequency(canBus, frequency, keepHigherFrequency, signals);
    registerSignals(canBus, signals);
  }

  /**
   * Applies the provided update frequency to the provided signals and registers the provided
   * signals for synchronized refresh through {@link #refreshAll()}.
   *
   * <p>By default, if a signal has an existing signal frequency that is higher than the one trying
   * to be applied, the higher frequency will be kept. To override this, use {@link
   * #setFrequencyAndRegister(CANBus, CANFrequency, boolean, BaseStatusSignal...)}.
   *
   * @param canBus The {@link CANBus} that the signals are located on.
   * @param frequency The {@link CANFrequency} that the signals should be updated at.
   * @param signals The {@link BaseStatusSignal} objects to apply the update frequency to and
   *     register.
   */
  public static void setFrequencyAndRegister(
      CANBus canBus, CANFrequency frequency, BaseStatusSignal... signals) {
    setFrequencyAndRegister(canBus, frequency, true, signals);
  }

  /**
   * Attempts to run the provided method/supplier until no error is produced.
   *
   * @param maxAttempts The maximum number of times to try before giving up.
   * @param method The method to run and check.
   * @return If the method was successful.
   */
  public static boolean tryUntilOk(int maxAttempts, Supplier<StatusCode> method) {
    for (int i = 0; i < maxAttempts; i++) {
      if (method.get().isOK()) return true;
    }
    return false;
  }
}
