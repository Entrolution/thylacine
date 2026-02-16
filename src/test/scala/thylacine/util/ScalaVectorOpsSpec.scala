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
package thylacine.util

import thylacine.util.ScalaVectorOps.Implicits.*

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should

class ScalaVectorOpsSpec extends AnyFlatSpec with should.Matchers {

  "VectorOps" should "compute magnitudeSquared" in {
    Vector(3.0, 4.0).magnitudeSquared shouldBe 25.0
  }

  it should "compute magnitude" in {
    Vector(3.0, 4.0).magnitude shouldBe 5.0
  }

  it should "compute dotProductWith" in {
    Vector(1.0, 2.0).dotProductWith(Vector(3.0, 4.0)) shouldBe 11.0
  }

  it should "compute scalarMultiplyWith" in {
    Vector(1.0, 2.0).scalarMultiplyWith(3.0) shouldBe Vector(3.0, 6.0)
  }

  it should "compute add" in {
    Vector(1.0, 2.0).add(Vector(3.0, 4.0)) shouldBe Vector(4.0, 6.0)
  }

  it should "compute subtract" in {
    Vector(5.0, 6.0).subtract(Vector(1.0, 2.0)) shouldBe Vector(4.0, 4.0)
  }
}
