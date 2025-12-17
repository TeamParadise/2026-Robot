/*
 * Copyright (c) 2025 Team Paradise - FRC 1165 (https://github.com/TeamParadise)
 *
 * Use of this source code is governed by the MIT License, which can be found in the LICENSE file at
 * the root directory of this project.
 */

package com.team1165.util.tunables.wrappers.numbers;

import org.littletonrobotics.junction.networktables.LoggedNetworkNumber;

public interface NumberWrapper {
  double get();

  class LoggedNumberWrapper extends LoggedNetworkNumber implements NumberWrapper {
    public LoggedNumberWrapper(String key, double defaultValue) {
      super(key, defaultValue);
    }
  }
}
