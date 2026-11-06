import com.sap.gateway.ip.core.customdev.util.Message
import groovy.util.XmlParser



// set B2BUnit + BP_REL datastore name, entry ID to write the two payload, set WRITE_ACTION to "True"
def Message processData(Message message) {
    	
    def body = message.getBody(java.lang.String) as String
    message.setProperty("BP_REL_PAYLOAD", body);


	String BP_Payload = message.getProperty("BP_PAYLOAD");
	
	String B2BUnit_REL_ID = getNodeValueWithParser(body, "RelationshipBusinessPartnerInternalID");
	
	message.setHeader("DATASTORE_NAME", "BP_B2BUnit_REL");
	message.setHeader("ENTRY_ID", B2BUnit_REL_ID);
	message.setHeader("ACTION", "WRITE");
	
	if (BP_Payload != null && BP_Payload != "")
	{
	    message.setBody("<B2BUnitRel>" + body + BP_Payload + "</B2BUnitRel>");

	} 
	else 
	{
	    message.setBody("<B2BUnitRel>" + body + "</B2BUnitRel>");
	    
	}
    
    
   return message;
}    

def getNodeValueWithParser(String xml, String nodeName) {
    def parser = new XmlParser()
    def rootNode = parser.parseText(xml)
    
    def node = rootNode.depthFirst().find { it.name() == nodeName }
    return node?.text() ?: ""

}
