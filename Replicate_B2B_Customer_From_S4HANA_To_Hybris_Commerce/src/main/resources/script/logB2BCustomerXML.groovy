import com.sap.gateway.ip.core.customdev.util.Message;

def Message processData(Message message) {
    
    def b2bCustomerXml = message.getProperties().get("b2bCustomersXml") as String;

    
	def messageLog = messageLogFactory.getMessageLog(message);

	if(messageLog != null && message.getProperty("enableLog") == "true"){

	    messageLog.addAttachmentAsString("Log - b2bCustomersXml at end", b2bCustomerXml,
		                                                   "text/xml");
	}
	return message;
}