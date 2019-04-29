package io.shiftleft.queryprimitives.dsl

import io.shiftleft.queryprimitives.dsl.Implicits._
import io.shiftleft.queryprimitives.dsl.ShallowPipe.ShallowPipe
import io.shiftleft.queryprimitives.dsl.pipeops.PipeOperations
import org.scalatest.{Matchers, WordSpec}

case class A(a: Int = 0)

case class B(b: Int = 0)

class NodeAMethods[PipeType[+_]](val pipe: PipeType[A]) extends AnyVal {
  def toB(implicit ops: PipeOperations[PipeType]): PipeType[B] = {
    ops.map(pipe, (x: A) => B())
  }
  def toMultipleB(implicit ops: PipeOperations[PipeType]): RealPipe[B] = {
    ops.flatMap2(pipe, (x: A) => B() :: Nil)
  }
}

class NodeBMethods[PipeType[+_]](val pipe: PipeType[B]) extends AnyVal {
  def toA(implicit ops: PipeOperations[PipeType]): PipeType[A] = {
    ops.map(pipe, (x: B) => A())
  }
  def toMultipleA(implicit ops: PipeOperations[PipeType]): RealPipe[A] = {
    ops.flatMap2(pipe, (x: B) => A() :: Nil)
  }
}

object DslTests extends LowPrio {
  implicit def nodeAMethods(pipe: RealPipe[A]) = {
    new NodeAMethods(pipe)
  }

  implicit def nodeBMethods(pipe: RealPipe[B]) = {
    new NodeBMethods(pipe)
  }
}

trait LowPrio {
  implicit def nodeAMethods(pipe: ShallowPipe[A]) = {
    new NodeAMethods(pipe)
  }

  implicit def nodeBMethods(pipe: ShallowPipe[B]) = {
    new NodeBMethods(pipe)
  }

}


class DslTests extends WordSpec with Matchers {
  import DslTests._

  "Be able to call DSL methods on shallow pipe." in {
    A().toB shouldBe B()
  }

  "Be able to call multiple DSL methods on shallow pipe." in {
    A().toB.toA shouldBe A()
  }

  // TODO check/fix "real pipe" in test description is still the correct term.
  "Be able to call multiple DSL methods on real pipe." in {
    A().toMultipleB.toMultipleA.head shouldBe A()
  }

  "Be able to use map." in {
    A().toMultipleB.map(_.toA).head shouldBe A()
  }

  "Be able to use map with repeat function." in {
    A().toMultipleB.map(_.toA.toB, 1).head shouldBe B()
  }

  "Be able to use flatMap on real pipe function." in {
    A().toMultipleB.flatMap(_.toMultipleA).head shouldBe A()
  }

  "Be able to use flatMap on GenTraversableOnce function." in {
    A().toMultipleB.flatMap2(_ => A()::Nil).head shouldBe A()
  }

}