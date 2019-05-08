package io.shiftleft.queryprimitives.dsl

import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.codepropertygraph.generated.nodes.MethodReturn
import io.shiftleft.queryprimitives.dsl.pipetypes.RealPipe.RealPipe
import io.shiftleft.queryprimitives.dsl.pipetypes.ShallowPipe.ShallowPipe
import io.shiftleft.queryprimitives.dsl.pipeops.{RealPipeOperations, ShallowPipeOperations}
import io.shiftleft.queryprimitives.steps.types.structure.{MethodMethods, MethodReturnMethods}

object Implicits extends PipeOperationImplicits with LowPriorityImplicits {
  implicit def realPipeMethods[ElemType](pipe: RealPipe[ElemType]) = {
    new RealPipeMethods(pipe)
  }

  implicit def methodMethods[PipeType[+_]](pipe: PipeType[nodes.Method]) = {
    new MethodMethods(pipe)
  }

  implicit def methodReturnMethods[PipeType[+_]](pipe: PipeType[nodes.MethodReturn]) = {
    new MethodReturnMethods(pipe)
  }

}

class PipeOperationImplicits {
  private val realPipeOps = new RealPipeOperations[Nothing]()
  private val shallowPipeOps = new ShallowPipeOperations[Nothing]()

  implicit def getRealPipeOps[ElemType]: RealPipeOperations[ElemType] = {
    realPipeOps.asInstanceOf[RealPipeOperations[ElemType]]
  }

  implicit def getShallowPipeOps[ElemType]: ShallowPipeOperations[ElemType] = {
    shallowPipeOps.asInstanceOf[ShallowPipeOperations[ElemType]]
  }
}

sealed trait LowPriorityImplicits {

  implicit def methodMethods(pipe: nodes.Method) = {
    new MethodMethods(pipe.asInstanceOf[ShallowPipe[nodes.Method]])
  }

  implicit def methodReturnMethods(pipe: nodes.MethodReturn) = {
    new MethodReturnMethods(pipe.asInstanceOf[ShallowPipe[MethodReturn]])
  }

}

