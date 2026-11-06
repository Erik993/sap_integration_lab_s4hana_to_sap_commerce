import com.sap.gateway.ip.core.customdev.util.Message;


def Message processData(Message message) {
    def messageLog = messageLogFactory.getMessageLog(message);

	def baseUnitNode = message.getBody(java.lang.String) as String;

	def baseUnit = newSecureXmlSlurper().parseText(baseUnitNode); 
	def internalID = baseUnit.B2BUnit.uid.text();
	//Get base unit id from property(customer ticket)
	def unitId =  message.getProperties().get("currentBaseUnit").get("baseUnitId");

	def wholeUnitXml =  message.getProperties().get("b2bUnitsXml");
	def b2bUnitXml = wholeUnitXml.get(unitId);
    
	def root = newSecureXmlSlurper().parseText(b2bUnitXml);

    if(root.BaseUnits != null) {
		root.BaseUnits.appendNode( baseUnit );
	}
   	def xmlStr = new groovy.xml.StreamingMarkupBuilder().bind {
        mkp.yield root
    }.toString();


	if(messageLog != null && message.getProperty("enableLog").equals("true")){
	    messageLog.addAttachmentAsString("Log - Single base unit " + internalID + " After Post Exits",  baseUnitNode,
		                                                   "text/xml");
	}
	
	//Add base unit node to property b2bUnitsXml
	message.getProperties().get("b2bUnitsXml").put(unitId, xmlStr);

	return message;
}

def newSecureXmlSlurper(boolean namespaceAware = true) {
    def slurper = new XmlSlurper(false, namespaceAware)
    slurper.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true)
    slurper.setFeature("http://xml.org/sax/features/external-general-entities", false)
    slurper.setFeature("http://xml.org/sax/features/external-parameter-entities", false)
    return slurper
}
