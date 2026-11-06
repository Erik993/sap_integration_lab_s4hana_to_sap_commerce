import com.sap.gateway.ip.core.customdev.util.Message;

def Message processData(Message message) {
    def properties = message.getProperties();
    def b2bCustomersXml = properties.get("b2bCustomersXml");
  	def messageLog = messageLogFactory.getMessageLog(message);

    def customPostHook = message.getProperty("B2BCustomerCustomPostHook") as String;
    def customPreHook = message.getProperty("B2BCustomerCustomPreHook") as String;
    def headers = "";
    if(!customPostHook.equals("") || !customPreHook.equals("")){
        def customPostHookData = "";
        def customPreHookData = "";
        if(!customPostHook.equals("")){
            customPostHookData = "<header><headerName>Post-Persist-Hook</headerName><headerValue>${customPostHook}</headerValue></header>";
        }
        if(!customPreHook.equals("")){
            customPreHookData = "<header><headerName>Pre-Persist-Hook</headerName><headerValue>${customPreHook}</headerValue></header>";
        }
        headers = "<headers>${customPostHookData}${customPreHookData}</headers>";
    }
    
    
	def batchedCustomerData = "";
    
    //Append single batched B2BCustomer
    for (entry in b2bCustomersXml) {
        if (entry.value != null && !entry.value.equals("")) {

            def xmlStr = new groovy.xml.StreamingMarkupBuilder().bind {
                mkp.yield entry.value
            }.toString();
            def unescapedXmlString = unescapeXml(xmlStr);
            def singleBatchCustomerData = "<batchChangeSet><batchChangeSetPart><method>POST</method>${headers}${unescapedXmlString}</batchChangeSetPart></batchChangeSet>";
            batchedCustomerData = batchedCustomerData + singleBatchCustomerData;
            
        }
    }
    
    if (!batchedCustomerData.equals("")) {
        batchedCustomerData = "<batchParts>${batchedCustomerData}</batchParts>";
        message.setBody(batchedCustomerData);
        message.setProperty("b2bCustomerIsEmpty", false);
        if(messageLog != null && message.getProperty("enableLog").equals("true")){
	       messageLog.addAttachmentAsString("Log - Batched B2BCustomers",  batchedCustomerData, "text/xml");
	    }
    } else {
        message.setProperty("b2bCustomerIsEmpty", true);

    }
    
    return message;
}

def String unescapeXml(String input) {
        return input
                .replaceAll("&lt;", "<")
                .replaceAll("&gt;", ">")
                .replaceAll("&amp;", "&")
                .replaceAll("&quot;", "\"")
                .replaceAll("&apos;", "'");
}