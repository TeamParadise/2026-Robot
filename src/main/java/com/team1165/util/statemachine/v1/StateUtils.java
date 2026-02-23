/*
 * Copyright (c) 2025-2026 Team Paradise - FRC 1165 (https://github.com/TeamParadise)
 *
 * Use of this source code is governed by the MIT License, which can be found in the LICENSE file at
 * the root directory of this project.
 */

package com.team1165.util.statemachine.v1;

import com.team1165.util.tunables.TunableNumber;
import java.util.EnumMap;

/** Utility class for creating tunable mappings from {@link State} enums. */
public final class StateUtils {
  /** Private constructor to prevent instantiation. */
  private StateUtils() {}

  /**
   * Creates an {@link EnumMap} that maps each {@link State} enum constant (that has a value
   * present) to a {@link TunableNumber}. The tunable number is initialized with the default value
   * from the state's {@link State#get()} method.
   *
   * @param key The base key to use for the tunable numbers in the dashboard. Each state's name will
   *     be appended as a sub-key.
   * @param stateEnum The {@link Class} of the state enum to create the map for.
   * @param <S> The state enum type, which must implement {@link State}.
   * @return An {@link EnumMap} mapping each state with a present value to a {@link TunableNumber}.
   */
  public static <S extends Enum<S> & State> EnumMap<S, TunableNumber> createTunableNumberMap(
      String key, Class<S> stateEnum) {
    EnumMap<S, TunableNumber> map = new EnumMap<>(stateEnum);
    for (S state : stateEnum.getEnumConstants()) {
      var stateValue = state.get();
      if (stateValue.isPresent()) {
        map.put(state, new TunableNumber(key + "/" + state.name(), stateValue.getAsDouble()));
      }
    }
    return map;
  }

  /**
   * Creates an {@link EnumMap} that maps each provided {@link State} enum constant (that has a
   * value present) to a {@link TunableNumber}. The tunable number is initialized with the default
   * value from the state's {@link State#get()} method.
   *
   * @param key The base key to use for the tunable numbers in the dashboard. Each state's name will
   *     be appended as a sub-key.
   * @param states The state enum constants to create the map for.
   * @param <S> The state enum type, which must implement {@link State}.
   * @return An {@link EnumMap} mapping each provided state with a present value to a {@link
   *     TunableNumber}.
   * @throws IllegalArgumentException If no states are provided.
   */
  @SafeVarargs
  public static <S extends Enum<S> & State> EnumMap<S, TunableNumber> createTunableNumberMap(
      String key, S... states) {
    if (states.length == 0) {
      throw new IllegalArgumentException("Must provide at least one state to create a map.");
    }
    EnumMap<S, TunableNumber> map = new EnumMap<>(states[0].getDeclaringClass());
    for (S state : states) {
      var stateValue = state.get();
      if (stateValue.isPresent()) {
        map.put(state, new TunableNumber(key + "/" + state.name(), stateValue.getAsDouble()));
      }
    }
    return map;
  }
}
