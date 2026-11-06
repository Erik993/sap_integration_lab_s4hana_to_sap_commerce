/*
 The integration developer needs to create the method processData 
 This method takes Message object of package com.sap.gateway.ip.core.customdev.util 
which includes helper methods useful for the content developer:
The methods available are:
    public java.lang.Object getBody()
    public void setBody(java.lang.Object exchangeBody)
    public java.util.Map<java.lang.String,java.lang.Object> getHeaders()
    public void setHeaders(java.util.Map<java.lang.String,java.lang.Object> exchangeHeaders)
    public void setHeader(java.lang.String name, java.lang.Object value)
    public java.util.Map<java.lang.String,java.lang.Object> getProperties()
    public void setProperties(java.util.Map<java.lang.String,java.lang.Object> exchangeProperties) 
       public void setProperty(java.lang.String name, java.lang.Object value)
 */
import com.sap.gateway.ip.core.customdev.util.Message;
import java.util.concurrent.ConcurrentHashMap;
import groovy.xml.StreamingMarkupBuilder
import groovy.xml.XmlUtil

def Message processData(Message message) {
    def b2bCustomer_reader = message.getBody(java.io.Reader) ;
    
    def builder = new StreamingMarkupBuilder()
    def b2bCustomer_root = newSecureXmlSlurper().parse(b2bCustomer_reader);
    def b2bCustomerId = b2bCustomer_root.B2BCustomer.uid.text();
    
    def hmap1 = new ConcurrentHashMap<String, String>();
    hmap1.put("currentB2BCustomerId", b2bCustomerId);
    message.setProperty("currentB2BCustomer", hmap1);

    if(!isMatchedOrLaterCommerceVersion(message, "1905-CEP"))
        b2bCustomer_root.B2BCustomer.sapBusinessPartnerID.findAll{it!='' || it.text()==''}*.replaceNode {};
    message.setBody(XmlUtil.serialize(b2bCustomer_root));
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
