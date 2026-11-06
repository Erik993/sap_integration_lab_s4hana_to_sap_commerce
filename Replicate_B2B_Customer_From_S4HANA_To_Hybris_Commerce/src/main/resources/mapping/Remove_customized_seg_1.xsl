<!--remove customized root-->
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
    xmlns:xs="http://www.w3.org/2001/XMLSchema"
    exclude-result-prefixes="xs"
    version="2.0">

   <xsl:template match="node() | @*">
  <xsl:copy>
    <xsl:apply-templates select="node() | @*" />
  </xsl:copy>
</xsl:template>

<!-- template for the document element -->
<xsl:template match="/*">
  <xsl:apply-templates select="node()" />
</xsl:template>

</xsl:stylesheet>