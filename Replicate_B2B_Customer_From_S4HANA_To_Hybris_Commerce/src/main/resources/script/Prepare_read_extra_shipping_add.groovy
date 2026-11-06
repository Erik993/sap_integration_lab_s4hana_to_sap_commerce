import com.sap.gateway.ip.core.customdev.util.Message;
import javax.xml.xpath.XPathFactory;
import javax.xml.parsers.DocumentBuilderFactory

// set extra ship-to party B2BUnit addresss datastore name, entry ID to find extra ship-to party B2BUnit, set WRITE_ACTION to "False", set B2BUnit as a property
def Message processData(Message message) {
    	
	def messageLog = messageLogFactory.getMessageLog(message);
	def body = message.getBody(java.lang.String) as String;
	def properties = message.getProperties() as Map<String, Object>;
	
	String B2BUnit_ADDRESS_DATASTORE_ENTRY_ID = processXml(body, "//BusinessPartnerSUITEReplicateRequestMessage/BusinessPartner/Customer/SalesArrangement/PartnerFunctions/PartyInternalID");
	
	message.setHeader("ENTRY_ID", B2BUnit_ADDRESS_DATASTORE_ENTRY_ID);
	message.setProperty("extraCustomerId", B2BUnit_ADDRESS_DATASTORE_ENTRY_ID);
    message.setHeader("DATASTORE_NAME", "BP_B2BUnit_Address");
    message.setHeader("ACTION", "READ");

    message.setProperty("BP_B2BUnit_Paylaod", body);

   return message;
}    

def processXml( String xml, String xpathQuery ) {
    
   def xpath = XPathFactory.newInstance().newXPath()
   def dbf         = DocumentBuilderFactory.newInstance()
   dbf.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true)
   dbf.setFeature("http://xml.org/sax/features/external-general-entities", false)
   dbf.setFeature("http://xml.org/sax/features/external-parameter-entities", false)
   dbf.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false)
   dbf.setXIncludeAware(false)
   dbf.setExpandEntityReferences(false)
   def builder     = dbf.newDocumentBuilder()
   def inputStream = new ByteArrayInputStream( xml.bytes )
   def records     = builder.parse(inputStream).documentElement
  
   xpath.evaluate( xpathQuery, records )
}