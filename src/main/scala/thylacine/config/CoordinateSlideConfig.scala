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

case class CoordinateSlideConfig(
  convergenceThreshold: Double,
  goldenSectionTolerance: Double,
  lineProbeExpansionFactor: Double,
  numberOfPriorSamplesToSetScale: Option[Int]
) {
  require(convergenceThreshold > 0, s"convergenceThreshold must be > 0, got $convergenceThreshold")
  require(goldenSectionTolerance > 0, s"goldenSectionTolerance must be > 0, got $goldenSectionTolerance")
  require(lineProbeExpansionFactor > 0, s"lineProbeExpansionFactor must be > 0, got $lineProbeExpansionFactor")
  numberOfPriorSamplesToSetScale.foreach(n => require(n > 0, s"numberOfPriorSamplesToSetScale must be > 0, got $n"))
}
