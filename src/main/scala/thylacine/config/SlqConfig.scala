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

case class SlqConfig(
  poolSize: Int,
  abscissaNumber: Int,
  domainScalingIncrement: Double,
  targetAcceptanceProbability: Double,
  sampleParallelism: Int,
  maxIterationCount: Int,
  minIterationCount: Int
) {
  require(poolSize > 0, s"poolSize must be > 0, got $poolSize")
  require(abscissaNumber > 0, s"abscissaNumber must be > 0, got $abscissaNumber")
  require(domainScalingIncrement > 0, s"domainScalingIncrement must be > 0, got $domainScalingIncrement")
  require(
    targetAcceptanceProbability > 0 && targetAcceptanceProbability < 1,
    s"targetAcceptanceProbability must be in (0, 1), got $targetAcceptanceProbability"
  )
  require(sampleParallelism > 0, s"sampleParallelism must be > 0, got $sampleParallelism")
  require(maxIterationCount > 0, s"maxIterationCount must be > 0, got $maxIterationCount")
  require(minIterationCount > 0, s"minIterationCount must be > 0, got $minIterationCount")
  require(
    minIterationCount <= maxIterationCount,
    s"minIterationCount ($minIterationCount) must be <= maxIterationCount ($maxIterationCount)"
  )
}
