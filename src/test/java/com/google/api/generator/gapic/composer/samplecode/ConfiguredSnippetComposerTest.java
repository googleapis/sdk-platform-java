package com.google.api.generator.gapic.composer.samplecode;

import static org.junit.Assert.assertEquals;
import com.google.api.generator.engine.ast.ClassDefinition;
import com.google.api.generator.engine.ast.Statement;
import com.google.api.generator.gapic.composer.common.TestProtoLoader;
import com.google.api.generator.gapic.model.GapicContext;
import com.google.api.generator.gapic.model.GapicSnippetConfig;
import org.junit.Test;

import java.util.List;

public class ConfiguredSnippetComposerTest {

  private GapicContext context = TestProtoLoader.instance().parseShowcaseEcho();
  private List<GapicSnippetConfig> gapicSnippetConfigList = context.snippetConfigs();
  private GapicSnippetConfig snippetConfig = gapicSnippetConfigList.get(0);

  @Test
  public void composeSampleMethodName() {
    String result = ConfiguredSnippetComposer.composeSampleMethodName(snippetConfig);
    assertEquals("asyncCreateCustomClass", result);
  }

  // TODO: Update test
  //    @Test
  //    public void createConfigSnippet() {
  //        String sampleResult =
  // writeSample(ConfiguredSnippetComposer.composeConfiguredSnippetClass(snippetConfig));
  //        String expected =
  //                LineFormatter.lines(
  //                        "google.cloud.speech.v1.samples;\n",
  //                        "\n",
  //                        "// [START
  // adaptation_v1_config_Adaptation_CreateCustomClass_Basic_sync]\n",
  //                        "public class SyncCreateExecutableSampleEmptyStatementSample {\n",
  //                        "\n",
  //                        "  public static void main(String[] args) throws Exception {\n",
  //                        "    syncCreateExecutableSampleEmptyStatementSample();\n",
  //                        "  }\n",
  //                        "\n",
  //                        "  public static void syncCreateExecutableSampleEmptyStatementSample()
  // throws Exception {\n",
  //                        "    // This snippet has been automatically generated and should be
  // regarded as a code template only.\n",
  //                        "    // It will require modifications to work:\n",
  //                        "    // - It may require correct/in-range values for request
  // initialization.\n",
  //                        "    // - It may require specifying regional endpoints when creating the
  // service client as shown in\n",
  //                        "    //
  // https://cloud.google.com/java/docs/setup#configure_endpoints_for_the_client_library\n",
  //                        "  }\n",
  //                        "}\n",
  //                        "// [END
  // adaptation_v1_config_Adaptation_CreateCustomClass_Basic_sync]\n");
  //        assertEquals(expected, sampleResult);
  //    }

  private static String writeSample(ClassDefinition sample) {
    return SampleCodeWriter.write(sample);
  }

  private static String writeSample(List<Statement> sample) {
    return SampleCodeWriter.write(sample);
  }
}
