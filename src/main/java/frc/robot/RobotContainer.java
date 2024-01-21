

package frc.robot;


import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants.OIConstants;
import frc.robot.commands.SwerveJoystickCmd;
import frc.robot.subsystems.SwerveSubsystem;


public class RobotContainer {
  
  private final SwerveSubsystem swerveSubsystem = new SwerveSubsystem();
  private final Joystick driverJoystick = new Joystick(OI.Constants.kDriverControllerPort);
  
  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {

    swerveSubsystem.setDefaultCommand(new SwerveJoystickCmd(
      swerveSubsystem,
      () -> -driverJoystick.getRawAxis(OIConstants.kDriverYAxis),
      () -> driverJoystick.getRawAxis(OIConstants.kDriverXAxis),
      () -> driverJoystick.getRawAxis(OIConstants.kDriverRotAxis),
      () -> driverJoystick.getRawButton(OIConstants.kDriverFieldOrientedButtonIdx)));

    configureBindings();
  }

  private void configureBindings() {
   
  }


  public Command getAutonomousCommand() {
    
  }
}
