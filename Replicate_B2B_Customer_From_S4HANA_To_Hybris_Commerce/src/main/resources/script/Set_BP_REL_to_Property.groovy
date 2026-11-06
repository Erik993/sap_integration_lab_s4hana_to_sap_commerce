import com.sap.gateway.ip.core.customdev.util.Message;

// set BP_REL as property
def Message processData(Message message) {
    	
	def messageLog = messageLogFactory.getMessageLog(message);
	def body = message.getBody(java.lang.String) as String;
	def properties = message.getProperties() as Map<String, Object>;
	
    
    message.setProperty("BP_REL_PAYLOAD", body);

   return message;
}    