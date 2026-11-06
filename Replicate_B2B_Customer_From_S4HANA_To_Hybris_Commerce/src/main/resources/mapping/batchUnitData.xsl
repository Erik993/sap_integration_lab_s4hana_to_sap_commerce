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
                <xsl:variable name="b2bUnitsXml" select="iflow:exchangeProperty('b2bUnitsXml')" />

                <!-- Check if the property is not empty and is a map -->
                <xsl:if test="$b2bUnitsXml and map:size($b2bUnitsXml) > 0">
                    <!-- Loop through the values of the map -->
                    <xsl:for-each select="map:keys($b2bUnitsXml)">
                        <!-- Access each value using the key -->

                        <xsl:variable name="currentValue" select="$exchangeProperty(current())" />
                    <!-- Check if the value is not equal to null -->
                        <xsl:if test="$currentValue">
                            <!-- Use XPath to select BaseUnits/B2BUnits/B2BUnit value -->
                            <xsl:variable name="b2bUnitValue" select="$currentValue//BaseUnits/B2BUnits/B2BUnit" />
                            <!-- Check if B2BUnit value is not null -->
                            <xsl:if test="$b2bUnitValue">
                                 <batchChangeSetPart>
                                    <method>POST</method>
                                    <B2BUnits>
                                        <!--copy all the the element and data under path //BaseUnit/B2BUnits/B2BUnit-->
                                        <xsl:value-of select="$b2bUnitValue"/>
                                    </B2BUnits>
                                </batchChangeSetPart>
                            </xsl:if>
                        </xsl:if>
                    </xsl:for-each>

                                        <!-- Loop through the values of the map -->
                    <xsl:for-each select="map:keys($b2bUnitsXml)">
                        <!-- Access each value using the key -->

                        <xsl:variable name="currentSalesValue" select="$exchangeProperty(current())" />
                    <!-- Check if the value is not equal to null -->
                        <xsl:if test="$currentSalesValue">
                            <xsl:for-each select="$currentSalesValue//BaseUnits/B2BUnits/B2BUnit">
                                 <batchChangeSetPart>
                                    <method>POST</method>
                                    <B2BUnits>
                                        <xsl:value-of select="."/>
                                    </B2BUnits>
                                </batchChangeSetPart>
                                
                            </xsl:for-each>
                           
                        </xsl:if>
                    </xsl:for-each>
                </xsl:if>

                <!--Filter all sales unit which located in path //SalesUnit/B2BUnits/B2BUnit and put them in batch request -->

            </batchChangeSet>
        </batchParts>
    </xsl:template>

</xsl:stylesheet>


