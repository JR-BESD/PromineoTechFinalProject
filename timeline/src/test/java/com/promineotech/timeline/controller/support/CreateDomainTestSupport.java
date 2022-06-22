package com.promineotech.timeline.controller.support;

/**
 * @author JamesR
 *
 */
public class CreateDomainTestSupport extends BaseTest {

  /**
   * 
   * @return
   */
  protected String createDomainBody() {
    //@formatter:off
    String body = "{\n"
        + "   \"domainName\":\"Kartakass\",\n"
        + "   \"domainDesc\":\"Kartakass (Pronounced: KAR-ta-kass) was the domain ruled by Harkon Lukas. "
        + "Its culture revolved around music and bards. Apparently, it resembled Cormyr on Toril, "
        + "where Harkon Lukas originated. The domain was land-locked but had rivers and streams, "
        + "the main town was Skald and the second town was Harmonia, each ruled by a Meistersinger.\",\n"
        + "   \"region\":\"The Southern Core\"\n"  
        + "}";                
    //@formatter:on
    return body;
  }

}
