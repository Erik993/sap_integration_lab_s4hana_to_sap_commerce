import com.sap.gateway.ip.core.customdev.util.Message;
import java.util.concurrent.ConcurrentHashMap;

def Message processData(Message message) {
    
    //Initiate HashMap and assign it to Property   
    def hmap1 = new ConcurrentHashMap<String, String>();
    message.setProperty("b2bCustomersXml", new ConcurrentHashMap<String, String>());
    
    def reader = message.getBody(java.io.Reader);
    def xmlParser = newSecureXmlSlurper().parse(reader);

    //Incoming message is a BP request
    if (xmlParser.name().equals('BusinessPartnerSUITEBulkReplicateRequest')) {
        def businessPartners = xmlParser.'**'.findAll { it.BusinessPartner.CategoryCode.text().equals('2') }
    
        businessPartners.each { partner ->
            def internalID = partner.BusinessPartner.InternalID.text();
            if (internalID != null && !internalID.equals('')) {
                //To construct the base batched B2BUnit.
                hmap1.put(internalID,'<B2BUnitXml><BaseUnits></BaseUnits><SalesUnits></SalesUnits></B2BUnitXml>');
            }
        }
        message.setProperty("b2bUnitsXml",hmap1);
    }

    // Set SAP_ApplicationID (first business partner id) so the message is searchable in Monitor.
    // 'count' = count(//BusinessPartnerRelationshipSUITEReplicateRequestMessage), set by the
    // preceding Configuration step; >= 1 means this is a BP relationship message.
    def count = message.getProperty("count")?.toString();
    def isRelationship = count != null && count.isNumber() && count.toDouble() >= 1;

    def ids;
    if (isRelationship) {
        ids = xmlParser.'**'.findAll { it.name().equals('BusinessPartnerInternalID') }
                            .collect { it.text() }
                            .findAll { it != null && !it.equals('') };
    } else {
        ids = xmlParser.'**'.findAll { it.name().equals('BusinessPartner') }
                            .collect { it.InternalID.text() }
                            .findAll { it != null && !it.equals('') };
    }

    // Monitor searches Application Message ID by exact match, so only the first
    // partner id is stored (a concatenated list would not be searchable by a single id).
    if (!ids.isEmpty()) {
        message.setHeader("SAP_ApplicationID", ids[0]);
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
