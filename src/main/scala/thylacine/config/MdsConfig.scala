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

case class MdsConfig(
  convergenceThreshold: Double,
  expansionMultiplier: Double,
  contractionMultiplier: Double,
  numberOfPriorSamplesToSetStartingPoint: Option[Int]
) {
  require(convergenceThreshold > 0, s"convergenceThreshold must be > 0, got $convergenceThreshold")
  require(expansionMultiplier > 1, s"expansionMultiplier must be > 1, got $expansionMultiplier")
  require(
    contractionMultiplier > 0 && contractionMultiplier < 1,
    s"contractionMultiplier must be in (0, 1), got $contractionMultiplier"
  )
  numberOfPriorSamplesToSetStartingPoint.foreach(n =>
    require(n > 0, s"numberOfPriorSamplesToSetStartingPoint must be > 0, got $n")
  )
}
