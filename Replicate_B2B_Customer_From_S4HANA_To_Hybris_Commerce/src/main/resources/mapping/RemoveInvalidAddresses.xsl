<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="3.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
  <!-- Define current date as a variable -->
  <xsl:variable name="currentDate" select="current-date()"/>  <!-- Identity template to copy all nodes and attributes -->
  <xsl:template match="@*|node()">
    <xsl:copy>
      <xsl:apply-templates select="@*|node()"/>
    </xsl:copy>
  </xsl:template>  
  <xsl:template match="AddressInformation[ValidityPeriod/EndDate &lt; $currentDate]"/>
</xsl:stylesheet> 