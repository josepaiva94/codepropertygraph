package io.shiftleft.fuzzyc2cpg.ast.expressions;

import io.shiftleft.fuzzyc2cpg.ast.walking.ASTNodeVisitor;

public class AssignmentExpression extends BinaryExpression {
  @Override
  public void accept(ASTNodeVisitor visitor) {
    visitor.visit(this);
  }
}
