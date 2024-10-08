// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

#include "frc/geometry/proto/Pose2dProto.h"

#include <wpi/ProtoHelper.h>

#include "geometry2d.pb.h"

google::protobuf::Message* wpi::Protobuf<frc::Pose2d>::New(
    google::protobuf::Arena* arena) {
  return wpi::CreateMessage<wpi::proto::ProtobufPose2d>(arena);
}

frc::Pose2d wpi::Protobuf<frc::Pose2d>::Unpack(
    const google::protobuf::Message& msg) {
  auto m = static_cast<const wpi::proto::ProtobufPose2d*>(&msg);
  return frc::Pose2d{
      wpi::UnpackProtobuf<frc::Translation2d>(m->wpi_translation()),
      wpi::UnpackProtobuf<frc::Rotation2d>(m->wpi_rotation()),
  };
}

void wpi::Protobuf<frc::Pose2d>::Pack(google::protobuf::Message* msg,
                                      const frc::Pose2d& value) {
  auto m = static_cast<wpi::proto::ProtobufPose2d*>(msg);
  wpi::PackProtobuf(m->mutable_translation(), value.Translation());
  wpi::PackProtobuf(m->mutable_rotation(), value.Rotation());
}
