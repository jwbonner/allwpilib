// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package edu.wpi.first.math.kinematics;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.proto.Kinematics.ProtobufSwerveModuleState;
import edu.wpi.first.util.protobuf.Protobuf;
import edu.wpi.first.util.struct.Struct;
import java.nio.ByteBuffer;
import java.util.Objects;
import us.hebi.quickbuf.Descriptors.Descriptor;

/** Represents the state of one swerve module. */
public class SwerveModuleState implements Comparable<SwerveModuleState> {
  /** Speed of the wheel of the module. */
  public double speedMetersPerSecond;

  /** Angle of the module. */
  public Rotation2d angle = Rotation2d.fromDegrees(0);

  /** Constructs a SwerveModuleState with zeros for speed and angle. */
  public SwerveModuleState() {}

  /**
   * Constructs a SwerveModuleState.
   *
   * @param speedMetersPerSecond The speed of the wheel of the module.
   * @param angle The angle of the module.
   */
  public SwerveModuleState(double speedMetersPerSecond, Rotation2d angle) {
    this.speedMetersPerSecond = speedMetersPerSecond;
    this.angle = angle;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj instanceof SwerveModuleState) {
      SwerveModuleState other = (SwerveModuleState) obj;
      return Math.abs(other.speedMetersPerSecond - speedMetersPerSecond) < 1E-9
          && angle.equals(other.angle);
    }
    return false;
  }

  @Override
  public int hashCode() {
    return Objects.hash(speedMetersPerSecond, angle);
  }

  /**
   * Compares two swerve module states. One swerve module is "greater" than the other if its speed
   * is higher than the other.
   *
   * @param other The other swerve module.
   * @return 1 if this is greater, 0 if both are equal, -1 if other is greater.
   */
  @Override
  public int compareTo(SwerveModuleState other) {
    return Double.compare(this.speedMetersPerSecond, other.speedMetersPerSecond);
  }

  @Override
  public String toString() {
    return String.format(
        "SwerveModuleState(Speed: %.2f m/s, Angle: %s)", speedMetersPerSecond, angle);
  }

  /**
   * Minimize the change in heading the desired swerve module state would require by potentially
   * reversing the direction the wheel spins. If this is used with the PIDController class's
   * continuous input functionality, the furthest a wheel will ever rotate is 90 degrees.
   *
   * @param desiredState The desired state.
   * @param currentAngle The current module angle.
   * @return Optimized swerve module state.
   */
  public static SwerveModuleState optimize(
      SwerveModuleState desiredState, Rotation2d currentAngle) {
    var delta = desiredState.angle.minus(currentAngle);
    if (Math.abs(delta.getDegrees()) > 90.0) {
      return new SwerveModuleState(
          -desiredState.speedMetersPerSecond,
          desiredState.angle.rotateBy(Rotation2d.fromDegrees(180.0)));
    } else {
      return new SwerveModuleState(desiredState.speedMetersPerSecond, desiredState.angle);
    }
  }

  public static final class AStruct implements Struct<SwerveModuleState> {
    @Override
    public Class<SwerveModuleState> getTypeClass() {
      return SwerveModuleState.class;
    }

    @Override
    public String getTypeString() {
      return "struct:SwerveModuleState";
    }

    @Override
    public int getSize() {
      return kSizeDouble + Rotation2d.struct.getSize();
    }

    @Override
    public String getSchema() {
      return "double speed;Rotation2d angle";
    }

    @Override
    public Struct<?>[] getNested() {
      return new Struct<?>[] {Rotation2d.struct};
    }

    @Override
    public SwerveModuleState unpack(ByteBuffer bb) {
      double speed = bb.getDouble();
      Rotation2d angle = Rotation2d.struct.unpack(bb);
      return new SwerveModuleState(speed, angle);
    }

    @Override
    public void pack(ByteBuffer bb, SwerveModuleState value) {
      bb.putDouble(value.speedMetersPerSecond);
      Rotation2d.struct.pack(bb, value.angle);
    }
  }

  public static final AStruct struct = new AStruct();

  public static final class AProto
      implements Protobuf<SwerveModuleState, ProtobufSwerveModuleState> {
    @Override
    public Class<SwerveModuleState> getTypeClass() {
      return SwerveModuleState.class;
    }

    @Override
    public Descriptor getDescriptor() {
      return ProtobufSwerveModuleState.getDescriptor();
    }

    @Override
    public Protobuf<?, ?>[] getNested() {
      return new Protobuf<?, ?>[] {Rotation2d.proto};
    }

    @Override
    public ProtobufSwerveModuleState createMessage() {
      return ProtobufSwerveModuleState.newInstance();
    }

    @Override
    public SwerveModuleState unpack(ProtobufSwerveModuleState msg) {
      return new SwerveModuleState(msg.getSpeed(), Rotation2d.proto.unpack(msg.getAngle()));
    }

    @Override
    public void pack(ProtobufSwerveModuleState msg, SwerveModuleState value) {
      msg.setSpeed(value.speedMetersPerSecond);
      Rotation2d.proto.pack(msg.getMutableAngle(), value.angle);
    }
  }

  public static final AProto proto = new AProto();
}
