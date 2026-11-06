import com.sap.gateway.ip.core.customdev.util.Message;

def Message processData(Message message) {
    	
	def messageLog = messageLogFactory.getMessageLog(message);
	def body = message.getBody(java.lang.String) as String;
	def properties = message.getProperties() as Map<String, Object>;
    
    String BP_B2BUnit_Body = message.getProperty("BP_B2BUnit_Paylaod");
    message.setBody(BP_B2BUnit_Body);
    message.setProperty("BP_B2BUnit_Paylaod", "");
    
    
   return message;
}    