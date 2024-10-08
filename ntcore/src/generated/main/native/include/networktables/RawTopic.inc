// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

// THIS FILE WAS AUTO-GENERATED BY ./ntcore/generate_topics.py. DO NOT MODIFY

#pragma once

#include <vector>

#include "networktables/RawTopic.h"
#include "networktables/NetworkTableType.h"
#include "ntcore_cpp.h"

namespace nt {

inline RawSubscriber::RawSubscriber(
    NT_Subscriber handle, ParamType defaultValue)
    : Subscriber{handle},
      m_defaultValue{defaultValue.begin(), defaultValue.end()} {}

inline std::vector<uint8_t> RawSubscriber::Get() const {
  return Get(m_defaultValue);
}

inline std::vector<uint8_t> RawSubscriber::Get(
    ParamType defaultValue) const {
  return ::nt::GetRaw(m_subHandle, defaultValue);
}

inline std::span<uint8_t> RawSubscriber::Get(wpi::SmallVectorImpl<uint8_t>& buf) const {
  return Get(buf, m_defaultValue);
}

inline std::span<uint8_t> RawSubscriber::Get(wpi::SmallVectorImpl<uint8_t>& buf, ParamType defaultValue) const {
  return nt::GetRaw(m_subHandle, buf, defaultValue);
}

inline TimestampedRaw RawSubscriber::GetAtomic() const {
  return GetAtomic(m_defaultValue);
}

inline TimestampedRaw RawSubscriber::GetAtomic(
    ParamType defaultValue) const {
  return ::nt::GetAtomicRaw(m_subHandle, defaultValue);
}

inline TimestampedRawView RawSubscriber::GetAtomic(wpi::SmallVectorImpl<uint8_t>& buf) const {
  return GetAtomic(buf, m_defaultValue);
}

inline TimestampedRawView RawSubscriber::GetAtomic(wpi::SmallVectorImpl<uint8_t>& buf, ParamType defaultValue) const {
  return nt::GetAtomicRaw(m_subHandle, buf, defaultValue);
}

inline std::vector<TimestampedRaw>
RawSubscriber::ReadQueue() {
  return ::nt::ReadQueueRaw(m_subHandle);
}

inline RawTopic RawSubscriber::GetTopic() const {
  return RawTopic{::nt::GetTopicFromHandle(m_subHandle)};
}

inline RawPublisher::RawPublisher(NT_Publisher handle)
    : Publisher{handle} {}

inline void RawPublisher::Set(ParamType value,
                                         int64_t time) {
  ::nt::SetRaw(m_pubHandle, value, time);
}

inline void RawPublisher::SetDefault(ParamType value) {
  ::nt::SetDefaultRaw(m_pubHandle, value);
}

inline RawTopic RawPublisher::GetTopic() const {
  return RawTopic{::nt::GetTopicFromHandle(m_pubHandle)};
}

inline RawEntry::RawEntry(
    NT_Entry handle, ParamType defaultValue)
    : RawSubscriber{handle, defaultValue},
      RawPublisher{handle} {}

inline RawTopic RawEntry::GetTopic() const {
  return RawTopic{::nt::GetTopicFromHandle(m_subHandle)};
}

inline void RawEntry::Unpublish() {
  ::nt::Unpublish(m_pubHandle);
}

inline RawSubscriber RawTopic::Subscribe(
    std::string_view typeString, std::span<const uint8_t> defaultValue,
    const PubSubOptions& options) {
  return RawSubscriber{
      ::nt::Subscribe(m_handle, NT_RAW, typeString, options),
      defaultValue};
}
inline RawPublisher RawTopic::Publish(
    std::string_view typeString, const PubSubOptions& options) {
  return RawPublisher{
      ::nt::Publish(m_handle, NT_RAW, typeString, options)};
}

inline RawPublisher RawTopic::PublishEx(
    std::string_view typeString,
    const wpi::json& properties, const PubSubOptions& options) {
  return RawPublisher{
      ::nt::PublishEx(m_handle, NT_RAW, typeString, properties, options)};
}

inline RawEntry RawTopic::GetEntry(
    std::string_view typeString, std::span<const uint8_t> defaultValue,
    const PubSubOptions& options) {
  return RawEntry{
      ::nt::GetEntry(m_handle, NT_RAW, typeString, options),
      defaultValue};
}
}  // namespace nt
