<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
  
  <xsl:template match="@*|node()">
    <xsl:copy>
      <xsl:apply-templates select="@*|node()"/>
    </xsl:copy>
  </xsl:template>
  
  <xsl:template match="to_BusinessPartnerAddress">
    <xsl:copy>
      <xsl:apply-templates select="A_BusinessPartnerAddressType">
        <xsl:sort select="ValidityStartDate" order="ascending"/>
      </xsl:apply-templates>
    </xsl:copy>
  </xsl:template>
  
  <xsl:template match="A_BusinessPartnerAddressType">
    <xsl:variable name="validityEndDate" select="ValidityEndDate"/>
    
    <xsl:choose>
      <xsl:when test="string-length($validityEndDate) = 0">
        <xsl:copy>
          <xsl:apply-templates select="@*|node()"/>
        </xsl:copy>
      </xsl:when>
      
      <xsl:otherwise>
        <xsl:variable name="endDate" select="substring($validityEndDate, 1, 10)"/>
        <xsl:variable name="currentDate" select="substring(translate(string(current-date()), '-', ''), 1, 8)"/>
        <xsl:variable name="endDateNum" select="translate($endDate, '-', '')"/>
        
        <xsl:if test="$endDateNum &gt;= $currentDate">
          <xsl:copy>
            <xsl:apply-templates select="@*|node()"/>
          </xsl:copy>
        </xsl:if>
      </xsl:otherwise>
    </xsl:choose>
  </xsl:template>

</xsl:stylesheet>