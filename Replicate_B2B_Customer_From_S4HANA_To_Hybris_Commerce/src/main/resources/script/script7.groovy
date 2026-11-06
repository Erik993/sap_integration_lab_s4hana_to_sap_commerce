import groovy.xml.StreamingMarkupBuilder;
import com.sap.gateway.ip.core.customdev.util.Message;
import groovy.xml.XmlUtil;

def Message processData(Message message) {
    
	def baseUnitNode = message.getBody(java.lang.String) as String;

    def b2bUnitXml = message.getProperty("b2bUnitXml") as String;
    
	def root = newSecureXmlSlurper(false).parseText(b2bUnitXml);

    if(root.BaseUnits != null) {
		root.BaseUnits.appendNode( baseUnitNode );
	}
	String xmlStr = XmlUtil.serialize( new StreamingMarkupBuilder().bind { mkp.yield root } );
	message.setProperty("b2bUnitXml", xmlStr);
	return message;
}

def newSecureXmlSlurper(boolean namespaceAware = true) {
    def slurper = new XmlSlurper(false, namespaceAware)
    slurper.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true)
    slurper.setFeature("http://xml.org/sax/features/external-general-entities", false)
    slurper.setFeature("http://xml.org/sax/features/external-parameter-entities", false)
    return slurper
}
