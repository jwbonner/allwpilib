// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package edu.wpi.first.math.controller;

import edu.wpi.first.math.proto.Controller.ProtobufDifferentialDriveWheelVoltages;
import edu.wpi.first.util.protobuf.Protobuf;
import edu.wpi.first.util.struct.Struct;
import java.nio.ByteBuffer;
import us.hebi.quickbuf.Descriptors.Descriptor;

/** Motor voltages for a differential drive. */
public class DifferentialDriveWheelVoltages {
  public double left;
  public double right;

  public DifferentialDriveWheelVoltages() {}

  public DifferentialDriveWheelVoltages(double left, double right) {
    this.left = left;
    this.right = right;
  }

  public static final class AStruct implements Struct<DifferentialDriveWheelVoltages> {
    @Override
    public Class<DifferentialDriveWheelVoltages> getTypeClass() {
      return DifferentialDriveWheelVoltages.class;
    }

    @Override
    public String getTypeString() {
      return "struct:DifferentialDriveWheelVoltages";
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
    public DifferentialDriveWheelVoltages unpack(ByteBuffer bb) {
      double left = bb.getDouble();
      double right = bb.getDouble();
      return new DifferentialDriveWheelVoltages(left, right);
    }

    @Override
    public void pack(ByteBuffer bb, DifferentialDriveWheelVoltages value) {
      bb.putDouble(value.left);
      bb.putDouble(value.right);
    }
  }

  public static final AStruct struct = new AStruct();

  public static final class AProto
      implements Protobuf<DifferentialDriveWheelVoltages, ProtobufDifferentialDriveWheelVoltages> {
    @Override
    public Class<DifferentialDriveWheelVoltages> getTypeClass() {
      return DifferentialDriveWheelVoltages.class;
    }

    @Override
    public Descriptor getDescriptor() {
      return ProtobufDifferentialDriveWheelVoltages.getDescriptor();
    }

    @Override
    public ProtobufDifferentialDriveWheelVoltages createMessage() {
      return ProtobufDifferentialDriveWheelVoltages.newInstance();
    }

    @Override
    public DifferentialDriveWheelVoltages unpack(ProtobufDifferentialDriveWheelVoltages msg) {
      return new DifferentialDriveWheelVoltages(msg.getLeft(), msg.getRight());
    }

    @Override
    public void pack(
        ProtobufDifferentialDriveWheelVoltages msg, DifferentialDriveWheelVoltages value) {
      msg.setLeft(value.left).setRight(value.right);
    }
  }

  public static final AProto proto = new AProto();
}
