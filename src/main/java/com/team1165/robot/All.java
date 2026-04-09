// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package com.team1165.robot;

import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.DutyCycleOut;
import com.ctre.phoenix6.controls.Follower;
import com.ctre.phoenix6.controls.VelocityDutyCycle;
import com.ctre.phoenix6.controls.VelocityTorqueCurrentFOC;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.MotorAlignmentValue;
import com.revrobotics.PersistMode;
import com.revrobotics.ResetMode;
import com.revrobotics.spark.SparkBase.ControlType;
import com.revrobotics.spark.SparkClosedLoopController;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.team1165.robot.globalconstants.IDConstants;
import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.RepeatCommand;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class All extends SubsystemBase {
  private final SparkMax spindexerMotor = new SparkMax(12, MotorType.kBrushless);
  private final SparkMax transferMotor = new SparkMax(5, MotorType.kBrushless);
  private final SparkMax intakeMotor = new SparkMax(1, MotorType.kBrushless);
  private final SparkMax mainPivot = new SparkMax(2, MotorType.kBrushless);
  private final SparkMax secondaryPivot = new SparkMax(15, MotorType.kBrushless);
  private final SparkMax hood = new SparkMax(20, MotorType.kBrushless);
  private final SparkClosedLoopController controller = hood.getClosedLoopController();
  private final SparkClosedLoopController transferController =
      transferMotor.getClosedLoopController();
  private final TalonFX main = new TalonFX(13, IDConstants.CANivore.bus);
  private final TalonFX follow = new TalonFX(14, IDConstants.CANivore.bus);

  private final DutyCycleOut dutyCycle = new DutyCycleOut(0).withUpdateFreqHz(0);
  private final VelocityDutyCycle velocityDutyCycle = new VelocityDutyCycle(0).withUpdateFreqHz(0);
  private final VelocityTorqueCurrentFOC velocityTorqueCurrentFOC =
      new VelocityTorqueCurrentFOC(0).withUpdateFreqHz(0);
  private final Follower follower = new Follower(13, MotorAlignmentValue.Opposed);

  private double testRPM = 4000;
  private double testAngle = 0.5;

  /** Creates a new All. */
  public All() {
    var config = new TalonFXConfiguration();

    config.Slot0.kP = 999999.0;
    config.Feedback.SensorToMechanismRatio = (1.0 / 1.5);
    config.TorqueCurrent.PeakForwardTorqueCurrent = 40.0;
    config.TorqueCurrent.PeakReverseTorqueCurrent = 0.0;
    config.MotorOutput.PeakForwardDutyCycle = 1.0;
    config.MotorOutput.PeakReverseDutyCycle = 0.0;

    main.getConfigurator().apply(config);
    follow.getConfigurator().apply(config);
    follow.setControl(follower);
    secondaryPivot.configure(
        new SparkMaxConfig().follow(2, true),
        ResetMode.kNoResetSafeParameters,
        PersistMode.kNoPersistParameters);

    SmartDashboard.putNumber("ShooterRPM", testRPM);
    SmartDashboard.putNumber("ShooterAngle", testAngle);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    testRPM = SmartDashboard.getNumber("ShooterRPM", testRPM);
    testAngle = SmartDashboard.getNumber("ShooterAngle", testAngle);
  }

  public Command runTransfer() {
    return Commands.run(
        () -> {
          transferController.setSetpoint(-5200, ControlType.kVelocity);
          controller.setSetpoint(testAngle, ControlType.kPosition);
          setShooterSpeed(testRPM);
        });
  }

  public Command runIntake() {
    return Commands.run(
        () -> {
          intakeMotor.set(-1);
          mainPivot.set(-0.1);
        });
  }

  public Command stop() {
    return Commands.run(
        () -> {
          intakeMotor.set(0);
          spindexerMotor.set(0);
          transferMotor.set(0);
          main.setControl(dutyCycle.withOutput(0));
          controller.setSetpoint(0, ControlType.kPosition);
          mainPivot.set(-0.0);
        },
        this);
  }

  public Command reverse() {
    return Commands.run(
        () -> {
          spindexerMotor.set(-0.8);
          transferMotor.set(0.4);
          main.setControl(dutyCycle.withOutput(0.2));
        });
  }

  public Command runShooter() {
    return Commands.run(
        () -> {
          controller.setSetpoint(testAngle, ControlType.kPosition);
          setShooterSpeed(testRPM);
        });
  }

  public Command runAll() {
    return Commands.run(
        () -> {
          spindexerMotor.set(0.8);
          transferController.setSetpoint(-5200, ControlType.kVelocity);
          intakeMotor.set(-1);
          mainPivot.set(-0.1);
          controller.setSetpoint(testAngle, ControlType.kPosition);
          setShooterSpeed(testRPM);
        });
  }

  public Command spindexerForward() {
    return Commands.run(
        () -> {
          spindexerMotor.set(1);
        });
  }

  public Command spindexerReverse() {
    return Commands.run(
        () -> {
          spindexerMotor.set(-1.0);
        });
  }

  public Command runAgitate() {
    return new RepeatCommand(
        Commands.run(() -> mainPivot.set(0.3))
            .withTimeout(0.2)
            .andThen(Commands.run(() -> mainPivot.set(0)).withTimeout(0.2))
            .andThen(Commands.run(() -> mainPivot.set(-0.25))));
  }

  public Command kickIntakeOut() {
    return Commands.run(
        () -> {
          mainPivot.set(-0.25);
        });
  }

  public Command pullIntakeIn() {
    return Commands.run(
        () -> {
          mainPivot.set(0.30);
        });
  }

  public Command stopIntake() {
    return Commands.runOnce(
        () -> {
          mainPivot.set(0);
        });
  }

  private void setShooterSpeed(double shooterRPM) {
    double currentVelocity;
    SmartDashboard.putNumber(
        "CurrentShooterRPM", currentVelocity = main.getVelocity().getValueAsDouble() * 60);

    if (MathUtil.isNear(shooterRPM, currentVelocity, shooterRPM * 0.05)) {
      main.setControl(velocityTorqueCurrentFOC.withVelocity(shooterRPM / 60));
    } else {
      main.setControl(velocityDutyCycle.withVelocity(shooterRPM / 60));
    }
  }
}
