import com.sap.gateway.ip.core.customdev.util.Message;


// set telephone number and cellphone number as properties and clean BP_PAYLOAD property
def Message processData(Message message) {
    	
	def messageLog = messageLogFactory.getMessageLog(message);
	def reader = message.getBody(java.io.Reader);
	def properties = message.getProperties() as Map<String, Object>;
    def root = newSecureXmlSlurper().parse(reader);
    
    def phoneNumList = root.BusinessPartnerSUITEReplicateRequestMessage.BusinessPartner.AddressInformation.Address.Telephone.collect{ it.MobilePhoneNumberIndicator.text()  + "|" + it.Number.SubscriberID.text() };

    message.setProperty("PhoneNumList", phoneNumList.join(","));
    
    message.setProperty("BP_PAYLOAD", '');

   return message;
}

def newSecureXmlSlurper(boolean namespaceAware = true) {
    def slurper = new XmlSlurper(false, namespaceAware)
    slurper.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true)
    slurper.setFeature("http://xml.org/sax/features/external-general-entities", false)
    slurper.setFeature("http://xml.org/sax/features/external-parameter-entities", false)
    return slurper
}
