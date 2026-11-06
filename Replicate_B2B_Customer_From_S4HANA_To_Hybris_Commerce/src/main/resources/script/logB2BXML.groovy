import com.sap.gateway.ip.core.customdev.util.Message;

def Message processData(Message message) {
    
    def b2bUnitXml = message.getProperties().get("b2bUnitsXml") as String;
    def b2bCustomerXml = message.getProperties().get("b2bCustomersXml") as String;

    
	def messageLog = messageLogFactory.getMessageLog(message);

	if(messageLog != null && message.getProperty("enableLog") == "true"){
	    messageLog.addAttachmentAsString("Log - b2bUnitXml at end",  b2bUnitXml,
		                                                   "text/xml");
	    messageLog.addAttachmentAsString("Log - b2bCustomersXml at end", b2bCustomerXml,
		                                                   "text/xml");
	}
	return message;
}