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

class CauchyDistributionSpec extends AnyFlatSpec with should.Matchers {

  private val tol = 1e-8

  private def cauchyDist(mean: Vector[Double], covDiag: Vector[Double]): CauchyDistribution = {
    val covMatrix = covDiag.zipWithIndex.map { case (v, i) =>
      covDiag.indices.map(j => if (i == j) v else 0.0).toVector
    }.toVector
    CauchyDistribution(
      mean       = VectorContainer(mean),
      covariance = MatrixContainer(covMatrix)
    ).getValidated
  }

  "CauchyDistribution" should "have maximum logPdf at the mean" in {
    val dist         = cauchyDist(Vector(1.0, 2.0), Vector(1.0, 1.0))
    val atMean       = dist.logPdfAt(VectorContainer(Vector(1.0, 2.0)))
    val awayFromMean = dist.logPdfAt(VectorContainer(Vector(5.0, 6.0)))
    atMean should be > awayFromMean
  }

  it should "be symmetric around the mean" in {
    val dist  = cauchyDist(Vector(0.0), Vector(1.0))
    val left  = dist.logPdfAt(VectorContainer(Vector(-2.0)))
    val right = dist.logPdfAt(VectorContainer(Vector(2.0)))
    left shouldBe (right +- tol)
  }

  it should "compute zero gradient at the mean" in {
    val dist = cauchyDist(Vector(1.0, 2.0), Vector(1.0, 1.0))
    val grad = dist.logPdfGradientAt(VectorContainer(Vector(1.0, 2.0)))
    grad.rawVector(0) shouldBe (0.0 +- tol)
    grad.rawVector(1) shouldBe (0.0 +- tol)
  }

  it should "report the correct domain dimension" in {
    val dist = cauchyDist(Vector(1.0, 2.0, 3.0), Vector(1.0, 1.0, 1.0))
    dist.domainDimension shouldBe 3
  }

  it should "have heavier tails than Gaussian" in {
    val gaussDist = GaussianDistribution(
      mean       = VectorContainer(Vector(0.0)),
      covariance = MatrixContainer(Vector(Vector(1.0)))
    ).getValidated
    val cauchDist = cauchyDist(Vector(0.0), Vector(1.0))

    // At a far point, Cauchy logPdf should be higher (heavier tails)
    val farPoint = VectorContainer(Vector(10.0))
    cauchDist.logPdfAt(farPoint) should be > gaussDist.logPdfAt(farPoint)
  }

  it should "generate samples" in {
    val dist   = cauchyDist(Vector(0.0, 0.0), Vector(1.0, 1.0))
    val sample = dist.getRawSample
    sample.dimension shouldBe 2
  }
}
