/*
 * Copyright (c) 2026 Team Paradise - FRC 1165 (https://github.com/TeamParadise)
 *
 * Use of this source code is governed by the MIT License, which can be found in the LICENSE file at
 * the root directory of this project.
 */

package com.team1165.robot;

import com.ctre.phoenix6.configs.Slot0Configs;
import com.team1165.robot.subsystems.drive.Drive;
import com.team1165.robot.subsystems.drive.constants.DriveConstants;
import com.team1165.robot.subsystems.drive.io.DriveIO;
import com.team1165.robot.subsystems.drive.io.DriveIOMapleSim;
import com.team1165.robot.subsystems.drive.io.DriveIOReal;
import com.team1165.robot.subsystems.groundintake.GroundIntake;
import com.team1165.robot.subsystems.groundintake.GroundIntakeConstants;
import com.team1165.robot.subsystems.groundintake.GroundIntakeState;
import com.team1165.robot.subsystems.groundintake.io.PivotIO;
import com.team1165.robot.subsystems.groundintake.io.PivotIOSpark;
import com.team1165.util.io.roller.RollerIO;
import com.team1165.util.io.roller.RollerIOSpark;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;

/** RobotContainer that wires up both drive and ground intake subsystems. */
public class RobotContainer {

    private final Drive drive;
    private final GroundIntake groundIntake;
    private final CommandXboxController driverController =
        new CommandXboxController(0);

    /** Creates subsystems and IO implementations based on current runtime mode. */
    public RobotContainer() {
        switch (Constants.currentMode) {
            case REAL -> {
                drive = new Drive(
                    new DriveIOReal(
                        DriveConstants.drivetrainConstants,
                        DriveConstants.getModuleConstants()
                    )
                );
                groundIntake = new GroundIntake(
                    new RollerIOSpark(GroundIntakeConstants.rollerMotorConfig),
                    new PivotIOSpark(GroundIntakeConstants.pivotMotorConfig),
                    GroundIntakeConstants.pivotMotorPIDConfig
                );
            }
            case SIM -> {
                drive = new Drive(
                    new DriveIOMapleSim(
                        DriveConstants.drivetrainConstants,
                        DriveConstants.simConfig,
                        DriveConstants.getModuleConstants()
                    )
                );
                groundIntake = new GroundIntake(
                    new RollerIO() {},
                    new PivotIO() {},
                    new Slot0Configs() {}
                );
            }
            case REPLAY -> {
                drive = new Drive(new DriveIO() {});
                groundIntake = new GroundIntake(
                    new RollerIO() {},
                    new PivotIO() {},
                    new Slot0Configs() {}
                );
            }
            default -> {
                drive = new Drive(new DriveIO() {});
                groundIntake = new GroundIntake(
                    new RollerIO() {},
                    new PivotIO() {},
                    new Slot0Configs() {}
                );
            }
        }

        // Configure controller bindings (keeps the same behavior as before).
        configureButtonBindings();
    }

    /** Configure driver button bindings for ground intake. */
    private void configureButtonBindings() {
        driverController
            .a()
            .onTrue(
                groundIntake
                    .overrideState(GroundIntakeState.IDLE)
                    .withName("Controller - A - Idle State")
            );
        driverController
            .b()
            .onTrue(
                groundIntake
                    .overrideState(GroundIntakeState.DEPLOY)
                    .withName("Controller - B - Deploy State")
            );
    }

    /** Returns the autonomous command to run. */
    public Command getAutonomousCommand() {
        return Commands.none();
    }
}
