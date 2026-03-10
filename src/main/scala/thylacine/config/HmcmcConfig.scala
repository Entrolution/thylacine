/*
 * Copyright 2023 Greg von Nessi
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package ai.entrolution
package thylacine.config

case class HmcmcConfig(
  stepsBetweenSamples: Int,
  stepsInDynamicsSimulation: Int,
  warmupStepCount: Int,
  dynamicsSimulationStepSize: Double,
  massMatrixDiagonal: Option[Vector[Double]] = None,
  adaptStepSize: Boolean                     = false,
  targetAcceptanceRate: Double               = 0.65
) {
  require(stepsBetweenSamples > 0, s"stepsBetweenSamples must be > 0, got $stepsBetweenSamples")
  require(stepsInDynamicsSimulation > 0, s"stepsInDynamicsSimulation must be > 0, got $stepsInDynamicsSimulation")
  require(warmupStepCount >= 0, s"warmupStepCount must be >= 0, got $warmupStepCount")
  require(dynamicsSimulationStepSize > 0, s"dynamicsSimulationStepSize must be > 0, got $dynamicsSimulationStepSize")
  require(
    targetAcceptanceRate > 0 && targetAcceptanceRate < 1,
    s"targetAcceptanceRate must be in (0, 1), got $targetAcceptanceRate"
  )
}
