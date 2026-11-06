import com.sap.gateway.ip.core.customdev.util.Message;
import javax.xml.xpath.XPathFactory;
import javax.xml.parsers.DocumentBuilderFactory

// set streetName, streetNumber, postalCode, town, country, region, fax, phone1, cellPhone, district, pobox, company for extra shipping address mapping
def Message processData(Message message) {
    	
	def messageLog = messageLogFactory.getMessageLog(message);
	def body = message.getBody(java.lang.String) as String;
	def properties = message.getProperties() as Map<String, Object>;
	
	String streetName = processXml(body, "//BusinessPartnerSUITEReplicateRequestMessage/BusinessPartner/AddressInformation/Address/PostalAddress/StreetName");
	message.setProperty("StreetName", streetName);
	
	String streetNumber = processXml(body, "//BusinessPartnerSUITEReplicateRequestMessage/BusinessPartner/AddressInformation/Address/PostalAddress/HouseID");
	message.setProperty("StreetNumber", streetNumber);
	
	String postalCode = processXml(body, "//BusinessPartnerSUITEReplicateRequestMessage/BusinessPartner/AddressInformation/Address/PostalAddress/StreetPostalCode");
	message.setProperty("PostalCode", postalCode);
	
	String town = processXml(body, "//BusinessPartnerSUITEReplicateRequestMessage/BusinessPartner/AddressInformation/Address/PostalAddress/CityName");
	message.setProperty("Town", town);
	
	String country = processXml(body, "//BusinessPartnerSUITEReplicateRequestMessage/BusinessPartner/AddressInformation/Address/PostalAddress/CountryCode");
	message.setProperty("Country", country);
	
	String region = processXml(body, "//BusinessPartnerSUITEReplicateRequestMessage/BusinessPartner/AddressInformation/Address/PostalAddress/RegionCode");
	message.setProperty("Region", region);
	
	String fax = processXml(body, "//BusinessPartnerSUITEReplicateRequestMessage/BusinessPartner/AddressInformation/Address/Facsimile/Number/SubscriberID");
	message.setProperty("Fax", fax);
	
	def root = newSecureXmlSlurper().parseText(body);
    def cellPhoneValue = '';
	def telePhoneValue = '';
    root.BusinessPartnerSUITEReplicateRequestMessage.BusinessPartner.AddressInformation.Address.Telephone.each { tel ->
        def indicator = tel.MobilePhoneNumberIndicator?.text()
        if (indicator && indicator.toString().toLowerCase() == 'true') {
            cellPhoneValue = tel.Number?.SubscriberID?.text()
			message.setProperty('extraCellPhone', cellPhoneValue)
        } else {
			telePhoneValue = tel.Number?.SubscriberID?.text()
			message.setProperty('extraPhone', telePhoneValue)
		}
    }
	
	String district = processXml(body, "//BusinessPartnerSUITEReplicateRequestMessage/BusinessPartner/AddressInformation/Address/PostalAddress/DistrictName");
	message.setProperty("District", district);
	
	String pobox = processXml(body, "//BusinessPartnerSUITEReplicateRequestMessage/BusinessPartner/AddressInformation/Address/PostalAddress/POBoxID");
	message.setProperty("Pobox", pobox);
	
	String company = processXml(body, "//BusinessPartnerSUITEReplicateRequestMessage/BusinessPartner/Common/Organisation/Name/FirstLineName");
	message.setProperty("Company", company);
	
	String sapAddressUUID = processXml(body, "//BusinessPartnerSUITEReplicateRequestMessage/BusinessPartner/AddressInformation/UUID");
	message.setProperty("sapAddressUUID", sapAddressUUID);
    
    
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
