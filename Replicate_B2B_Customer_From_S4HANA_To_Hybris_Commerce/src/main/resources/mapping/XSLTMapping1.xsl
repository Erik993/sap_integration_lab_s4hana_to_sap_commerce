<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
  
  <!-- 身份模板：默认复制所有节点 -->
  <xsl:template match="@*|node()">
    <xsl:copy>
      <xsl:apply-templates select="@*|node()"/>
    </xsl:copy>
  </xsl:template>

  <!-- 对 A_BusinessPartnerAddress 下的地址按 ValidityStartDate 升序排序 -->
  <xsl:template match="A_BusinessPartnerAddress">
    <xsl:copy>
      <!-- 按 ValidityStartDate 升序排列，最早的开始日期排在最前面 -->
      <xsl:apply-templates select="A_BusinessPartnerAddressType">
        <xsl:sort select="ValidityStartDate" data-type="text" order="ascending"/>
      </xsl:apply-templates>
    </xsl:copy>
  </xsl:template>

</xsl:stylesheet>