package com.team1165.robot.globalconstants; /*
                                             * Copyright (c) 2025-2026 Team Paradise - FRC 1165 (https://github.com/TeamParadise)
                                             *
                                             * Use of this source code is governed by the MIT License, which can be found in the LICENSE file at
                                             * the root directory of this project.
                                             */

import com.ctre.phoenix6.CANBus;

public class CANConstants {
  public static final class IDs {
    public static final class RIO {}

    public static final class CANivore {
      public static final CANBus bus = new CANBus("canivore");
      public static final int flywheelPrimary = 14;
      public static final int flywheelSecondary = 15;
    }
  }
}
