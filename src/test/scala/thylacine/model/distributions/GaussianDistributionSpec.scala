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

import thylacine.model.core.values.{ MatrixContainer, VectorContainer }

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should

class GaussianDistributionSpec extends AnyFlatSpec with should.Matchers {

  private val tol = 1e-8

  private def gaussianDist(mean: Vector[Double], covDiag: Vector[Double]): GaussianDistribution = {
    val covMatrix = covDiag.zipWithIndex.map { case (v, i) =>
      covDiag.indices.map(j => if (i == j) v else 0.0).toVector
    }.toVector
    GaussianDistribution(
      mean       = VectorContainer(mean),
      covariance = MatrixContainer(covMatrix)
    ).getValidated
  }

  "GaussianDistribution" should "have maximum logPdf at the mean" in {
    val dist         = gaussianDist(Vector(1.0, 2.0), Vector(1.0, 1.0))
    val atMean       = dist.logPdfAt(VectorContainer(Vector(1.0, 2.0)))
    val awayFromMean = dist.logPdfAt(VectorContainer(Vector(3.0, 4.0)))
    atMean should be > awayFromMean
  }

  it should "compute correct logPdf for 1D standard normal" in {
    val dist         = gaussianDist(Vector(0.0), Vector(1.0))
    val logPdfAtZero = dist.logPdfAt(VectorContainer(Vector(0.0)))
    val expected     = -0.5 * Math.log(2.0 * Math.PI)
    logPdfAtZero shouldBe (expected +- tol)
  }

  it should "compute zero gradient at the mean" in {
    val dist = gaussianDist(Vector(1.0, 2.0), Vector(1.0, 1.0))
    val grad = dist.logPdfGradientAt(VectorContainer(Vector(1.0, 2.0)))
    grad.rawVector(0) shouldBe (0.0 +- tol)
    grad.rawVector(1) shouldBe (0.0 +- tol)
  }

  it should "compute correct gradient for diagonal covariance" in {
    val dist = gaussianDist(Vector(1.0, 2.0), Vector(4.0, 9.0))
    val grad = dist.logPdfGradientAt(VectorContainer(Vector(3.0, 5.0)))
    // gradient = Sigma^{-1} * (mu - x) = diag(1/4, 1/9) * (-2, -3) = (-0.5, -1/3)
    grad.rawVector(0) shouldBe (-0.5 +- tol)
    grad.rawVector(1) shouldBe (-1.0 / 3.0 +- tol)
  }

  it should "report the correct domain dimension" in {
    val dist = gaussianDist(Vector(1.0, 2.0, 3.0), Vector(1.0, 1.0, 1.0))
    dist.domainDimension shouldBe 3
  }
}
