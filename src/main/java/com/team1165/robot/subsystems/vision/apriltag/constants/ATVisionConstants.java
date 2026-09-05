/*
 * Copyright (c) 2025-2026 Team Paradise - FRC 1165 (https://github.com/TeamParadise)
 *
 * Use of this source code is governed by the MIT License, which can be found in the LICENSE file at
 * the root directory of this project.
 */

package com.team1165.robot.subsystems.vision.apriltag.constants;

import static edu.wpi.first.units.Units.Degrees;

import edu.wpi.first.apriltag.AprilTagFieldLayout;
import edu.wpi.first.apriltag.AprilTagFields;
import edu.wpi.first.math.geometry.Rotation3d;
import edu.wpi.first.math.geometry.Transform3d;

public class ATVisionConstants {
  // AprilTag layout
  public static AprilTagFieldLayout aprilTagLayout =
      AprilTagFieldLayout.loadField(AprilTagFields.kDefaultField);

  public static final class Cameras {
    public static final class CenterCamera {
      public static final String name = "Center";
      public static final Transform3d robotToCamera =
          new Transform3d(
              0.0665105604,
              -0.23495,
              0.512347083,
              new Rotation3d(Degrees.zero(), Degrees.of(-20.0), Degrees.of(0.0)));
    }

    public static final class RightCamera {
      public static final String name = "Right";
      public static final Transform3d robotToCamera =
          new Transform3d(
              0.0308866794,
              -0.2831329872,
              0.5135683404,
              new Rotation3d(Degrees.zero(), Degrees.of(-30.0), Degrees.of(-72.5)));
    }
  }

  // Field border margin for rejection
  public static double fieldBorderMargin = 0.5;

  // Basic filtering thresholds
  public static double maxAmbiguity = 0.25;
  public static double maxZError = 0.75;

  // Standard deviation baselines, for 1 meter distance and 1 tag
  // (Adjusted automatically based on distance and # of tags)
  public static double linearStdDevBaseline = 0.02; // Meters
  public static double angularStdDevBaseline = 0.05; // Radians

  public static double linearStdDevSingleTagBaseline = 0.02;
}
