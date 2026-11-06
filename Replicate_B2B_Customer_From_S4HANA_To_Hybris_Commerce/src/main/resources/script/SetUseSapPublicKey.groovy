import com.sap.gateway.ip.core.customdev.util.Message;

def Message processData(Message message) {

  def reader = message.getBody(java.io.Reader);
  def metadataPayload = newSecureXmlSlurper().parse(reader);
  

  def sapPublicKeyNode = metadataPayload.DataServices.Schema.EntityType.find { it.@Name == 'Address' }.Property.find { it.@Name == 'sapPublicKey' }
   if (sapPublicKeyNode) {
        message.setProperty("sapPublicKeyEnabled","true"); 
    } else {
        message.setProperty("sapPublicKeyEnabled","false"); 
    }

  return message;
}

def newSecureXmlSlurper(boolean namespaceAware = true) {
    def slurper = new XmlSlurper(false, namespaceAware)
    slurper.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true)
    slurper.setFeature("http://xml.org/sax/features/external-general-entities", false)
    slurper.setFeature("http://xml.org/sax/features/external-parameter-entities", false)
    return slurper
}
