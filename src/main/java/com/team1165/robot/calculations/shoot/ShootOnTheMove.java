package com.team1165.robot.calculations.shoot;

import com.team1165.robot.calculations.Vector2;
import com.team1165.robot.calculations.Vector3;
import com.team1165.robot.globalconstants.FieldConstants.Hub;
import com.team1165.robot.subsystems.drive.Drive;
import edu.wpi.first.math.kinematics.ChassisSpeeds;


public class ShootOnTheMove {

  /** Calculates the vector at which a ball should be shot in order to make it into the hub and returns the speed.
   * @param drive The {@link Drive} for the robot. Used to calculate the speed and position of the bot.
   * @return The {@link Vector2} in which the ball should be shot.
   * */
  public Vector2 calculate(Drive drive) {
    Vector2 robotPosition = new Vector2(drive.getPose().getX(), drive.getPose().getY());
    double turretRotation = drive.getPose().getRotation().getRadians();

    Vector3 hubPosition = new Vector3(Hub.topCenterPoint.getX(), Hub.topCenterPoint.getY(), 6.0);
    Vector2 hubPosition2D = new Vector2(hubPosition);

    Vector2 robotSpeed = new Vector2(drive.getSpeeds().vxMetersPerSecond * 3.28084, drive.getSpeeds().vyMetersPerSecond * 3.28084);
    Vector2 vecToHub = robotPosition.minus(hubPosition2D);
    Vector2 shootVector = vecToHub.minus(robotSpeed);

    return shootVector;
  }
}
