package com.google.api.generator.gapic.composer.spanner;

import com.google.api.generator.gapic.composer.grpc.GrpcServiceStubClassComposer;
import com.google.api.generator.gapic.composer.grpc.GrpcTestProtoLoader;
import com.google.api.generator.gapic.model.GapicClass;
import com.google.api.generator.gapic.model.GapicContext;
import com.google.api.generator.gapic.model.Service;
import com.google.api.generator.test.framework.Assert;
import org.junit.Test;

public class SpannerOptionsClassComposerTest {
  @Test
  public void generateGrpcServiceStubClass_simple() {
    GapicContext context = GrpcTestProtoLoader.instance().parseSpanner();
    Service service = context.services().get(0);
    GapicClass clazz = GrpcServiceStubClassComposer.instance().generate(context, service);

    // Assert.assertGoldenClass(this.getClass(), clazz, "SpannerOptionStub.golden");
    // Assert.assertEmptySamples(clazz.samples());
  }

}
