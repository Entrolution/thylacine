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
package thylacine.model.core.computation

import thylacine.TestUtils.*
import thylacine.model.core.values.{ IndexedVectorCollection, VectorContainer }

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should

class FiniteDifferenceJacobianSpec extends AnyFlatSpec with should.Matchers {

  // Linear: f(x1, x2) = [x1 + 2*x2, 3*x1 + 4*x2]
  private val linearEval: IndexedVectorCollection => VectorContainer = { input =>
    val x = input.index.head._2.scalaVector
    VectorContainer(Vector(x(0) + 2 * x(1), 3 * x(0) + 4 * x(1)))
  }

  // Quadratic: f(x1, x2) = [x1^2, x1*x2]
  private val quadraticEval: IndexedVectorCollection => VectorContainer = { input =>
    val x = input.index.head._2.scalaVector
    VectorContainer(Vector(x(0) * x(0), x(0) * x(1)))
  }

  "FiniteDifferenceJacobian" should "match exact Jacobian for a linear function" in {
    val fdJac  = FiniteDifferenceJacobian(linearEval, 1e-7)
    val result = fdJac.finiteDifferenceJacobianAt(IndexedVectorCollection(Map("p" -> Vector(1.0, 2.0))))
    val jac    = result.genericScalaRepresentation("p")
    // Exact: [[1, 2], [3, 4]]
    maxMatrixDiff(jac, Vector(Vector(1.0, 2.0), Vector(3.0, 4.0))) shouldBe (0.0 +- 1e-6)
  }

  it should "approximate analytical Jacobian for a quadratic function" in {
    val fdJac  = FiniteDifferenceJacobian(quadraticEval, 1e-7)
    val result = fdJac.finiteDifferenceJacobianAt(IndexedVectorCollection(Map("p" -> Vector(2.0, 3.0))))
    val jac    = result.genericScalaRepresentation("p")
    // Analytical: [[2*x1, 0], [x2, x1]] = [[4, 0], [3, 2]]
    maxMatrixDiff(jac, Vector(Vector(4.0, 0.0), Vector(3.0, 2.0))) shouldBe (0.0 +- 1e-3)
  }
}
