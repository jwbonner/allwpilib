// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package edu.wpi.first.math.kinematics;

import edu.wpi.first.math.proto.Kinematics.ProtobufDifferentialDriveWheelSpeeds;
import edu.wpi.first.util.protobuf.Protobuf;
import edu.wpi.first.util.struct.Struct;
import java.nio.ByteBuffer;
import us.hebi.quickbuf.Descriptors.Descriptor;

/** Represents the wheel speeds for a differential drive drivetrain. */
public class DifferentialDriveWheelSpeeds {
  /** Speed of the left side of the robot. */
  public double leftMetersPerSecond;

  /** Speed of the right side of the robot. */
  public double rightMetersPerSecond;

  /** Constructs a DifferentialDriveWheelSpeeds with zeros for left and right speeds. */
  public DifferentialDriveWheelSpeeds() {}

  /**
   * Constructs a DifferentialDriveWheelSpeeds.
   *
   * @param leftMetersPerSecond The left speed.
   * @param rightMetersPerSecond The right speed.
   */
  public DifferentialDriveWheelSpeeds(double leftMetersPerSecond, double rightMetersPerSecond) {
    this.leftMetersPerSecond = leftMetersPerSecond;
    this.rightMetersPerSecond = rightMetersPerSecond;
  }

  /**
   * Renormalizes the wheel speeds if any either side is above the specified maximum.
   *
   * <p>Sometimes, after inverse kinematics, the requested speed from one or more wheels may be
   * above the max attainable speed for the driving motor on that wheel. To fix this issue, one can
   * reduce all the wheel speeds to make sure that all requested module speeds are at-or-below the
   * absolute threshold, while maintaining the ratio of speeds between wheels.
   *
   * @param attainableMaxSpeedMetersPerSecond The absolute max speed that a wheel can reach.
   */
  public void desaturate(double attainableMaxSpeedMetersPerSecond) {
    double realMaxSpeed = Math.max(Math.abs(leftMetersPerSecond), Math.abs(rightMetersPerSecond));

    if (realMaxSpeed > attainableMaxSpeedMetersPerSecond) {
      leftMetersPerSecond = leftMetersPerSecond / realMaxSpeed * attainableMaxSpeedMetersPerSecond;
      rightMetersPerSecond =
          rightMetersPerSecond / realMaxSpeed * attainableMaxSpeedMetersPerSecond;
    }
  }

  /**
   * Adds two DifferentialDriveWheelSpeeds and returns the sum.
   *
   * <p>For example, DifferentialDriveWheelSpeeds{1.0, 0.5} + DifferentialDriveWheelSpeeds{2.0, 1.5}
   * = DifferentialDriveWheelSpeeds{3.0, 2.0}
   *
   * @param other The DifferentialDriveWheelSpeeds to add.
   * @return The sum of the DifferentialDriveWheelSpeeds.
   */
  public DifferentialDriveWheelSpeeds plus(DifferentialDriveWheelSpeeds other) {
    return new DifferentialDriveWheelSpeeds(
        leftMetersPerSecond + other.leftMetersPerSecond,
        rightMetersPerSecond + other.rightMetersPerSecond);
  }

  /**
   * Subtracts the other DifferentialDriveWheelSpeeds from the current DifferentialDriveWheelSpeeds
   * and returns the difference.
   *
   * <p>For example, DifferentialDriveWheelSpeeds{5.0, 4.0} - DifferentialDriveWheelSpeeds{1.0, 2.0}
   * = DifferentialDriveWheelSpeeds{4.0, 2.0}
   *
   * @param other The DifferentialDriveWheelSpeeds to subtract.
   * @return The difference between the two DifferentialDriveWheelSpeeds.
   */
  public DifferentialDriveWheelSpeeds minus(DifferentialDriveWheelSpeeds other) {
    return new DifferentialDriveWheelSpeeds(
        leftMetersPerSecond - other.leftMetersPerSecond,
        rightMetersPerSecond - other.rightMetersPerSecond);
  }

  /**
   * Returns the inverse of the current DifferentialDriveWheelSpeeds. This is equivalent to negating
   * all components of the DifferentialDriveWheelSpeeds.
   *
   * @return The inverse of the current DifferentialDriveWheelSpeeds.
   */
  public DifferentialDriveWheelSpeeds unaryMinus() {
    return new DifferentialDriveWheelSpeeds(-leftMetersPerSecond, -rightMetersPerSecond);
  }

  /**
   * Multiplies the DifferentialDriveWheelSpeeds by a scalar and returns the new
   * DifferentialDriveWheelSpeeds.
   *
   * <p>For example, DifferentialDriveWheelSpeeds{2.0, 2.5} * 2 = DifferentialDriveWheelSpeeds{4.0,
   * 5.0}
   *
   * @param scalar The scalar to multiply by.
   * @return The scaled DifferentialDriveWheelSpeeds.
   */
  public DifferentialDriveWheelSpeeds times(double scalar) {
    return new DifferentialDriveWheelSpeeds(
        leftMetersPerSecond * scalar, rightMetersPerSecond * scalar);
  }

  /**
   * Divides the DifferentialDriveWheelSpeeds by a scalar and returns the new
   * DifferentialDriveWheelSpeeds.
   *
   * <p>For example, DifferentialDriveWheelSpeeds{2.0, 2.5} / 2 = DifferentialDriveWheelSpeeds{1.0,
   * 1.25}
   *
   * @param scalar The scalar to divide by.
   * @return The scaled DifferentialDriveWheelSpeeds.
   */
  public DifferentialDriveWheelSpeeds div(double scalar) {
    return new DifferentialDriveWheelSpeeds(
        leftMetersPerSecond / scalar, rightMetersPerSecond / scalar);
  }

  @Override
  public String toString() {
    return String.format(
        "DifferentialDriveWheelSpeeds(Left: %.2f m/s, Right: %.2f m/s)",
        leftMetersPerSecond, rightMetersPerSecond);
  }

  public static final class AStruct implements Struct<DifferentialDriveWheelSpeeds> {
    @Override
    public Class<DifferentialDriveWheelSpeeds> getTypeClass() {
      return DifferentialDriveWheelSpeeds.class;
    }

    @Override
    public String getTypeString() {
      return "struct:DifferentialDriveWheelSpeeds";
    }

    @Override
    public int getSize() {
      return kSizeDouble * 2;
    }

    @Override
    public String getSchema() {
      return "double left;double right";
    }

    @Override
    public DifferentialDriveWheelSpeeds unpack(ByteBuffer bb) {
      double left = bb.getDouble();
      double right = bb.getDouble();
      return new DifferentialDriveWheelSpeeds(left, right);
    }

    @Override
    public void pack(ByteBuffer bb, DifferentialDriveWheelSpeeds value) {
      bb.putDouble(value.leftMetersPerSecond);
      bb.putDouble(value.rightMetersPerSecond);
    }
  }

  public static final AStruct struct = new AStruct();

  public static final class AProto
      implements Protobuf<DifferentialDriveWheelSpeeds, ProtobufDifferentialDriveWheelSpeeds> {
    @Override
    public Class<DifferentialDriveWheelSpeeds> getTypeClass() {
      return DifferentialDriveWheelSpeeds.class;
    }

    @Override
    public Descriptor getDescriptor() {
      return ProtobufDifferentialDriveWheelSpeeds.getDescriptor();
    }

    @Override
    public ProtobufDifferentialDriveWheelSpeeds createMessage() {
      return ProtobufDifferentialDriveWheelSpeeds.newInstance();
    }

    @Override
    public DifferentialDriveWheelSpeeds unpack(ProtobufDifferentialDriveWheelSpeeds msg) {
      return new DifferentialDriveWheelSpeeds(msg.getLeft(), msg.getRight());
    }

    @Override
    public void pack(ProtobufDifferentialDriveWheelSpeeds msg, DifferentialDriveWheelSpeeds value) {
      msg.setLeft(value.leftMetersPerSecond).setRight(value.rightMetersPerSecond);
    }
  }

  public static final AProto proto = new AProto();
}
