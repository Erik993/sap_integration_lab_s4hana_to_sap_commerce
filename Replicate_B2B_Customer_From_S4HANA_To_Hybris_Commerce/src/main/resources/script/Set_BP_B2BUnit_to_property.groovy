import com.sap.gateway.ip.core.customdev.util.Message;
import javax.xml.xpath.XPathFactory;
import javax.xml.parsers.DocumentBuilderFactory

// set B2BUnit as BP_Paylaod property
def Message processData(Message message) {
    	
	def messageLog = messageLogFactory.getMessageLog(message);
	def body = message.getBody(java.lang.String) as String;

    message.setProperty("BP_PAYLOAD", body);
    message.setProperty("hasB2BUnit", true);


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