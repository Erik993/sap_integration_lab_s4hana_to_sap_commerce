import com.sap.gateway.ip.core.customdev.util.Message;

// Set BP to Body
def Message processData(Message message) {
    	
	def messageLog = messageLogFactory.getMessageLog(message);
	def body = message.getBody(java.lang.String) as String;
	def properties = message.getProperties() as Map<String, Object>;
	
	String BP_Body = message.getProperty("BP_PAYLOAD");
	message.setBody(BP_Body);
	
    
   return message;
}    