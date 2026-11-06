<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="2.0"
    xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
    xmlns:iflow="http://www.sap.com/ifl"
    xmlns:map="http://www.sap.com/pi/XSLT/Mapping" 
    exclude-result-prefixes="iflow map">

    <xsl:output method="xml" indent="yes"/>
    <!-- Main template -->
    <xsl:template match="/">
        <batchParts>
            <batchChangeSet>

            <!-- Get HashMap from Exchange Property -->
                <xsl:variable name="b2bCustomersXml" select="iflow:exchangeProperty('b2bCustomersXml')" />

                <!-- Check if the property is not empty and is a map -->
                <xsl:if test="$b2bCustomersXml and map:size($b2bCustomersXml) > 0">
                    <!-- Loop through the values of the map -->
                    <xsl:for-each select="map:keys($b2bCustomersXml)">
                        <!-- Access each value using the key -->

                        <xsl:variable name="currentValue" select="$exchangeProperty(current())" />
                    <!-- Check if the value is not equal to null -->
                        <xsl:if test="$currentValue">
                            <!-- Use XPath to select BaseUnits/B2BUnits/B2BUnit value -->
                            <xsl:variable name="b2bCustomerValue" select="$currentValue//B2BCustomer" />
                            <!-- Check if B2BUnit value is not null -->
                            <xsl:if test="$b2bCustomerValue">
                                 <batchChangeSetPart>
                                    <method>POST</method>
                                    <B2BCustomers>
                                        <!--copy all the the element and data under path //BaseUnit/B2BUnits/B2BUnit-->
                                        <xsl:value-of select="$b2bCustomerValue"/>
                                    </B2BCustomers>
                                </batchChangeSetPart>
                            </xsl:if>
                        </xsl:if>
                    </xsl:for-each>
                </xsl:if>

                <!--Filter all sales unit which located in path //SalesUnit/B2BUnits/B2BUnit and put them in batch request -->

            </batchChangeSet>
        </batchParts>
    </xsl:template>

</xsl:stylesheet>


