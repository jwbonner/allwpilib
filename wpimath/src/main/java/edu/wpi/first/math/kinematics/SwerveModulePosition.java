// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package edu.wpi.first.math.kinematics;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.interpolation.Interpolatable;
import edu.wpi.first.math.proto.Kinematics.ProtobufSwerveModulePosition;
import edu.wpi.first.util.protobuf.Protobuf;
import edu.wpi.first.util.struct.Struct;
import java.nio.ByteBuffer;
import java.util.Objects;
import us.hebi.quickbuf.Descriptors.Descriptor;

/** Represents the state of one swerve module. */
public class SwerveModulePosition
    implements Comparable<SwerveModulePosition>, Interpolatable<SwerveModulePosition> {
  /** Distance measured by the wheel of the module. */
  public double distanceMeters;

  /** Angle of the module. */
  public Rotation2d angle = Rotation2d.fromDegrees(0);

  /** Constructs a SwerveModulePosition with zeros for distance and angle. */
  public SwerveModulePosition() {}

  /**
   * Constructs a SwerveModulePosition.
   *
   * @param distanceMeters The distance measured by the wheel of the module.
   * @param angle The angle of the module.
   */
  public SwerveModulePosition(double distanceMeters, Rotation2d angle) {
    this.distanceMeters = distanceMeters;
    this.angle = angle;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj instanceof SwerveModulePosition) {
      SwerveModulePosition other = (SwerveModulePosition) obj;
      return Math.abs(other.distanceMeters - distanceMeters) < 1E-9 && angle.equals(other.angle);
    }
    return false;
  }

  @Override
  public int hashCode() {
    return Objects.hash(distanceMeters, angle);
  }

  /**
   * Compares two swerve module positions. One swerve module is "greater" than the other if its
   * distance is higher than the other.
   *
   * @param other The other swerve module.
   * @return 1 if this is greater, 0 if both are equal, -1 if other is greater.
   */
  @Override
  public int compareTo(SwerveModulePosition other) {
    return Double.compare(this.distanceMeters, other.distanceMeters);
  }

  @Override
  public String toString() {
    return String.format(
        "SwerveModulePosition(Distance: %.2f m, Angle: %s)", distanceMeters, angle);
  }

  /**
   * Returns a copy of this swerve module position.
   *
   * @return A copy.
   */
  public SwerveModulePosition copy() {
    return new SwerveModulePosition(distanceMeters, angle);
  }

  @Override
  public SwerveModulePosition interpolate(SwerveModulePosition endValue, double t) {
    return new SwerveModulePosition(
        MathUtil.interpolate(this.distanceMeters, endValue.distanceMeters, t),
        this.angle.interpolate(endValue.angle, t));
  }

  public static final class AStruct implements Struct<SwerveModulePosition> {
    @Override
    public Class<SwerveModulePosition> getTypeClass() {
      return SwerveModulePosition.class;
    }

    @Override
    public String getTypeString() {
      return "struct:SwerveModulePosition";
    }

    @Override
    public int getSize() {
      return kSizeDouble + Rotation2d.struct.getSize();
    }

    @Override
    public String getSchema() {
      return "double distance;Rotation2d angle";
    }

    @Override
    public Struct<?>[] getNested() {
      return new Struct<?>[] {Rotation2d.struct};
    }

    @Override
    public SwerveModulePosition unpack(ByteBuffer bb) {
      double distance = bb.getDouble();
      Rotation2d angle = Rotation2d.struct.unpack(bb);
      return new SwerveModulePosition(distance, angle);
    }

    @Override
    public void pack(ByteBuffer bb, SwerveModulePosition value) {
      bb.putDouble(value.distanceMeters);
      Rotation2d.struct.pack(bb, value.angle);
    }
  }

  public static final AStruct struct = new AStruct();

  public static final class AProto
      implements Protobuf<SwerveModulePosition, ProtobufSwerveModulePosition> {
    @Override
    public Class<SwerveModulePosition> getTypeClass() {
      return SwerveModulePosition.class;
    }

    @Override
    public Descriptor getDescriptor() {
      return ProtobufSwerveModulePosition.getDescriptor();
    }

    @Override
    public Protobuf<?, ?>[] getNested() {
      return new Protobuf<?, ?>[] {Rotation2d.proto};
    }

    @Override
    public ProtobufSwerveModulePosition createMessage() {
      return ProtobufSwerveModulePosition.newInstance();
    }

    @Override
    public SwerveModulePosition unpack(ProtobufSwerveModulePosition msg) {
      return new SwerveModulePosition(msg.getDistance(), Rotation2d.proto.unpack(msg.getAngle()));
    }

    @Override
    public void pack(ProtobufSwerveModulePosition msg, SwerveModulePosition value) {
      msg.setDistance(value.distanceMeters);
      Rotation2d.proto.pack(msg.getMutableAngle(), value.angle);
    }
  }

  public static final AProto proto = new AProto();
}
