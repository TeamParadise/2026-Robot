package com.team1165.robot.calculations.shoot;

import com.team1165.robot.calculations.Vector2;
import com.team1165.robot.calculations.Vector3;
import com.team1165.robot.globalconstants.FieldConstants.Hub;
import com.team1165.robot.subsystems.drive.Drive;

public class ShootOnTheMove {
  public double calculate(Drive drive, Vector2 robotSpeed, double turretHeight, double shootAngle) {
    Vector3 turretPosition =
        new Vector3(drive.getPose().getX(), drive.getPose().getY(), turretHeight);
    double turretRotation = drive.getPose().getRotation().getRadians();
    Vector3 hubPosition = new Vector3(Hub.topCenterPoint.getX(), Hub.topCenterPoint.getY(), 6.0);

    double angleToHub = Math.atan2(hubPosition.getY() - turretPosition.getY(), hubPosition.getX() - turretPosition.getX());

    double ballSpeed =
        SpeedCalculation.calculateBallSpeed(
            shootAngle,
            hubPosition.getZ() - turretHeight,
            hubPosition.minus(turretPosition).magnitude());

    return 1.0f;
  }
}
