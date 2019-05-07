package io.shiftleft.queryprimitives.dsl.pipeops

import io.shiftleft.queryprimitives.dsl.pipetypes.RealPipe.RealPipe
import io.shiftleft.queryprimitives.dsl.pipetypes.ShallowPipe.ShallowPipe

import scala.collection.GenTraversableOnce

class ShallowPipeOperations[ElemType] extends PipeOperations[ShallowPipe, ElemType] {

  override def map[DstType](pipe: ShallowPipe[ElemType],
                            function: ElemType => DstType): ShallowPipe[DstType] = {
    function.apply(pipe)
  }

  override def flatMap[DstType](pipe: ShallowPipe[ElemType],
                                function: ElemType => GenTraversableOnce[DstType]): RealPipe[DstType] = {
    RealPipe(function.apply(pipe).toList)
  }

  override def filter(pipe: ShallowPipe[ElemType],
                      function: ElemType => Boolean): RealPipe[ElemType] = {
    if (function.apply(pipe)){
      RealPipe(pipe :: Nil)
    } else {
      RealPipe(Nil)
    }
  }

  override def head(pipe: ShallowPipe[ElemType]): ElemType = {
    pipe
  }

  override def iterator(pipe: ShallowPipe[ElemType]): Iterator[ElemType] = {
    (pipe :: Nil).toIterator
  }

  override def toList(pipe: ShallowPipe[ElemType]): List[ElemType] = {
    pipe :: Nil
  }

  override def toSet(pipe: ShallowPipe[ElemType]): Set[ElemType] = {
    Set() + pipe
  }

  override def foreach[DstType](pipe: ShallowPipe[ElemType],
                                function: ElemType => DstType): Unit = {
    function(pipe)
  }
}

