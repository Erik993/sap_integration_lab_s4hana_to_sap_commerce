/* Refer the link below to learn more about the use cases of script.
https://help.sap.com/viewer/368c481cd6954bdfa5d0435479fd4eaf/Cloud/en/148851bf8192412cba1f9d2c17f4bd25.html

If you want to know more about the SCRIPT APIs, refer the link below
https://help.sap.com/doc/a56f52e1a58e4e2bac7f7adbf45b2e26/Cloud/en/index.html */
import com.sap.gateway.ip.core.customdev.util.Message;
def Message processData(Message message) {
    //Body

    def inputString = '''
<batchParts>
  <batchChangeSet>
    <batchChangeSetPart>
      <method>POST</method>
      <B2BUnits>
        <B2BUnit>
          <integrationKey>Constant</integrationKey>
          <description>test-155744 - 40055</description>
          <name>test-155744 - 40055</name>
          <buyer>true</buyer>
          <locName>test-155744 - 40055</locName>
          <active>true</active>
          <uid>155744</uid>
          <addresses>
            <Address>
              <integrationKey>
              </integrationKey>
              <sapCustomerID>40055</sapCustomerID>
              <duplicate>false</duplicate>
              <company>test-155744 - 40055</company>
              <district>LOS ANGELES 1</district>
              <town>Beverly Hills address sync 1</town>
              <shippingAddress>false</shippingAddress>
              <billingAddress>false</billingAddress>
              <streetnumber>87656</streetnumber>
              <postalcode>90211</postalcode>
              <streetname>Sunset 5</streetname>
              <sapAddressUsage>DE</sapAddressUsage>
              <sapAddressUsageCounter>000</sapAddressUsageCounter>
              <phone1>50599912</phone1>
              <publicKey>155744|155744|KNA1|DE</publicKey>
              <cellphone>50488810</cellphone>
              <fax>678990</fax>
              <region>
                <Region>
                  <integrationKey>
                  </integrationKey>
                  <isocode>US-CA</isocode>
                  <country>
                    <Country>
                      <integrationKey>
                      </integrationKey>
                      <isocode>US</isocode>
                    </Country>
                  </country>
                </Region>
              </region>
              <country>
                <Country>
                  <integrationKey>
                  </integrationKey>
                  <isocode>US</isocode>
                </Country>
              </country>
            </Address>
          </addresses>
        </B2BUnit>
      </B2BUnits>
    </batchChangeSetPart>
  </batchChangeSet>
  <batchChangeSet>
    <batchChangeSetPart>
      <method>POST</method>
      <B2BUnits>
        <B2BUnit>
          <integrationKey>
          </integrationKey>
          <description>test-155744 - 40055</description>
          <name>test-155744 - 40055</name>
          <buyer>true</buyer>
          <locName>test-155744 - 40055</locName>
          <active>true</active>
          <uid>155744_1000_10_00</uid>
          <userDiscountGroup>
            <UserDiscountGroup>
              <integrationKey>
              </integrationKey>
              <code>01</code>
            </UserDiscountGroup>
          </userDiscountGroup>
          <addresses>
            <Address>
              <integrationKey>
              </integrationKey>
              <sapCustomerID>40055</sapCustomerID>
              <duplicate>false</duplicate>
              <company>test-155744 - 40055</company>
              <district>LOS ANGELES 1</district>
              <town>Beverly Hills address sync 1</town>
              <shippingAddress>false</shippingAddress>
              <billingAddress>true</billingAddress>
              <streetnumber>87656</streetnumber>
              <postalcode>90211</postalcode>
              <streetname>Sunset 5</streetname>
              <sapAddressUsage>RE</sapAddressUsage>
              <sapAddressUsageCounter>000</sapAddressUsageCounter>
              <phone1>50599912</phone1>
              <publicKey>155744_1000_10_00|40055|KNA1|RE</publicKey>
              <cellphone>50488810</cellphone>
              <fax>678990</fax>
              <region>
                <Region>
                  <integrationKey>
                  </integrationKey>
                  <isocode>US-CA</isocode>
                  <country>
                    <Country>
                      <integrationKey>
                      </integrationKey>
                      <isocode>US</isocode>
                    </Country>
                  </country>
                </Region>
              </region>
              <country>
                <Country>
                  <integrationKey>dummy</integrationKey>
                  <isocode>US</isocode>
                </Country>
              </country>
            </Address>
          </addresses>
          <userPriceGroup>
            <UserPriceGroup>
              <integrationKey>
              </integrationKey>
              <code>01</code>
            </UserPriceGroup>
          </userPriceGroup>
          <groups>
            <UserGroup>
              <integrationKey>
              </integrationKey>
              <uid>155744</uid>
            </UserGroup>
          </groups>
        </B2BUnit>
      </B2BUnits>
    </batchChangeSetPart>
  </batchChangeSet>
  <batchChangeSet>
    <batchChangeSetPart>
      <method>POST</method>
      <B2BUnits>
        <B2BUnit>
          <integrationKey>
          </integrationKey>
          <description>test-155744 - 40055</description>
          <name>test-155744 - 40055</name>
          <buyer>true</buyer>
          <locName>test-155744 - 40055</locName>
          <active>true</active>
          <uid>155744_1000_10_00</uid>
          <userDiscountGroup>
            <UserDiscountGroup>
              <integrationKey>
              </integrationKey>
              <code>01</code>
            </UserDiscountGroup>
          </userDiscountGroup>
          <addresses>
            <Address>
              <integrationKey>
              </integrationKey>
              <sapCustomerID>40055</sapCustomerID>
              <duplicate>false</duplicate>
              <company>test-155744 - 40055</company>
              <district>LOS ANGELES 1</district>
              <town>Beverly Hills address sync 1</town>
              <shippingAddress>true</shippingAddress>
              <billingAddress>false</billingAddress>
              <streetnumber>87656</streetnumber>
              <postalcode>90211</postalcode>
              <streetname>Sunset 5</streetname>
              <sapAddressUsage>WE</sapAddressUsage>
              <sapAddressUsageCounter>000</sapAddressUsageCounter>
              <phone1>50599912</phone1>
              <publicKey>155744_1000_10_00|40055|KNA1|WE</publicKey>
              <cellphone>50488810</cellphone>
              <fax>678990</fax>
              <region>
                <Region>
                  <integrationKey>
                  </integrationKey>
                  <isocode>US-CA</isocode>
                  <country>
                    <Country>
                      <integrationKey>
                      </integrationKey>
                      <isocode>US</isocode>
                    </Country>
                  </country>
                </Region>
              </region>
              <country>
                <Country>
                  <integrationKey>dummy</integrationKey>
                  <isocode>US</isocode>
                </Country>
              </country>
            </Address>
          </addresses>
          <userPriceGroup>
            <UserPriceGroup>
              <integrationKey>
              </integrationKey>
              <code>01</code>
            </UserPriceGroup>
          </userPriceGroup>
          <groups>
            <UserGroup>
              <integrationKey>
              </integrationKey>
              <uid>155744</uid>
            </UserGroup>
          </groups>
        </B2BUnit>
      </B2BUnits>
    </batchChangeSetPart>
  </batchChangeSet>
  <batchChangeSet>
    <batchChangeSetPart>
      <method>POST</method>
      <B2BUnits>
        <B2BUnit>
          <integrationKey>
          </integrationKey>
          <description>test-155744 - 40055</description>
          <name>test-155744 - 40055</name>
          <buyer>true</buyer>
          <locName>test-155744 - 40055</locName>
          <active>true</active>
          <uid>155744_3020_30_00</uid>
          <userDiscountGroup>
            <UserDiscountGroup>
              <integrationKey>
              </integrationKey>
              <code>01</code>
            </UserDiscountGroup>
          </userDiscountGroup>
          <addresses>
            <Address>
              <integrationKey>
              </integrationKey>
              <sapCustomerID>40055</sapCustomerID>
              <duplicate>false</duplicate>
              <company>test-155744 - 40055</company>
              <district>LOS ANGELES 1</district>
              <town>Beverly Hills address sync 1</town>
              <shippingAddress>false</shippingAddress>
              <billingAddress>true</billingAddress>
              <streetnumber>87656</streetnumber>
              <postalcode>90211</postalcode>
              <streetname>Sunset 5</streetname>
              <sapAddressUsage>RE</sapAddressUsage>
              <sapAddressUsageCounter>000</sapAddressUsageCounter>
              <phone1>50599912</phone1>
              <publicKey>155744_3020_30_00|40055|KNA1|RE</publicKey>
              <cellphone>50488810</cellphone>
              <fax>678990</fax>
              <region>
                <Region>
                  <integrationKey>
                  </integrationKey>
                  <isocode>US-CA</isocode>
                  <country>
                    <Country>
                      <integrationKey>
                      </integrationKey>
                      <isocode>US</isocode>
                    </Country>
                  </country>
                </Region>
              </region>
              <country>
                <Country>
                  <integrationKey>dummy</integrationKey>
                  <isocode>US</isocode>
                </Country>
              </country>
            </Address>
          </addresses>
          <userPriceGroup>
            <UserPriceGroup>
              <integrationKey>
              </integrationKey>
              <code>01</code>
            </UserPriceGroup>
          </userPriceGroup>
          <groups>
            <UserGroup>
              <integrationKey>
              </integrationKey>
              <uid>155744</uid>
            </UserGroup>
          </groups>
        </B2BUnit>
      </B2BUnits>
    </batchChangeSetPart>
  </batchChangeSet>
  <batchChangeSet>
    <batchChangeSetPart>
      <method>POST</method>
      <B2BUnits>
        <B2BUnit>
          <integrationKey>
          </integrationKey>
          <description>test-155744 - 40055</description>
          <name>test-155744 - 40055</name>
          <buyer>true</buyer>
          <locName>test-155744 - 40055</locName>
          <active>true</active>
          <uid>155744_3020_30_00</uid>
          <userDiscountGroup>
            <UserDiscountGroup>
              <integrationKey>
              </integrationKey>
              <code>01</code>
            </UserDiscountGroup>
          </userDiscountGroup>
          <addresses>
            <Address>
              <integrationKey>
              </integrationKey>
              <sapCustomerID>40055</sapCustomerID>
              <duplicate>false</duplicate>
              <company>test-155744 - 40055</company>
              <district>LOS ANGELES 1</district>
              <town>Beverly Hills address sync 1</town>
              <shippingAddress>true</shippingAddress>
              <billingAddress>false</billingAddress>
              <streetnumber>87656</streetnumber>
              <postalcode>90211</postalcode>
              <streetname>Sunset 5</streetname>
              <sapAddressUsage>WE</sapAddressUsage>
              <sapAddressUsageCounter>000</sapAddressUsageCounter>
              <phone1>50599912</phone1>
              <publicKey>155744_3020_30_00|40055|KNA1|WE</publicKey>
              <cellphone>50488810</cellphone>
              <fax>678990</fax>
              <region>
                <Region>
                  <integrationKey>
                  </integrationKey>
                  <isocode>US-CA</isocode>
                  <country>
                    <Country>
                      <integrationKey>
                      </integrationKey>
                      <isocode>US</isocode>
                    </Country>
                  </country>
                </Region>
              </region>
              <country>
                <Country>
                  <integrationKey>dummy</integrationKey>
                  <isocode>US</isocode>
                </Country>
              </country>
            </Address>
          </addresses>
          <userPriceGroup>
            <UserPriceGroup>
              <integrationKey>
              </integrationKey>
              <code>01</code>
            </UserPriceGroup>
          </userPriceGroup>
          <groups>
            <UserGroup>
              <integrationKey>
              </integrationKey>
              <uid>155744</uid>
            </UserGroup>
          </groups>
        </B2BUnit>
      </B2BUnits>
    </batchChangeSetPart>
  </batchChangeSet>
</batchParts>
    '''
    
    // def messageLog = messageLogFactory.getMessageLog(message);
    // def result = message.getBody().replaceAll("&", "&amp;");
    // messageLog.addAttachmentAsString("Log - last result ",  result,
		  //                                                 "text/xml");
    
    // message.setBody(result);
    return message;
}