import com.sap.gateway.ip.core.customdev.util.Message;
import javax.xml.xpath.XPathFactory;
import javax.xml.parsers.DocumentBuilderFactory

// set streetName, streetNumber, postalCode, town, country, region, fax, phone1, cellPhone, district, pobox, company for extra shipping address mapping
def Message processData(Message message) {
    	
	def body = message.getBody(java.lang.String) as String;

	String streetName = processXml(body, "//A_BusinessPartner/A_BusinessPartnerType/to_BusinessPartnerAddress/A_BusinessPartnerAddressType/StreetName");
	message.setProperty("StreetName", streetName);
	
	String streetNumber = processXml(body, "//A_BusinessPartner/A_BusinessPartnerType/to_BusinessPartnerAddress/A_BusinessPartnerAddressType/HouseNumber");
	message.setProperty("StreetNumber", streetNumber);
	
	String postalCode = processXml(body,  "//A_BusinessPartner/A_BusinessPartnerType/to_BusinessPartnerAddress/A_BusinessPartnerAddressType/PostalCode");
	message.setProperty("PostalCode", postalCode);
	
	String town = processXml(body, "//A_BusinessPartner/A_BusinessPartnerType/to_BusinessPartnerAddress/A_BusinessPartnerAddressType/CityName");
	message.setProperty("Town", town);
	
	String country = processXml(body, "//A_BusinessPartner/A_BusinessPartnerType/to_BusinessPartnerAddress/A_BusinessPartnerAddressType/Country");
	message.setProperty("Country", country);
	
	String company = processXml(body, "//A_BusinessPartner/A_BusinessPartnerType/to_Customer/A_CustomerType/CustomerName");
	message.setProperty("Company", company);
	
	String region = processXml(body, "//A_BusinessPartner/A_BusinessPartnerType/to_BusinessPartnerAddress/A_BusinessPartnerAddressType/Region");
	message.setProperty("Region", region);
	
	String sapAddressUUID = processXml(body, "//A_BusinessPartner/A_BusinessPartnerType/to_BusinessPartnerAddress/A_BusinessPartnerAddressType/AddressUUID");
	message.setProperty("sapAddressUUID", sapAddressUUID);
	
	String fax = processXml(body, "//A_BusinessPartner/A_BusinessPartnerType/to_BusinessPartnerAddress/A_BusinessPartnerAddressType/to_FaxNumber/A_AddressFaxNumberType/FaxNumber");
	message.setProperty("Fax", fax);
	
	def root = newSecureXmlSlurper().parseText(body);
    def phoneNumList = root.A_BusinessPartnerAddress.A_BusinessPartnerAddressType.to_PhoneNumber.A_AddressPhoneNumberType.collect{ it.IsDefaultPhoneNumber.text()  + "|" + it.PhoneNumber.SubscriberID.text() };
    message.setProperty("PhoneNumList", phoneNumList.join(","));
	
	String district = processXml(body, "//A_BusinessPartner/A_BusinessPartnerType/to_BusinessPartnerAddress/A_BusinessPartnerAddressType/District");
	message.setProperty("District", district);
	
	String pobox = processXml(body, "//A_BusinessPartner/A_BusinessPartnerType/to_BusinessPartnerAddress/A_BusinessPartnerAddressType/POBox");
	message.setProperty("Pobox", pobox);
	
	String extraCellPhone = processXml(body, "//A_BusinessPartner/A_BusinessPartnerType/to_BusinessPartnerAddress/A_BusinessPartnerAddressType/to_MobilePhoneNumber/A_AddressPhoneNumberType/PhoneNumber");
	message.setProperty("extraCellPhone", extraCellPhone);
	
	String extraPhone = processXml(body, "//A_BusinessPartner/A_BusinessPartnerType/to_BusinessPartnerAddress/A_BusinessPartnerAddressType/to_PhoneNumber/A_AddressPhoneNumberType/PhoneNumber");
	message.setProperty("extraPhone", extraPhone);
    
    
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
