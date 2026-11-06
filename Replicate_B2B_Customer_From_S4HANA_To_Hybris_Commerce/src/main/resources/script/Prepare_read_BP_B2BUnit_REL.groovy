import com.sap.gateway.ip.core.customdev.util.Message;
import javax.xml.xpath.XPathFactory;
import javax.xml.parsers.DocumentBuilderFactory

def Message processData(Message message) {
    	
	def messageLog = messageLogFactory.getMessageLog(message);
	def body = message.getBody(java.lang.String) as String;

	String BP_B2BUnit_REL_ENTRY_ID = processXml(body, "//InternalID");
	message.setHeader("DATASTORE_NAME", "BP_B2BUnit_REL");
	message.setHeader("ENTRY_ID", BP_B2BUnit_REL_ENTRY_ID);
	message.setHeader("ACTION", "READ");
	
	message.setProperty("BP_PERSON", body);	
    
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