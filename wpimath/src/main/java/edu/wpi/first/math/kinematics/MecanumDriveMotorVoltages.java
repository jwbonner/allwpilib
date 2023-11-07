// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package edu.wpi.first.math.kinematics;

import edu.wpi.first.math.proto.Kinematics.ProtobufMecanumDriveMotorVoltages;
import edu.wpi.first.util.protobuf.Protobuf;
import edu.wpi.first.util.struct.Struct;
import java.nio.ByteBuffer;
import us.hebi.quickbuf.Descriptors.Descriptor;

/** Represents the motor voltages for a mecanum drive drivetrain. */
public class MecanumDriveMotorVoltages {
  /** Voltage of the front left motor. */
  public double frontLeftVoltage;

  /** Voltage of the front right motor. */
  public double frontRightVoltage;

  /** Voltage of the rear left motor. */
  public double rearLeftVoltage;

  /** Voltage of the rear right motor. */
  public double rearRightVoltage;

  /** Constructs a MecanumDriveMotorVoltages with zeros for all member fields. */
  public MecanumDriveMotorVoltages() {}

  /**
   * Constructs a MecanumDriveMotorVoltages.
   *
   * @param frontLeftVoltage Voltage of the front left motor.
   * @param frontRightVoltage Voltage of the front right motor.
   * @param rearLeftVoltage Voltage of the rear left motor.
   * @param rearRightVoltage Voltage of the rear right motor.
   */
  public MecanumDriveMotorVoltages(
      double frontLeftVoltage,
      double frontRightVoltage,
      double rearLeftVoltage,
      double rearRightVoltage) {
    this.frontLeftVoltage = frontLeftVoltage;
    this.frontRightVoltage = frontRightVoltage;
    this.rearLeftVoltage = rearLeftVoltage;
    this.rearRightVoltage = rearRightVoltage;
  }

  @Override
  public String toString() {
    return String.format(
        "MecanumDriveMotorVoltages(Front Left: %.2f V, Front Right: %.2f V, "
            + "Rear Left: %.2f V, Rear Right: %.2f V)",
        frontLeftVoltage, frontRightVoltage, rearLeftVoltage, rearRightVoltage);
  }

  public static final class AStruct implements Struct<MecanumDriveMotorVoltages> {
    @Override
    public Class<MecanumDriveMotorVoltages> getTypeClass() {
      return MecanumDriveMotorVoltages.class;
    }

    @Override
    public String getTypeString() {
      return "struct:MecanumDriveMotorVoltages";
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
    public MecanumDriveMotorVoltages unpack(ByteBuffer bb) {
      double frontLeft = bb.getDouble();
      double frontRight = bb.getDouble();
      double rearLeft = bb.getDouble();
      double rearRight = bb.getDouble();
      return new MecanumDriveMotorVoltages(frontLeft, frontRight, rearLeft, rearRight);
    }

    @Override
    public void pack(ByteBuffer bb, MecanumDriveMotorVoltages value) {
      bb.putDouble(value.frontLeftVoltage);
      bb.putDouble(value.frontRightVoltage);
      bb.putDouble(value.rearLeftVoltage);
      bb.putDouble(value.rearRightVoltage);
    }
  }

  public static final AStruct struct = new AStruct();

  public static final class AProto
      implements Protobuf<MecanumDriveMotorVoltages, ProtobufMecanumDriveMotorVoltages> {
    @Override
    public Class<MecanumDriveMotorVoltages> getTypeClass() {
      return MecanumDriveMotorVoltages.class;
    }

    @Override
    public Descriptor getDescriptor() {
      return ProtobufMecanumDriveMotorVoltages.getDescriptor();
    }

    @Override
    public ProtobufMecanumDriveMotorVoltages createMessage() {
      return ProtobufMecanumDriveMotorVoltages.newInstance();
    }

    @Override
    public MecanumDriveMotorVoltages unpack(ProtobufMecanumDriveMotorVoltages msg) {
      return new MecanumDriveMotorVoltages(
          msg.getFrontLeft(), msg.getFrontRight(), msg.getRearLeft(), msg.getRearRight());
    }

    @Override
    public void pack(ProtobufMecanumDriveMotorVoltages msg, MecanumDriveMotorVoltages value) {
      msg.setFrontLeft(value.frontLeftVoltage)
          .setFrontRight(value.frontRightVoltage)
          .setRearLeft(value.rearLeftVoltage)
          .setRearRight(value.rearRightVoltage);
    }
  }

  public static final AProto proto = new AProto();
}
