// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

#pragma once

#include <Eigen/Core>
#include <units/time.h>
#include <units/voltage.h>

namespace sysid {

/**
 * Simulation of a Simple Motor mechanism based off of a model from SysId
 * Feedforward gains.
 */
class SimpleMotorSim {
 public:
  /**
   * @param Ks              Static friction gain.
   * @param Kv              Velocity gain.
   * @param Ka              Acceleration gain.
   * @param initialPosition Initial flywheel position.
   * @param initialVelocity Initial flywheel velocity.
   */
  SimpleMotorSim(double Ks, double Kv, double Ka, double initialPosition = 0.0,
                 double initialVelocity = 0.0);

  /**
   * Simulates affine state-space system dx/dt = Ax + Bu + c sgn(x) forward dt
   * seconds.
   *
   * @param voltage Voltage to apply over the timestep.
   * @param dt      Sample period.
   */
  void Update(units::volt_t voltage, units::second_t dt);

  /**
   * Returns the position.
   *
   * @return The current position
   */
  double GetPosition() const;

  /**
   * Returns the velocity.
   *
   * @return The current velocity
   */
  double GetVelocity() const;

  /**
   * Returns the acceleration for the current state and given input.
   *
   * @param voltage The voltage that is being applied to the mechanism / input
   * @return The acceleration given the state and input
   */
  double GetAcceleration(units::volt_t voltage) const;

  /**
   * Resets model position and velocity.
   *
   * @param position The position the mechanism should be reset to
   * @param velocity The velocity the mechanism should be reset to
   */
  void Reset(double position = 0.0, double velocity = 0.0);

 private:
  Eigen::Matrix<double, 2, 2> m_A;
  Eigen::Matrix<double, 2, 1> m_B;
  Eigen::Vector<double, 2> m_c;
  Eigen::Vector<double, 2> m_x;
};

}  // namespace sysid
