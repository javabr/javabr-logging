package org.javabr.mule.logging;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import org.mule.functional.junit4.MuleArtifactFunctionalTestCase;

import static org.junit.Assert.assertNull;

import org.junit.Test;

public class JavabrloggingOperationsTestCase extends MuleArtifactFunctionalTestCase {

  @Override
  protected String getConfigFile() {
    return "test-mule-config.xml";
  }

  @Test
  public void executeLogOperationNullContent() throws Exception {
    String payloadValue = ((String) flowRunner("testFlowNullContent").run()
        .getMessage()
        .getPayload()
        .getValue());
    assertNull(payloadValue);
  }

  // testFlowJSONContent
  @Test
  public void executeLogOperationJSONContent() throws Exception {
    String payloadValue = ((String) flowRunner("testFlowJSONContent").run()
        .getMessage()
        .getPayload()
        .getValue());
    assertNull(payloadValue);
  }

  @Test
  public void executeLogOperationJSONContentWithPayload() throws Exception {
    String payloadValue = ((String) flowRunner("testFlowJSONContentWithPayload").run()
        .getMessage()
        .getPayload()
        .getValue());
    assertThat(payloadValue, is("the payload is 20"));
  }

  @Test
  public void executeLogOperationJavaContentWithPayload() throws Exception {
    String payloadValue = ((String) flowRunner("testFlowJavaContentWithPayload").run()
        .getMessage()
        .getPayload()
        .getValue());
    assertThat(payloadValue, is("the payload is 20"));
  }
}
