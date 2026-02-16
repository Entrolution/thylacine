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

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should

class LinearAlgebraSpec extends AnyFlatSpec with should.Matchers {

  private val tol = 1e-10

  "LinearAlgebra" should "create a zeros matrix" in {
    val m = LinearAlgebra.zeros(2, 3)
    m.numRows shouldBe 2
    m.numCols shouldBe 3
    m.get(0, 0) shouldBe 0.0
  }

  it should "invert a 2x2 matrix" in {
    val m   = LinearAlgebra.fromArray2D(Array(Array(4.0, 7.0), Array(2.0, 6.0)))
    val inv = LinearAlgebra.invert(m)
    inv.get(0, 0) shouldBe (0.6 +- tol)
    inv.get(0, 1) shouldBe (-0.7 +- tol)
    inv.get(1, 0) shouldBe (-0.2 +- tol)
    inv.get(1, 1) shouldBe (0.4 +- tol)
  }

  it should "verify that A * A^-1 = I" in {
    val m       = LinearAlgebra.fromArray2D(Array(Array(1.0, 2.0), Array(3.0, 4.0)))
    val inv     = LinearAlgebra.invert(m)
    val product = LinearAlgebra.multiply(m, inv)
    product.get(0, 0) shouldBe (1.0 +- tol)
    product.get(0, 1) shouldBe (0.0 +- tol)
    product.get(1, 0) shouldBe (0.0 +- tol)
    product.get(1, 1) shouldBe (1.0 +- tol)
  }

  it should "compute determinant" in {
    val m = LinearAlgebra.fromArray2D(Array(Array(1.0, 2.0), Array(3.0, 4.0)))
    LinearAlgebra.determinant(m) shouldBe (-2.0 +- tol)
  }

  it should "transpose a matrix" in {
    val m = LinearAlgebra.fromArray2D(Array(Array(1.0, 2.0, 3.0), Array(4.0, 5.0, 6.0)))
    val t = LinearAlgebra.transpose(m)
    t.numRows shouldBe 3
    t.numCols shouldBe 2
    t.get(0, 0) shouldBe 1.0
    t.get(0, 1) shouldBe 4.0
    t.get(1, 0) shouldBe 2.0
    t.get(2, 0) shouldBe 3.0
  }

  it should "multiply two matrices" in {
    val a      = LinearAlgebra.fromArray2D(Array(Array(1.0, 2.0), Array(3.0, 4.0)))
    val b      = LinearAlgebra.fromArray2D(Array(Array(5.0, 6.0), Array(7.0, 8.0)))
    val result = LinearAlgebra.multiply(a, b)
    result.get(0, 0) shouldBe (19.0 +- tol)
    result.get(0, 1) shouldBe (22.0 +- tol)
    result.get(1, 0) shouldBe (43.0 +- tol)
    result.get(1, 1) shouldBe (50.0 +- tol)
  }

  it should "multiply matrix by vector (Array)" in {
    val m      = LinearAlgebra.fromArray2D(Array(Array(1.0, 2.0), Array(3.0, 4.0)))
    val v      = Array(5.0, 6.0)
    val result = LinearAlgebra.multiplyMV(m, v)
    result(0) shouldBe (17.0 +- tol)
    result(1) shouldBe (39.0 +- tol)
  }

  it should "solve linear system A * x = b" in {
    val a = LinearAlgebra.fromArray2D(Array(Array(2.0, 1.0), Array(5.0, 3.0)))
    val b = Array(4.0, 7.0)
    val x = LinearAlgebra.solve(a, b)
    x(0) shouldBe (5.0 +- tol)
    x(1) shouldBe (-6.0 +- tol)
  }

  it should "compute quadratic form v^T * M * v" in {
    val m = LinearAlgebra.fromArray2D(Array(Array(2.0, 0.0), Array(0.0, 3.0)))
    val v = Array(1.0, 2.0)
    LinearAlgebra.quadraticForm(v, m) shouldBe (14.0 +- tol)
  }

  it should "add two matrices" in {
    val a      = LinearAlgebra.fromArray2D(Array(Array(1.0, 2.0), Array(3.0, 4.0)))
    val b      = LinearAlgebra.fromArray2D(Array(Array(5.0, 6.0), Array(7.0, 8.0)))
    val result = LinearAlgebra.add(a, b)
    result.get(0, 0) shouldBe 6.0
    result.get(0, 1) shouldBe 8.0
    result.get(1, 0) shouldBe 10.0
    result.get(1, 1) shouldBe 12.0
  }

  it should "subtract two matrices" in {
    val a      = LinearAlgebra.fromArray2D(Array(Array(5.0, 6.0), Array(7.0, 8.0)))
    val b      = LinearAlgebra.fromArray2D(Array(Array(1.0, 2.0), Array(3.0, 4.0)))
    val result = LinearAlgebra.subtract(a, b)
    result.get(0, 0) shouldBe 4.0
    result.get(1, 1) shouldBe 4.0
  }

  it should "scale a matrix" in {
    val m      = LinearAlgebra.fromArray2D(Array(Array(1.0, 2.0), Array(3.0, 4.0)))
    val result = LinearAlgebra.scale(m, 3.0)
    result.get(0, 0) shouldBe 3.0
    result.get(0, 1) shouldBe 6.0
    result.get(1, 0) shouldBe 9.0
    result.get(1, 1) shouldBe 12.0
  }

  it should "divide a matrix by scalar" in {
    val m      = LinearAlgebra.fromArray2D(Array(Array(6.0, 4.0), Array(2.0, 8.0)))
    val result = LinearAlgebra.divide(m, 2.0)
    result.get(0, 0) shouldBe 3.0
    result.get(0, 1) shouldBe 2.0
    result.get(1, 0) shouldBe 1.0
    result.get(1, 1) shouldBe 4.0
  }

  it should "symmetrize a matrix" in {
    val m      = LinearAlgebra.fromArray2D(Array(Array(1.0, 2.0), Array(4.0, 3.0)))
    val result = LinearAlgebra.symmetrize(m)
    result.get(0, 0) shouldBe (1.0 +- tol)
    result.get(0, 1) shouldBe (3.0 +- tol)
    result.get(1, 0) shouldBe (3.0 +- tol)
    result.get(1, 1) shouldBe (3.0 +- tol)
  }

  it should "convert array to column vector and back" in {
    val arr = Array(1.0, 2.0, 3.0)
    val v   = LinearAlgebra.arrayToColumnVector(arr)
    v.numRows shouldBe 3
    v.numCols shouldBe 1
    LinearAlgebra.columnVectorToArray(v) shouldBe Array(1.0, 2.0, 3.0)
  }

  it should "roundtrip through toArray2D and fromArray2D" in {
    val original      = LinearAlgebra.fromArray2D(Array(Array(1.0, 2.0, 3.0), Array(4.0, 5.0, 6.0)))
    val array2d       = LinearAlgebra.toArray2D(original)
    val reconstructed = LinearAlgebra.fromArray2D(array2d)
    reconstructed.get(0, 0) shouldBe 1.0
    reconstructed.get(0, 2) shouldBe 3.0
    reconstructed.get(1, 0) shouldBe 4.0
    reconstructed.get(1, 2) shouldBe 6.0
  }

  it should "produce invalid results for singular matrix inversion" in {
    val singular = LinearAlgebra.fromArray2D(Array(Array(1.0, 2.0), Array(2.0, 4.0)))
    val result   = LinearAlgebra.invert(singular)
    // EJML may produce NaN, Inf, or numerically poor results for singular matrices.
    // Verify A * A^-1 != I
    val product = LinearAlgebra.multiply(singular, result)
    val isIdentity = product.get(0, 0) == 1.0 && product.get(1, 1) == 1.0 &&
      product.get(0, 1) == 0.0 && product.get(1, 0) == 0.0
    isIdentity shouldBe false
  }
}
