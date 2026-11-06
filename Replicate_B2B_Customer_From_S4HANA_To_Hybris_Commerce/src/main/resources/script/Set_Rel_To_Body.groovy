import com.sap.gateway.ip.core.customdev.util.Message;


def Message processData(Message message) {
    	
	def messageLog = messageLogFactory.getMessageLog(message);
	def body = message.getBody(java.lang.String) as String;

    def relBody = message.getProperty("BP_Rel_Body");

	message.setBody("<B2BUnitRel>" + relBody + body + "</B2BUnitRel>");

	message.setProperty("BP_Rel_Body", "");	


   return message;
}    
