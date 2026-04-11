/*
 * Copyright (c) 2026 Team Paradise - FRC 1165 (https://github.com/TeamParadise)
 *
 * Use of this source code is governed by the MIT License, which can be found in the LICENSE file at
 * the root directory of this project.
 */

package com.team1165.robot.commands;

import com.team1165.robot.subsystems.drive.Drive;
import com.team1165.robot.subsystems.intake.GroundIntake;
import com.team1165.robot.subsystems.intake.GroundIntakeState;
import com.team1165.robot.subsystems.shooter.ShooterManager;
import com.team1165.robot.subsystems.shooter.ShooterState;
import com.team1165.robot.subsystems.spindexer.Spindexer;
import com.team1165.robot.subsystems.spindexer.SpindexerState;
import com.team1165.robot.subsystems.transfer.Transfer;
import com.team1165.robot.subsystems.transfer.TransferState;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.WaitCommand;

public class AutoCommands {
  public static Command depot(
      Drive drive,
      GroundIntake intake,
      Spindexer spindexer,
      Transfer transfer,
      ShooterManager shooter) {
    return Commands.none();
  }
}
