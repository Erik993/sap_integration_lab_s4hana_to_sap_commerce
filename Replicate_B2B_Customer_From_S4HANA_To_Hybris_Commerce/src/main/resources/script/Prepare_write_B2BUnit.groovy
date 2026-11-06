import com.sap.gateway.ip.core.customdev.util.Message
import groovy.util.XmlParser

def Message processData(Message message) {

    def body = message.getBody(java.lang.String) as String

    message.setProperty("BP_PAYLOAD", body)

    String B2BUnit_DATASTORE_ENTRY_ID = getNodeValueWithParser(body, "InternalID")
    String CorrespondenceLanguageCode = getNodeValueWithParser(body, "CorrespondenceLanguageCode")

    message.setHeader("ENTRY_ID", B2BUnit_DATASTORE_ENTRY_ID)
    
    def b2bUnitXml = buildB2BUnitXmlWithParser(body, B2BUnit_DATASTORE_ENTRY_ID, CorrespondenceLanguageCode)
    
    message.setBody(b2bUnitXml)
    message.setHeader("DATASTORE_NAME", "BP_B2BUnit")
    message.setHeader("ACTION", "WRITE")

    return message

}

def buildB2BUnitXmlWithParser(String xml, String businessPartnerInternalID, String correspondenceLanguageCode) {
    def salesArrangementList = []
    
    def parser = new XmlParser()
    def rootNode = parser.parseText(xml)
    
    def salesArrangements = rootNode.depthFirst().findAll { it.name() == 'SalesArrangement' }
    
    salesArrangements.each { salesArrangement ->
        def salesOrgID = salesArrangement.SalesOrganisationID?.text() ?: ""
        def distChannelCode = salesArrangement.DistributionChannelCode?.text() ?: ""
        def divisionCode = salesArrangement.DivisionCode?.text() ?: ""
        
        def salesUnit = "${businessPartnerInternalID}_${salesOrgID}_${distChannelCode}_${divisionCode}"
        
        def partnerFunctions = salesArrangement.children().findAll { it.name() == 'PartnerFunctions' }
        
        partnerFunctions.each { partnerFunction ->
            def partyRoleCode = partnerFunction.PartyRoleCode?.text()
            
            if (partyRoleCode == "AP") {
                def partyInternalID = partnerFunction.PartyInternalID?.text()
                
                if (partyInternalID) {
                    salesArrangementList.add([
                        salesUnit: salesUnit,
                        partyInternalID: partyInternalID
                    ])
                }
            }
        }
    }
    
    def xmlBuilder = new StringBuilder()
    
    //Example Data: <B2BUnit><Language>EN</Language><SalesArrangement><SalesUnit>155744_1000_10_00</SalesUnit><PartyInternalID>155956</PartyInternal></SalesArrangement>
    //<SalesArrangement><SalesUnit>155744_3020_10_00</SalesUnit><PartyInternalID>155730</PartyInternalID></SalesArrangement></ContactPersonPartnerFunctions></B2BUnit>
    xmlBuilder.append("<B2BUnit>")
    xmlBuilder.append("<Language>").append(correspondenceLanguageCode).append("</Language>")
    
    if (salesArrangementList.size() > 0)
    {
        xmlBuilder.append("<ContactPersonPartnerFunctions>")
        salesArrangementList.each { salesArrangement ->
            xmlBuilder.append("<SalesArrangement>")
            xmlBuilder.append("<SalesUnit>").append(salesArrangement.salesUnit).append("</SalesUnit>")
            xmlBuilder.append("<PartyInternalID>").append(salesArrangement.partyInternalID).append("</PartyInternalID>")
            xmlBuilder.append("</SalesArrangement>")
        }

        xmlBuilder.append("</ContactPersonPartnerFunctions>")

    }
    
    xmlBuilder.append("</B2BUnit>")
    
    return xmlBuilder.toString()
}

def getNodeValueWithParser(String xml, String nodeName) {
    def parser = new XmlParser()
    def rootNode = parser.parseText(xml)
    
    def node = rootNode.depthFirst().find { it.name() == nodeName }
    return node?.text() ?: ""

}