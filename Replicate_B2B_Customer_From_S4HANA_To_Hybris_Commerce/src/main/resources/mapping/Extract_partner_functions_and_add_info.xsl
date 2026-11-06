<xsl:stylesheet
  xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
  version="2.0">

<xsl:output indent="yes"/>
<xsl:strip-space elements="*"/>

    <xsl:template match="@*|node()">
        <xsl:copy>
            <xsl:apply-templates select="@*|node()"/>
        </xsl:copy>
    </xsl:template>
    
<xsl:template match="/">
  <CUSTOMIZE>
  <xsl:for-each-group select="//BusinessPartnerSUITEReplicateRequestMessage/BusinessPartner/Customer/SalesArrangement/PartnerFunctions" group-by="concat(PartyRoleCode,PartyInternalID)">
        <xsl:apply-templates select="/*"/>
   </xsl:for-each-group>
   </CUSTOMIZE> 
</xsl:template>

<xsl:template match="//BusinessPartnerSUITEReplicateRequestMessage/BusinessPartner/Customer/SalesArrangement/PartnerFunctions">
  <xsl:if test=". intersect current-group()">
            <xsl:next-match/>
        </xsl:if>
</xsl:template>

</xsl:stylesheet>