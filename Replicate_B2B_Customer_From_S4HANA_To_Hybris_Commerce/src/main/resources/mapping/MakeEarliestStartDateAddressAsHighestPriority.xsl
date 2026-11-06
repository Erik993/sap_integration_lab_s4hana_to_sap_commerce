<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
 <!-- <xsl:output indent="yes"/> -->
  <xsl:template match="@*|node()">
    <xsl:copy>
      <xsl:apply-templates select="@*|node()"/>
    </xsl:copy>
  </xsl:template>

  <xsl:template match="BusinessPartner">
    <xsl:copy>
      <xsl:apply-templates select="@*|node()[not(self::AddressInformation)]"/>
      <xsl:apply-templates select="AddressInformation">
        <xsl:sort select="ValidityPeriod/StartDate" data-type="text" order="ascending"/>
      </xsl:apply-templates>
    </xsl:copy>
  </xsl:template>
  
</xsl:stylesheet>