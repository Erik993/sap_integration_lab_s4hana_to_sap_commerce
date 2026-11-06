import com.sap.gateway.ip.core.customdev.util.Message;

def Message processData(Message message) {	
    def logEnabled = message.getProperty("enableLog") as String;
    if(logEnabled == "true") {
    	def body = message.getBody(java.lang.String) as String;
    	
	    def headers = message.getHeaders() as Map<String, Object>;
	    def properties = message.getProperties() as Map<String, Object>;
	
	    def propertiesAsString ="\n";
	    properties.each{ it -> propertiesAsString = propertiesAsString + "${it}" + "\n" };
	
	    def headersAsString ="\n";
	    headers.each{ it -> headersAsString = headersAsString + "${it}" + "\n" };
	

	    def messageLog = messageLogFactory.getMessageLog(message);
        if(messageLog != null && properties.get("enableLog") == "true"){
		    messageLog.addAttachmentAsString("Log - Mapped B2BCustomer",   "\n Properties \n ----------   \n" + propertiesAsString +
                                                                "\n Headers \n ----------   \n" + headersAsString +
		                                                        "\n Body \n ----------  \n\n" + body,
		                                                        "text/xml");
	    }
    }
	return message;
}