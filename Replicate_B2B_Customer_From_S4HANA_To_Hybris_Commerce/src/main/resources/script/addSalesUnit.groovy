import com.sap.gateway.ip.core.customdev.util.Message;

def Message processData(Message message) {
    
	def salesUnitNode = message.getBody(java.lang.String) as String;

	def salesUnit = newSecureXmlSlurper().parseText(salesUnitNode); 

	//Get base unit id from property(customer ticket)
	def baseUnitId = message.getProperties().get("currentBaseUnit").get("baseUnitId");

	
	def b2bUnitXml = message.getProperties().get("b2bUnitsXml").get(baseUnitId);
	def root = newSecureXmlSlurper().parseText(b2bUnitXml);

    if(root.SalesUnits != null) {
        
		root.SalesUnits.appendNode(salesUnit);
	}
	
   	def xmlStr = new groovy.xml.StreamingMarkupBuilder().bind {
        mkp.yield root
    }.toString();    
    

	def messageLog = messageLogFactory.getMessageLog(message);

	if(messageLog != null && message.getProperty("enableLog").equals("true")){
	    messageLog.addAttachmentAsString("Log - Single sales unit " + salesUnit.B2BUnit.uid.text() + " After Post Exits",  salesUnitNode,
		                                                   "text/xml");
	}
	//Append sale unit node to property b2bUnitsXml
	message.getProperties().get("b2bUnitsXml").put(baseUnitId, xmlStr);


	return message;
}


def newSecureXmlSlurper(boolean namespaceAware = true) {
    def slurper = new XmlSlurper(false, namespaceAware)
    slurper.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true)
    slurper.setFeature("http://xml.org/sax/features/external-general-entities", false)
    slurper.setFeature("http://xml.org/sax/features/external-parameter-entities", false)
    return slurper
}
