package frc.robot.subsystems;

import edu.wpi.first.wpilibj.AnalogInput;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkLowLevel.MotorType;

import edu.wpi.first.math.controller.PIDController;
import frc.robot.Constants.ModuleConstants;

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


        }
}
