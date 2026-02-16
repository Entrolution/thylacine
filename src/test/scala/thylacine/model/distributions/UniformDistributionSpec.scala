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
package thylacine.model.distributions

import thylacine.model.core.values.VectorContainer

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should

class UniformDistributionSpec extends AnyFlatSpec with should.Matchers {

  private val tol = 1e-10

  private def uniformDist(lower: Vector[Double], upper: Vector[Double]): UniformDistribution =
    UniformDistribution(
      upperBounds = VectorContainer(upper),
      lowerBounds = VectorContainer(lower)
    ).getValidated

  "UniformDistribution" should "compute correct logPdf inside bounds" in {
    val dist   = uniformDist(Vector(0.0, 0.0), Vector(2.0, 3.0))
    val logPdf = dist.logPdfAt(VectorContainer(Vector(1.0, 1.5)))
    // volume = 2 * 3 = 6, logPdf = -log(6)
    logPdf shouldBe (-Math.log(6.0) +- tol)
  }

  it should "return negative infinity outside bounds" in {
    val dist = uniformDist(Vector(0.0, 0.0), Vector(1.0, 1.0))
    dist.logPdfAt(VectorContainer(Vector(2.0, 0.5))).isNegInfinity shouldBe true
    dist.logPdfAt(VectorContainer(Vector(0.5, -0.1))).isNegInfinity shouldBe true
  }

  it should "return zero gradient everywhere" in {
    val dist = uniformDist(Vector(0.0, 0.0), Vector(1.0, 1.0))
    val grad = dist.logPdfGradientAt(VectorContainer(Vector(0.5, 0.5)))
    grad.rawVector(0) shouldBe 0.0
    grad.rawVector(1) shouldBe 0.0
  }

  it should "detect points inside bounds correctly" in {
    val dist = uniformDist(Vector(-1.0, -1.0), Vector(1.0, 1.0))
    dist.insideBounds(VectorContainer(Vector(0.0, 0.0))) shouldBe true
    dist.insideBounds(VectorContainer(Vector(-1.0, 0.0))) shouldBe true // lower bound inclusive
    dist.insideBounds(VectorContainer(Vector(1.0, 0.0))) shouldBe false // upper bound exclusive
    dist.insideBounds(VectorContainer(Vector(2.0, 0.0))) shouldBe false
  }

  it should "report the correct domain dimension" in {
    val dist = uniformDist(Vector(0.0, 0.0, 0.0), Vector(1.0, 1.0, 1.0))
    dist.domainDimension shouldBe 3
  }

  it should "generate samples within bounds" in {
    val dist    = uniformDist(Vector(0.0, 0.0), Vector(1.0, 1.0))
    val samples = (1 to 100).map(_ => dist.getRawSample)
    samples.foreach { s =>
      dist.insideBounds(s) shouldBe true
    }
  }

  it should "compute correct negLogVolume for 1D" in {
    val dist = uniformDist(Vector(2.0), Vector(5.0))
    dist.negLogVolume shouldBe (-Math.log(3.0) +- tol)
  }
}
