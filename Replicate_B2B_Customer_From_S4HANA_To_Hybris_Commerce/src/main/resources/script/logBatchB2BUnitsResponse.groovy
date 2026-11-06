import com.sap.gateway.ip.core.customdev.util.Message;

def Message processData(Message message) {
	
	def body = message.getBody(java.lang.String) as String;
	
	def messageLog = messageLogFactory.getMessageLog(message);
	
	def properties = message.getProperties() as Map<String, Object>;
	
    if(messageLog != null && properties.get("enableLog").equals("true")){ 
		messageLog.addAttachmentAsString("B2BUnit Batch Response", body, "text/xml");
	}
	return message;
}