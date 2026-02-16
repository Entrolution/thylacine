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
package thylacine.model.components.prior

import thylacine.model.core.values.IndexedVectorCollection

import cats.effect.IO
import cats.effect.testing.scalatest.AsyncIOSpec
import org.scalatest.freespec.AsyncFreeSpec
import org.scalatest.matchers.should.Matchers

class UniformPriorSpec extends AsyncFreeSpec with AsyncIOSpec with Matchers {

  private val prior = UniformPrior.fromBounds[IO](
    label     = "x",
    maxBounds = Vector(5.0),
    minBounds = Vector(-5.0)
  )

  "UniformPrior" - {

    "return zero gradient inside bounds" in {
      IO(prior.rawLogPdfGradientAt(Vector(0.0)))
        .asserting(_ shouldBe Vector(0.0))
    }

    "return correct logPdf inside bounds" in {
      // Volume = 10, logPdf = -log(10)
      prior
        .logPdfAt(IndexedVectorCollection(Map("x" -> Vector(0.0))))
        .asserting(_ shouldBe (-Math.log(10.0) +- 1e-8))
    }

    "return negative infinity logPdf outside bounds" in {
      prior
        .logPdfAt(IndexedVectorCollection(Map("x" -> Vector(6.0))))
        .asserting(_ shouldBe Double.NegativeInfinity)
    }
  }
}
