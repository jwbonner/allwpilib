// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package edu.wpi.first.math.kinematics;

import edu.wpi.first.math.MathSharedStore;
import edu.wpi.first.math.MathUsageId;
import edu.wpi.first.math.geometry.Twist2d;
import edu.wpi.first.math.proto.Kinematics.ProtobufDifferentialDriveKinematics;
import edu.wpi.first.util.protobuf.Protobuf;
import edu.wpi.first.util.struct.Struct;
import java.nio.ByteBuffer;
import us.hebi.quickbuf.Descriptors.Descriptor;

/**
 * Helper class that converts a chassis velocity (dx and dtheta components) to left and right wheel
 * velocities for a differential drive.
 *
 * <p>Inverse kinematics converts a desired chassis speed into left and right velocity components
 * whereas forward kinematics converts left and right component velocities into a linear and angular
 * chassis speed.
 */
public class DifferentialDriveKinematics
    implements Kinematics<DifferentialDriveWheelSpeeds, DifferentialDriveWheelPositions> {
  public final double trackWidthMeters;

  /**
   * Constructs a differential drive kinematics object.
   *
   * @param trackWidthMeters The track width of the drivetrain. Theoretically, this is the distance
   *     between the left wheels and right wheels. However, the empirical value may be larger than
   *     the physical measured value due to scrubbing effects.
   */
  public DifferentialDriveKinematics(double trackWidthMeters) {
    this.trackWidthMeters = trackWidthMeters;
    MathSharedStore.reportUsage(MathUsageId.kKinematics_DifferentialDrive, 1);
  }

  /**
   * Returns a chassis speed from left and right component velocities using forward kinematics.
   *
   * @param wheelSpeeds The left and right velocities.
   * @return The chassis speed.
   */
  @Override
  public ChassisSpeeds toChassisSpeeds(DifferentialDriveWheelSpeeds wheelSpeeds) {
    return new ChassisSpeeds(
        (wheelSpeeds.leftMetersPerSecond + wheelSpeeds.rightMetersPerSecond) / 2,
        0,
        (wheelSpeeds.rightMetersPerSecond - wheelSpeeds.leftMetersPerSecond) / trackWidthMeters);
  }

  /**
   * Returns left and right component velocities from a chassis speed using inverse kinematics.
   *
   * @param chassisSpeeds The linear and angular (dx and dtheta) components that represent the
   *     chassis' speed.
   * @return The left and right velocities.
   */
  @Override
  public DifferentialDriveWheelSpeeds toWheelSpeeds(ChassisSpeeds chassisSpeeds) {
    return new DifferentialDriveWheelSpeeds(
        chassisSpeeds.vxMetersPerSecond
            - trackWidthMeters / 2 * chassisSpeeds.omegaRadiansPerSecond,
        chassisSpeeds.vxMetersPerSecond
            + trackWidthMeters / 2 * chassisSpeeds.omegaRadiansPerSecond);
  }

  @Override
  public Twist2d toTwist2d(
      DifferentialDriveWheelPositions start, DifferentialDriveWheelPositions end) {
    return toTwist2d(end.leftMeters - start.leftMeters, end.rightMeters - start.rightMeters);
  }

  /**
   * Performs forward kinematics to return the resulting Twist2d from the given left and right side
   * distance deltas. This method is often used for odometry -- determining the robot's position on
   * the field using changes in the distance driven by each wheel on the robot.
   *
   * @param leftDistanceMeters The distance measured by the left side encoder.
   * @param rightDistanceMeters The distance measured by the right side encoder.
   * @return The resulting Twist2d.
   */
  public Twist2d toTwist2d(double leftDistanceMeters, double rightDistanceMeters) {
    return new Twist2d(
        (leftDistanceMeters + rightDistanceMeters) / 2,
        0,
        (rightDistanceMeters - leftDistanceMeters) / trackWidthMeters);
  }

  public static final class AStruct implements Struct<DifferentialDriveKinematics> {
    @Override
    public Class<DifferentialDriveKinematics> getTypeClass() {
      return DifferentialDriveKinematics.class;
    }

    @Override
    public String getTypeString() {
      return "struct:DifferentialDriveKinematics";
    }

    @Override
    public int getSize() {
      return kSizeDouble;
    }

    @Override
    public String getSchema() {
      return "double track_width";
    }

    @Override
    public DifferentialDriveKinematics unpack(ByteBuffer bb) {
      return new DifferentialDriveKinematics(bb.getDouble());
    }

    @Override
    public void pack(ByteBuffer bb, DifferentialDriveKinematics value) {
      bb.putDouble(value.trackWidthMeters);
    }
  }

  public static final AStruct struct = new AStruct();

  public static final class AProto
      implements Protobuf<DifferentialDriveKinematics, ProtobufDifferentialDriveKinematics> {
    @Override
    public Class<DifferentialDriveKinematics> getTypeClass() {
      return DifferentialDriveKinematics.class;
    }

    @Override
    public Descriptor getDescriptor() {
      return ProtobufDifferentialDriveKinematics.getDescriptor();
    }

    @Override
    public ProtobufDifferentialDriveKinematics createMessage() {
      return ProtobufDifferentialDriveKinematics.newInstance();
    }

    @Override
    public DifferentialDriveKinematics unpack(ProtobufDifferentialDriveKinematics msg) {
      return new DifferentialDriveKinematics(msg.getTrackWidth());
    }

    @Override
    public void pack(ProtobufDifferentialDriveKinematics msg, DifferentialDriveKinematics value) {
      msg.setTrackWidth(value.trackWidthMeters);
    }
  }

  public static final AProto proto = new AProto();
}
