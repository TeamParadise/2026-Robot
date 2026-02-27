package com.team1165.robot.calculations.shoot;

import com.team1165.robot.globalconstants.FieldConstants.Hub;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.geometry.Translation3d;
import com.team1165.robot.subsystems.drive.Drive;


import com.team1165.robot.calculations.shoot.SpeedCalculation;
import com.team1165.robot.calculations.Vector2;
import com.team1165.robot.calculations.Vector3;
import com.team1165.robot.calculations.PhysicsConstants;


public class ShootOnTheMove {
  public double calculate(Drive drive, Vector2 robotSpeed, double turretHeight)
  {
    Vector3 turretPosition = new Vector3(drive.getPose().getX(), drive.getPose().getY(), turretHeight);
    double turretRotation = drive.getPose().getRotation().getRadians();

    Vector3 hubPosition = new Vector3(Hub.topCenterPoint.getX(), Hub.topCenterPoint.getY(), 6.0);

    return 1.0f;
  }
}
