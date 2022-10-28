package com.google.api.generator.engine.ast;

import com.google.auto.value.AutoValue;
import com.google.common.base.Preconditions;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.Nullable;

@AutoValue
public abstract class ArrayExpr implements Expr {

  @Nullable
  public abstract List<Expr> exprs();

  public abstract TypeNode type();

  @Override
  public void accept(AstNodeVisitor visitor) {
    visitor.visit(this);
  }

  public static ArrayExpr.Builder builder() {
    return new AutoValue_ArrayExpr.Builder();
  }

  public static ArrayExpr withStrings(String ...stringValues) {
    ArrayExpr.Builder builder = ArrayExpr.builder();
    Arrays.asList(stringValues)
        .stream()
        .forEach(s -> builder.addExpr(s));
    return builder.build();
  }

  public static ArrayExpr withExprs(Expr ...exprs) {
    return ArrayExpr.builder()
        .setExprsList(Arrays.asList(exprs))
        .build();
  }

  @AutoValue.Builder
  public abstract static class Builder {

    private static final String EMPTY_EXPRS_MESSAGE = "List of expressions cannot be empty";

    private static final String SAME_TYPE_EXPRS_MESSAGE = "All expressions must have the same type";

    abstract List<Expr> exprs();

    protected abstract ArrayExpr.Builder setType(TypeNode type);

    /**
     * To add a ValueExpr as to our list. Can be used repeatedly to add multiple parameters.
     * same-type validation is performed
     *
     * @param expr
     * @return Builder
     */
    public ArrayExpr.Builder addExpr(ValueExpr expr) {
      return addExprToList(expr);
    }

    /**
     * To add a VariableExpr as to our list. Can be used repeatedly to add multiple parameters.
     * same-type validation is performed
     *
     * @param expr
     * @return Builder
     */
    public ArrayExpr.Builder addExpr(VariableExpr expr) {
      return addExprToList(expr);
    }

    /**
     * To add a string expression
     * same-type validation is performed
     *
     * @param expr
     * @return Builder
     */
    public ArrayExpr.Builder addExpr(String expr) {
      return addExprToList(ValueExpr.withValue(StringObjectValue.withValue(expr)));
    }

    /**
     * To set the list of expressions for the anonymous array
     * Validates that every expression is of the same type
     *
     * @param exprs
     * @return Builder
     */
    private ArrayExpr.Builder setExprsList(List<Expr> exprs) {
      Preconditions.checkState(exprs.size() > 0, EMPTY_EXPRS_MESSAGE);
      // validate types
      TypeNode baseType = exprs.get(0).type();
      for (int i = 1; i < exprs.size(); i++) {
        TypeNode currentType = exprs.get(i).type();
        Preconditions.checkState(currentType.equals(baseType), SAME_TYPE_EXPRS_MESSAGE);
        baseType = currentType;
      }
      setType(baseType);
      return setExprs(exprs);
    }

    // this method is private, and called only by addExpr(Expr expr)
    private ArrayExpr.Builder addExprToList(Expr expr) {
      List<Expr> exprList = exprs();
      if (exprList == null) {
        exprList = new ArrayList<>();
      }
      exprList.add(expr);
      return setExprsList(exprList);
    }

    // this setter is private, and called only by setExprsList() to ensured sanitized contents
    abstract ArrayExpr.Builder setExprs(List<Expr> descriptionExprs);

    abstract ArrayExpr autoBuild();

    public ArrayExpr build() {
      ArrayExpr anonymousArrayExpr = autoBuild();
      Preconditions.checkState(
          anonymousArrayExpr.exprs() != null && anonymousArrayExpr.exprs().size() > 0,
          EMPTY_EXPRS_MESSAGE);
      Reference ref = anonymousArrayExpr.exprs().get(0).type().reference();
      Preconditions.checkNotNull(ref, "Annotations must be an Object type");
      return anonymousArrayExpr;
    }
  }
}
