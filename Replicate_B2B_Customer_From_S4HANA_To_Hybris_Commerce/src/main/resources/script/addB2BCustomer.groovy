import com.sap.gateway.ip.core.customdev.util.Message;

def Message processData(Message message) {
    
	def b2bCustomerNode = message.getBody(java.lang.String) as String;
	def b2bCustomer = newSecureXmlSlurper().parseText(b2bCustomerNode);
	
	def internalID = message.getProperty("currentB2BCustomer").get("currentB2BCustomerId");
	//To set B2BCustomer ContactPerson ID as key.
	def customerContactPersonInternalID = message.getProperty("InternalID");
	def customerContactPersonID = message.getProperty(customerContactPersonInternalID + "_CustomerID");
	
	
	def messageLog = messageLogFactory.getMessageLog(message);

	if(messageLog != null && message.getProperty("enableLog").equals("true")){
	    messageLog.addAttachmentAsString("Log - Single Customer " + internalID + "-" + customerContactPersonID + " After Post Exit",  b2bCustomerNode,
		                                                   "text/xml");
	}

    //Set mapped customer body to property.
	message.getProperties().get("b2bCustomersXml").put(customerContactPersonID, b2bCustomerNode);
	return message;
}

def newSecureXmlSlurper(boolean namespaceAware = true) {
    def slurper = new XmlSlurper(false, namespaceAware)
    slurper.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true)
    slurper.setFeature("http://xml.org/sax/features/external-general-entities", false)
    slurper.setFeature("http://xml.org/sax/features/external-parameter-entities", false)
    return slurper
}
