package com.google.api.generator.engine.ast;

import com.google.auto.value.AutoValue;
import com.google.common.base.Preconditions;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.Nullable;

@AutoValue
public abstract class AnonymousArrayAnnotationExpr implements Expr {

  @Nullable
  public abstract List<Expr> exprs();

  public abstract TypeNode type();

  @Override
  public void accept(AstNodeVisitor visitor) {
    visitor.visit(this);
  }

  public static AnonymousArrayAnnotationExpr.Builder builder() {
    return new AutoValue_AnonymousArrayAnnotationExpr.Builder();
  }

  public static AnonymousArrayAnnotationExpr withStrings(String ...stringValues) {
    return AnonymousArrayAnnotationExpr.builder()
        .setExprsString(Arrays.asList(stringValues))
        .build();
  }

  public static AnonymousArrayAnnotationExpr withExprs(Expr ...exprs) {
    return AnonymousArrayAnnotationExpr.builder()
        .setExprsList(Arrays.asList(exprs))
        .build();
  }

  @AutoValue.Builder
  public abstract static class Builder {
    private static final String REPEAT_SINGLE_EXCEPTION_MESSAGE =
        "Single parameter with no name cannot be set multiple times";

    private static final String EMPTY_EXPRS_MESSAGE = "List of expressions cannot be empty";

    private static final String SAME_TYPE_EXPRS_MESSAGE = "All expressions must have the same type";

    abstract List<Expr> exprs();

    protected abstract AnonymousArrayAnnotationExpr.Builder setType(TypeNode type);

    /**
     * To set single String as description.
     *
     * @param exprs
     * @return Builder
     */
    public AnonymousArrayAnnotationExpr.Builder setExprsString(List<String> exprs) {
      Preconditions.checkState(exprs() == null, REPEAT_SINGLE_EXCEPTION_MESSAGE);
      Preconditions.checkState(exprs.size() > 0, EMPTY_EXPRS_MESSAGE);
      List<Expr> valueExprs =
          exprs.stream()
              .map(x -> ValueExpr.withValue(StringObjectValue.withValue(x)))
              .collect(Collectors.toList());
      return setExprs(valueExprs);
    }

    /**
     * To set the list of expressions for the anonymous array
     *
     * @param exprs
     * @return Builder
     */
    public AnonymousArrayAnnotationExpr.Builder setExprsList(List<Expr> exprs) {
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

    /**
     * To add an AssignmentExpr as parameter. Can be used repeatedly to add multiple parameters.
     *
     * @param valueExpr
     * @return Builder
     */
    public AnonymousArrayAnnotationExpr.Builder addExpr(Expr valueExpr) {
      return addExprToList(valueExpr);
    }

    // this method is private, and called only by addDescription(AssignmentExpr expr)
    private AnonymousArrayAnnotationExpr.Builder addExprToList(Expr expr) {
      List<Expr> exprList = exprs();
      if (exprList == null) {
        exprList = new ArrayList<>();
      }
      exprList.add(expr);
      return setExprsList(exprList);
    }

    // this setter is private, and called only by setDescription() and setDescriptions() above.
    abstract AnonymousArrayAnnotationExpr.Builder setExprs(List<Expr> descriptionExprs);

    abstract AnonymousArrayAnnotationExpr autoBuild();

    public AnonymousArrayAnnotationExpr build() {
      AnonymousArrayAnnotationExpr anonymousArrayExpr = autoBuild();
      Preconditions.checkState(
          anonymousArrayExpr.exprs() != null && anonymousArrayExpr.exprs().size() > 0,
          EMPTY_EXPRS_MESSAGE);
      Reference ref = anonymousArrayExpr.exprs().get(0).type().reference();
      Preconditions.checkNotNull(ref, "Annotations must be an Object type");
      return anonymousArrayExpr;
    }
  }
}
