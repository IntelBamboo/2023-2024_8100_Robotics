package frc.robot.subsystems;

import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.DriveConstants;

public class SwerveSubsystem extends SubsystemBase {

    private final SwerveModule frontLeft = new SwerveModule(DriveConstants.kFrontLeftDriveMotorPort, DriveConstants.kFrontLeftTurningMotorPort, DriveConstants.kFrontLeftDriveEncoderReversed, DriveConstants.kFrontLeftTurningEncoderReversed, 
    DriveConstants.kFrontLeftDriveAbsoluteEncoderPort, 0, DriveConstants.kFrontLeftDriveAbsoluteEncoderReversed);

    private final SwerveModule frontRight = new SwerveModule(DriveConstants.kFrontRightDriveMotorPort, DriveConstants.kFrontRightTurningMotorPort, DriveConstants.kFrontRightDriveEncoderReversed, DriveConstants.kFrontRightTurningEncoderReversed, 
    DriveConstants.kFrontRightDriveAbsoluteEncoderPort, 0, DriveConstants.kFrontRightDriveAbsoluteEncoderReversed);

    private final SwerveModule backLeft = new SwerveModule(DriveConstants.kBackLeftDriveMotorPort, DriveConstants.kBackLeftTurningMotorPort, DriveConstants.kBackLeftDriveEncoderReversed, DriveConstants.kBackLeftTurningEncoderReversed, 
    DriveConstants.kBackLeftDriveAbsoluteEncoderPort, 0, DriveConstants.kBackLeftDriveAbsoluteEncoderReversed);

    private final SwerveModule backRight = new SwerveModule(DriveConstants.kBackRightDriveMotorPort, DriveConstants.kBackRightTurningMotorPort, DriveConstants.kBackRightDriveEncoderReversed, DriveConstants.kBackRightTurningEncoderReversed, 
    DriveConstants.kBackRightDriveAbsoluteEncoderPort, 0, DriveConstants.kBackRightDriveAbsoluteEncoderReversed);


    public void stopModules(){
        frontLeft.stop();
        frontRight.stop();
        backLeft.stop();
        backRight.stop();
    }


    public void setModuleStates(SwerveModuleState[] desiredStates){
        SwerveDriveKinematics.desaturateWheelSpeeds(desiredStates, DriveConstants.kPhysicalMaxSpeedMetersPerSecond);
        frontLeft.setDesiredState(desiredStates[0]);
        frontRight.setDesiredState(desiredStates[1]);
        backLeft.setDesiredState(desiredStates[2]);
        backRight.setDesiredState(desiredStates[3]);

    }

    
    
}
