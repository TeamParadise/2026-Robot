/*
 * Copyright (c) 2025-2026 Team Paradise - FRC 1165 (https://github.com/TeamParadise)
 *
 * Use of this source code is governed by the MIT License, which can be found in the LICENSE file at
 * the root directory of this project.
 */

package com.team1165.util.tunables.wrappers.numbers;

import org.littletonrobotics.junction.networktables.LoggedNetworkNumber;

/**
 * Class to wrap a logged NetworkTables double value.
 *
 * @see LoggedNetworkNumber
 */
public class LoggedNumberWrapper extends LoggedNetworkNumber implements NumberWrapper {
  /**
   * Creates a new {@link LoggedNumberWrapper}
   *
   * @param key The key for the number, published to the root table of NT or "/DashboardInputs/{key}" when logged.
   * @param defaultValue The default value if no value in NT is found.
   * @see LoggedNetworkNumber#LoggedNetworkNumber(String, double)
   */
  public LoggedNumberWrapper(String key, double defaultValue) {
    super(key, defaultValue);
  }
}
