package frc.robot.subsystems;

import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkLowLevel.MotorType;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.RobotController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import frc.robot.Constants.ModuleConstants;
import frc.robot.Constants.DriveConstants;

public class SwerveModule {
    
    private final CANSparkMax driveMotor;
    private final CANSparkMax turningMotor;

    private final RelativeEncoder driveEncoder;
    private final RelativeEncoder turningEncoder;

    private final PIDController turningPidController;

    private final AnalogInput absoluteEncoder;
    private final boolean absoluteEncoderReversed;
    private final double absoluteEncoderOffsetRad;


    public SwerveModule(int driveMotorID, int turningMotorId, boolean driveMotorReversed, boolean turningMotorReversed, 
        int absoluteEncoderId, double absoluteEncoderOffset, boolean absoluteEncoderReversed){
            
            this.absoluteEncoderOffsetRad = absoluteEncoderOffset;
            this.absoluteEncoderReversed = absoluteEncoderReversed;

            absoluteEncoder = new AnalogInput(absoluteEncoderId); 

            driveMotor = new CANSparkMax(driveMotorID, MotorType.kBrushless);
            turningMotor = new CANSparkMax(driveMotorID, MotorType.kBrushless);

            driveMotor.setInverted(driveMotorReversed);
            turningMotor.setInverted(turningMotorReversed);

            driveEncoder = driveMotor.getEncoder();
            turningEncoder = turningMotor.getEncoder();

            driveEncoder.setPositionConversionFactor(ModuleConstants.kDriveEncoderRot2Meter);
            driveEncoder.setPositionConversionFactor(ModuleConstants.kDriveEncoderRPM2MeterPerSec);
            turningEncoder.setPositionConversionFactor(ModuleConstants.kTurningEncoderRot2Rad);
            turningEncoder.setPositionConversionFactor(ModuleConstants.kTurningEncoderRPM2RadPerSec);

            turningPidController = new PIDController(ModuleConstants.kPTurning, 0, 0);
            turningPidController.enableContinuousInput(-Math.PI, Math.PI);  
            

            resetEncoders();
        }
    
        public double getDrivePosition(){
            return this.driveEncoder.getPosition();
        }

        public double getTurningPosition(){
            return this.turningEncoder.getPosition();
        }

        public double getDriveVelocity(){
            return this.driveEncoder.getVelocity();
        }

        public double getTurningVelocity(){
            return this.turningEncoder.getVelocity();
        }

        public double getAbsoluteEncoderRad(){
            double angle = absoluteEncoder.getVoltage()/ RobotController.getVoltage5V();
            angle *= 2.0 * Math.PI; //In radians
            angle -= absoluteEncoderOffsetRad;
            return angle * (absoluteEncoderReversed ? -1.0 : 1.0);

        }

        public void resetEncoders(){
            driveEncoder.setPosition(0);
            turningEncoder.setPosition(getAbsoluteEncoderRad());
        }

        /**
         * Returns in the format compatible with the WPILib Library
         * @return
         */

        public SwerveModuleState getState(){
            return new SwerveModuleState(getDriveVelocity(), new Rotation2d(getTurningPosition()));
        }

        public void setDesiredState(SwerveModuleState state){
            if(Math.abs(state.speedMetersPerSecond)< 0.001){
                stop();
                return;
            }
            state = SwerveModuleState.optimize(state, getState().angle);
            driveMotor.set(state.speedMetersPerSecond / DriveConstants.kPhysicalMaxSpeedMetersPerSecond);
            turningMotor.set(turningPidController.calculate(getTurningPosition(), state.angle.getRadians()));
            SmartDashboard.putString("Swerve[" + absoluteEncoder.getChannel() + "] state ", state.toString());
        }

        public void stop(){
            driveMotor.set(0);
            turningMotor.set(0);
        }
}
