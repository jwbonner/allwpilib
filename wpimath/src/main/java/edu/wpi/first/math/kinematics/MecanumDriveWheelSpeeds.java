// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package edu.wpi.first.math.kinematics;

import edu.wpi.first.math.proto.Kinematics.ProtobufMecanumDriveWheelSpeeds;
import edu.wpi.first.util.protobuf.Protobuf;
import edu.wpi.first.util.struct.Struct;
import java.nio.ByteBuffer;
import java.util.stream.DoubleStream;
import us.hebi.quickbuf.Descriptors.Descriptor;

public class MecanumDriveWheelSpeeds {
  /** Speed of the front left wheel. */
  public double frontLeftMetersPerSecond;

  /** Speed of the front right wheel. */
  public double frontRightMetersPerSecond;

  /** Speed of the rear left wheel. */
  public double rearLeftMetersPerSecond;

  /** Speed of the rear right wheel. */
  public double rearRightMetersPerSecond;

  /** Constructs a MecanumDriveWheelSpeeds with zeros for all member fields. */
  public MecanumDriveWheelSpeeds() {}

  /**
   * Constructs a MecanumDriveWheelSpeeds.
   *
   * @param frontLeftMetersPerSecond Speed of the front left wheel.
   * @param frontRightMetersPerSecond Speed of the front right wheel.
   * @param rearLeftMetersPerSecond Speed of the rear left wheel.
   * @param rearRightMetersPerSecond Speed of the rear right wheel.
   */
  public MecanumDriveWheelSpeeds(
      double frontLeftMetersPerSecond,
      double frontRightMetersPerSecond,
      double rearLeftMetersPerSecond,
      double rearRightMetersPerSecond) {
    this.frontLeftMetersPerSecond = frontLeftMetersPerSecond;
    this.frontRightMetersPerSecond = frontRightMetersPerSecond;
    this.rearLeftMetersPerSecond = rearLeftMetersPerSecond;
    this.rearRightMetersPerSecond = rearRightMetersPerSecond;
  }

  /**
   * Renormalizes the wheel speeds if any individual speed is above the specified maximum.
   *
   * <p>Sometimes, after inverse kinematics, the requested speed from one or more wheels may be
   * above the max attainable speed for the driving motor on that wheel. To fix this issue, one can
   * reduce all the wheel speeds to make sure that all requested module speeds are at-or-below the
   * absolute threshold, while maintaining the ratio of speeds between wheels.
   *
   * @param attainableMaxSpeedMetersPerSecond The absolute max speed that a wheel can reach.
   */
  public void desaturate(double attainableMaxSpeedMetersPerSecond) {
    double realMaxSpeed =
        DoubleStream.of(
                frontLeftMetersPerSecond,
                frontRightMetersPerSecond,
                rearLeftMetersPerSecond,
                rearRightMetersPerSecond)
            .max()
            .getAsDouble();

    if (realMaxSpeed > attainableMaxSpeedMetersPerSecond) {
      frontLeftMetersPerSecond =
          frontLeftMetersPerSecond / realMaxSpeed * attainableMaxSpeedMetersPerSecond;
      frontRightMetersPerSecond =
          frontRightMetersPerSecond / realMaxSpeed * attainableMaxSpeedMetersPerSecond;
      rearLeftMetersPerSecond =
          rearLeftMetersPerSecond / realMaxSpeed * attainableMaxSpeedMetersPerSecond;
      rearRightMetersPerSecond =
          rearRightMetersPerSecond / realMaxSpeed * attainableMaxSpeedMetersPerSecond;
    }
  }

  /**
   * Adds two MecanumDriveWheelSpeeds and returns the sum.
   *
   * <p>For example, MecanumDriveWheelSpeeds{1.0, 0.5, 2.0, 1.5} + MecanumDriveWheelSpeeds{2.0, 1.5,
   * 0.5, 1.0} = MecanumDriveWheelSpeeds{3.0, 2.0, 2.5, 2.5}
   *
   * @param other The MecanumDriveWheelSpeeds to add.
   * @return The sum of the MecanumDriveWheelSpeeds.
   */
  public MecanumDriveWheelSpeeds plus(MecanumDriveWheelSpeeds other) {
    return new MecanumDriveWheelSpeeds(
        frontLeftMetersPerSecond + other.frontLeftMetersPerSecond,
        frontRightMetersPerSecond + other.frontRightMetersPerSecond,
        rearLeftMetersPerSecond + other.rearLeftMetersPerSecond,
        rearRightMetersPerSecond + other.rearRightMetersPerSecond);
  }

  /**
   * Subtracts the other MecanumDriveWheelSpeeds from the current MecanumDriveWheelSpeeds and
   * returns the difference.
   *
   * <p>For example, MecanumDriveWheelSpeeds{5.0, 4.0, 6.0, 2.5} - MecanumDriveWheelSpeeds{1.0, 2.0,
   * 3.0, 0.5} = MecanumDriveWheelSpeeds{4.0, 2.0, 3.0, 2.0}
   *
   * @param other The MecanumDriveWheelSpeeds to subtract.
   * @return The difference between the two MecanumDriveWheelSpeeds.
   */
  public MecanumDriveWheelSpeeds minus(MecanumDriveWheelSpeeds other) {
    return new MecanumDriveWheelSpeeds(
        frontLeftMetersPerSecond - other.frontLeftMetersPerSecond,
        frontRightMetersPerSecond - other.frontRightMetersPerSecond,
        rearLeftMetersPerSecond - other.rearLeftMetersPerSecond,
        rearRightMetersPerSecond - other.rearRightMetersPerSecond);
  }

  /**
   * Returns the inverse of the current MecanumDriveWheelSpeeds. This is equivalent to negating all
   * components of the MecanumDriveWheelSpeeds.
   *
   * @return The inverse of the current MecanumDriveWheelSpeeds.
   */
  public MecanumDriveWheelSpeeds unaryMinus() {
    return new MecanumDriveWheelSpeeds(
        -frontLeftMetersPerSecond,
        -frontRightMetersPerSecond,
        -rearLeftMetersPerSecond,
        -rearRightMetersPerSecond);
  }

  /**
   * Multiplies the MecanumDriveWheelSpeeds by a scalar and returns the new MecanumDriveWheelSpeeds.
   *
   * <p>For example, MecanumDriveWheelSpeeds{2.0, 2.5, 3.0, 3.5} * 2 = MecanumDriveWheelSpeeds{4.0,
   * 5.0, 6.0, 7.0}
   *
   * @param scalar The scalar to multiply by.
   * @return The scaled MecanumDriveWheelSpeeds.
   */
  public MecanumDriveWheelSpeeds times(double scalar) {
    return new MecanumDriveWheelSpeeds(
        frontLeftMetersPerSecond * scalar,
        frontRightMetersPerSecond * scalar,
        rearLeftMetersPerSecond * scalar,
        rearRightMetersPerSecond * scalar);
  }

  /**
   * Divides the MecanumDriveWheelSpeeds by a scalar and returns the new MecanumDriveWheelSpeeds.
   *
   * <p>For example, MecanumDriveWheelSpeeds{2.0, 2.5, 1.5, 1.0} / 2 = MecanumDriveWheelSpeeds{1.0,
   * 1.25, 0.75, 0.5}
   *
   * @param scalar The scalar to divide by.
   * @return The scaled MecanumDriveWheelSpeeds.
   */
  public MecanumDriveWheelSpeeds div(double scalar) {
    return new MecanumDriveWheelSpeeds(
        frontLeftMetersPerSecond / scalar,
        frontRightMetersPerSecond / scalar,
        rearLeftMetersPerSecond / scalar,
        rearRightMetersPerSecond / scalar);
  }

  @Override
  public String toString() {
    return String.format(
        "MecanumDriveWheelSpeeds(Front Left: %.2f m/s, Front Right: %.2f m/s, "
            + "Rear Left: %.2f m/s, Rear Right: %.2f m/s)",
        frontLeftMetersPerSecond,
        frontRightMetersPerSecond,
        rearLeftMetersPerSecond,
        rearRightMetersPerSecond);
  }

  public static final class AStruct implements Struct<MecanumDriveWheelSpeeds> {
    @Override
    public Class<MecanumDriveWheelSpeeds> getTypeClass() {
      return MecanumDriveWheelSpeeds.class;
    }

    @Override
    public String getTypeString() {
      return "struct:MecanumDriveWheelSpeeds";
    }

    @Override
    public int getSize() {
      return kSizeDouble * 4;
    }

    @Override
    public String getSchema() {
      return "double front_left;double front_right;double rear_left;double rear_right";
    }

    @Override
    public MecanumDriveWheelSpeeds unpack(ByteBuffer bb) {
      double frontLeft = bb.getDouble();
      double frontRight = bb.getDouble();
      double rearLeft = bb.getDouble();
      double rearRight = bb.getDouble();
      return new MecanumDriveWheelSpeeds(frontLeft, frontRight, rearLeft, rearRight);
    }

    @Override
    public void pack(ByteBuffer bb, MecanumDriveWheelSpeeds value) {
      bb.putDouble(value.frontLeftMetersPerSecond);
      bb.putDouble(value.frontRightMetersPerSecond);
      bb.putDouble(value.rearLeftMetersPerSecond);
      bb.putDouble(value.rearRightMetersPerSecond);
    }
  }

  public static final AStruct struct = new AStruct();

  public static final class AProto
      implements Protobuf<MecanumDriveWheelSpeeds, ProtobufMecanumDriveWheelSpeeds> {
    @Override
    public Class<MecanumDriveWheelSpeeds> getTypeClass() {
      return MecanumDriveWheelSpeeds.class;
    }

    @Override
    public Descriptor getDescriptor() {
      return ProtobufMecanumDriveWheelSpeeds.getDescriptor();
    }

    @Override
    public ProtobufMecanumDriveWheelSpeeds createMessage() {
      return ProtobufMecanumDriveWheelSpeeds.newInstance();
    }

    @Override
    public MecanumDriveWheelSpeeds unpack(ProtobufMecanumDriveWheelSpeeds msg) {
      return new MecanumDriveWheelSpeeds(
          msg.getFrontLeft(), msg.getFrontRight(), msg.getRearLeft(), msg.getRearRight());
    }

    @Override
    public void pack(ProtobufMecanumDriveWheelSpeeds msg, MecanumDriveWheelSpeeds value) {
      msg.setFrontLeft(value.frontLeftMetersPerSecond)
          .setFrontRight(value.frontRightMetersPerSecond)
          .setRearLeft(value.rearLeftMetersPerSecond)
          .setRearRight(value.rearRightMetersPerSecond);
    }
  }

  public static final AProto proto = new AProto();
}
