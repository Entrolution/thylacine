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
package thylacine.model.core.values

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should

class VectorContainerSpec extends AnyFlatSpec with should.Matchers {

  "VectorContainer" should "construct from a Scala Vector" in {
    val vc = VectorContainer(Vector(1.0, 2.0, 3.0))
    vc.dimension shouldBe 3
    vc.rawVector shouldBe Array(1.0, 2.0, 3.0)
  }

  it should "construct from an Array" in {
    val vc = VectorContainer(Array(4.0, 5.0))
    vc.dimension shouldBe 2
    vc.rawVector shouldBe Array(4.0, 5.0)
  }

  it should "handle zero values sparsely" in {
    val vc = VectorContainer(Vector(0.0, 1.0, 0.0, 2.0))
    vc.getValidated.values should not contain key(1)
    vc.getValidated.values should not contain key(3)
    vc.rawVector shouldBe Array(0.0, 1.0, 0.0, 2.0)
  }

  it should "compute magnitude correctly" in {
    val vc = VectorContainer(Vector(3.0, 4.0))
    vc.squaredMagnitude shouldBe 25.0
    vc.magnitude shouldBe 5.0
  }

  it should "sum two vectors" in {
    val v1 = VectorContainer(Vector(1.0, 2.0, 3.0))
    val v2 = VectorContainer(Vector(4.0, 5.0, 6.0))
    v1.rawSumWith(v2).rawVector shouldBe Array(5.0, 7.0, 9.0)
  }

  it should "subtract two vectors" in {
    val v1 = VectorContainer(Vector(4.0, 5.0, 6.0))
    val v2 = VectorContainer(Vector(1.0, 2.0, 3.0))
    v1.rawSubtract(v2).rawVector shouldBe Array(3.0, 3.0, 3.0)
  }

  it should "scalar multiply" in {
    val vc = VectorContainer(Vector(1.0, 2.0, 3.0))
    vc.rawScalarProductWith(2.0).rawVector shouldBe Array(2.0, 4.0, 6.0)
  }

  it should "compute dot product" in {
    val v1 = VectorContainer(Vector(1.0, 2.0, 3.0))
    val v2 = VectorContainer(Vector(4.0, 5.0, 6.0))
    v1.rawDotProductWith(v2) shouldBe 32.0
  }

  it should "concatenate vectors" in {
    val v1     = VectorContainer(Vector(1.0, 2.0))
    val v2     = VectorContainer(Vector(3.0, 4.0, 5.0))
    val result = v1.rawConcatenateWith(v2)
    result.dimension shouldBe 5
    result.rawVector shouldBe Array(1.0, 2.0, 3.0, 4.0, 5.0)
  }

  it should "compute element-wise product" in {
    val v1 = VectorContainer(Vector(2.0, 3.0, 4.0))
    val v2 = VectorContainer(Vector(5.0, 6.0, 7.0))
    v1.rawProductWith(v2).rawVector shouldBe Array(10.0, 18.0, 28.0)
  }

  it should "compute absolute value of components" in {
    val vc = VectorContainer(Vector(-1.0, 2.0, -3.0))
    vc.rawAbsoluteValueOfComponents.rawVector shouldBe Array(1.0, 2.0, 3.0)
  }

  it should "compute value sum" in {
    val vc = VectorContainer(Vector(1.0, 2.0, 3.0))
    vc.valueSum shouldBe 6.0
  }

  it should "create zeros vector" in {
    val vc = VectorContainer.zeros(4)
    vc.dimension shouldBe 4
    vc.rawVector shouldBe Array(0.0, 0.0, 0.0, 0.0)
    vc.values shouldBe empty
  }

  it should "create filled vector" in {
    val vc = VectorContainer.fill(3)(7.0)
    vc.dimension shouldBe 3
    vc.rawVector shouldBe Array(7.0, 7.0, 7.0)
  }

  it should "nudge individual components" in {
    val vc     = VectorContainer(Vector(1.0, 2.0, 3.0))
    val nudged = vc.rawNudgeComponents(0.1)
    nudged.size shouldBe 3
    nudged(0).rawVector shouldBe Array(1.1, 2.0, 3.0)
    nudged(1).rawVector shouldBe Array(1.0, 2.1, 3.0)
    nudged(2).rawVector shouldBe Array(1.0, 2.0, 3.1)
  }
}
