import com.sap.gateway.ip.core.customdev.util.Message;

def Message processData(Message message) {

	def body = message.getBody(java.lang.String) as String;
	def properties = message.getProperties() as Map<String, Object>;

	def messageLog = messageLogFactory.getMessageLog(message);
	
    if(messageLog != null && properties.get("enableLog") == "true"){
	    messageLog.addAttachmentAsString("Default exception process", body, "text/xml");
	}

    return message;
}