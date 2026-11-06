import com.sap.gateway.ip.core.customdev.util.Message;
import groovy.xml.StreamingMarkupBuilder;
import java.util.concurrent.ConcurrentHashMap;
import groovy.xml.XmlUtil

def Message processData(Message message) {
    def b2bUnit_reader = message.getBody(java.io.Reader);
     
    def builder = new StreamingMarkupBuilder()
    def b2bUnit_root = newSecureXmlSlurper().parse(b2bUnit_reader);
    def internalId = b2bUnit_root.B2BUnit.uid.text();	

    def hmap1 = new ConcurrentHashMap<String, String>();
    hmap1.put("baseUnitId", internalId);
    message.setProperty("currentBaseUnit",hmap1);
    
    if(!isMatchedOrLaterCommerceVersion(message,"1811"))
        b2bUnit_root.B2BUnit.addresses.Address.sapAddressUUID.findAll{it!='' || it.text()==''}*.replaceNode {};
    message.setBody(XmlUtil.serialize(b2bUnit_root));
    return message;
}

def boolean isMatchedOrLaterCommerceVersion(Message message, String attributeVersionNumber){
    def commerceVersion = message.getProperty("COMMERCE_VERSION_NUMBER");
    if(commerceVersion == null) 
        return false;
    return commerceVersion.replace(".","").toUpperCase().compareTo(attributeVersionNumber.toUpperCase())>=0;
}

def newSecureXmlSlurper(boolean namespaceAware = true) {
    def slurper = new XmlSlurper(false, namespaceAware)
    slurper.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true)
    slurper.setFeature("http://xml.org/sax/features/external-general-entities", false)
    slurper.setFeature("http://xml.org/sax/features/external-parameter-entities", false)
    return slurper
}
