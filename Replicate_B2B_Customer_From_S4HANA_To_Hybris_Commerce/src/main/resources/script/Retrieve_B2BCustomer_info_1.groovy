import com.sap.gateway.ip.core.customdev.util.Message;
import javax.xml.xpath.XPathFactory;
import javax.xml.parsers.DocumentBuilderFactory
import groovy.util.XmlSlurper


// set customerId, uid, email, groups, language (BP_PAYLOAD) as properties for B2BCustomer mapping and phone1, fax, cellPhone for Contact Person Address mapping
def Message processData(Message message) {
    	
	def body = message.getBody(java.lang.String) as String;
	String BP = message.getProperty("BP_PAYLOAD");
	String BP_REL = message.getProperty("BP_REL_PAYLOAD");

	
	String correspondenceLanguageCode = processXml(BP, "//Language");
	message.setProperty("CorrespondenceLanguageCode", correspondenceLanguageCode);
	String customerId = processXml(BP_REL, "//BusinessPartnerRelationship/ContactPerson/CustomerContactPerson/InternalID");

	
	def salesUnitValue = extractSalesUnitWhenIdsMatch(BP_REL, customerId)


	String parentUnit = processXml(BP_REL, "//BusinessPartnerInternalID");
	if (message.getProperty("assignContactPartnerFunctionToSalesUnit").equals("true") && salesUnitValue)
	{
	    message.setProperty("DefaultB2BUnit", salesUnitValue);
	}
	else
	{
		message.setProperty("DefaultB2BUnit", parentUnit);
	}
    
    String internalId = processXml(BP_REL, "//BusinessPartnerRelationship/RelationshipBusinessPartnerInternalID");
    message.setProperty("InternalID", internalId);
    
    
    String contactPersonLanguage = processXml(body, "//BusinessPartner/AddressInformation/Address/CommunicationPreference/CorrespondenceLanguageCode");
    message.setProperty(internalId + "_" + "LanguageExist", false);
    if (contactPersonLanguage != null && contactPersonLanguage !='')
    {
        message.setProperty(internalId + "_" + "LanguageExist", true);
        message.setProperty(internalId + "_" + "Language", contactPersonLanguage);
    }
    
    // B2BCustomer mapping
    message.setProperty(internalId + "_CustomerID", customerId);
    
    String uid = processXml(BP_REL, "//BusinessPartnerRelationship/ContactPerson/WorkplaceAddressInformation/Address/Email/URI");
    message.setProperty(internalId + "_uid", uid);
    
    String email = processXml(BP_REL, "//BusinessPartnerRelationship/ContactPerson/WorkplaceAddressInformation/Address/Email/URI");
    message.setProperty(internalId + "_email", email);
    
    String groups = processXml(BP_REL, "//BusinessPartnerRelationship/ContactPerson/BusinessPartnerFunctionTypeCode");
    message.setProperty(internalId + "_groups", groups);
    
    
    // Contact Person Address mapping
    // set telephone number and cellphone number as properties
    def root = newSecureXmlSlurper().parseText(BP_REL);
    
    def phoneNumList = root.BusinessPartnerRelationshipSUITEReplicateRequestMessage.BusinessPartnerRelationship.ContactPerson.WorkplaceAddressInformation.Address.Telephone.collect{ it.MobilePhoneNumberIndicator.text()  + "|" + it.Number.SubscriberID.text() };
    message.setProperty(internalId + "_PhoneNumList", phoneNumList.join(","));
    
    String fax = processXml(BP_REL, "//BusinessPartnerRelationship/ContactPerson/WorkplaceAddressInformation/Address/Facsimile/Number/SubscriberID");
    message.setProperty(internalId + "_fax", fax);
    
    message.setProperty("BP_REL_PAYLOAD", '');
    
   return message;
}    

def extractSalesUnitWhenIdsMatch(String xml, String internalId) {
    def parser = newSecureXmlSlurper()
    def rootNode = parser.parseText(xml)
    
    def normalizedInternalId = internalId.replaceFirst('^0+', '')
    
    // Find all SalesArrangement elements
    def salesArrangements = rootNode.depthFirst().findAll { 
        it.name() == 'SalesArrangement' 
    }
    
    if (!internalId || !salesArrangements) {
        return null
    }
    
    // Iterate through each SalesArrangement
    for (def salesArrangement : salesArrangements) {
        def partyInternalId = salesArrangement.PartyInternalID?.text()
        def salesUnit = salesArrangement.SalesUnit?.text()
        
        if (partyInternalId && salesUnit) {
            def normalizedPartyId = partyInternalId.replaceFirst('^0+', '')
            
            if (normalizedPartyId == normalizedInternalId) {
                return salesUnit
            }
        }
    }
    
    return null
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
   def inputStream = new ByteArrayInputStream(xml.getBytes('UTF-8'))
   def records     = builder.parse(inputStream).documentElement
  
   xpath.evaluate( xpathQuery, records )
}

def newSecureXmlSlurper(boolean namespaceAware = true) {
    def slurper = new XmlSlurper(false, namespaceAware)
    slurper.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true)
    slurper.setFeature("http://xml.org/sax/features/external-general-entities", false)
    slurper.setFeature("http://xml.org/sax/features/external-parameter-entities", false)
    return slurper
}
