/*
 * Copyright (c) 2025-2026 Team Paradise - FRC 1165 (https://github.com/TeamParadise)
 *
 * Use of this source code is governed by the MIT License, which can be found in the LICENSE file at
 * the root directory of this project.
 */

package com.team1165.util.tunables.wrappers.numbers;

import com.team1165.util.tunables.TunableNumber;

/** Interface to wrap a number, mainly used for {@link TunableNumber}. */
public interface NumberWrapper {
  /** Returns the current value. */
  double get();
}
