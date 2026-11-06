<?xml version="1.0" encoding="iso-8859-1"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
   <xsl:strip-space elements="*" />
   <xsl:output method="xml" indent="yes"/>

   <xsl:template match="@*|node()">
       <xsl:copy>
           <xsl:apply-templates select="@*|node()"/>
       </xsl:copy>
   </xsl:template>

   <xsl:template match="B2BCustomer/groups/UserGroup/uid" />

   <xsl:template match="B2BCustomer/groups/UserGroup">
       <xsl:call-template name="splitUID">
           <xsl:with-param name="UID" select="uid" />
       </xsl:call-template>
   </xsl:template>

   <xsl:template name="splitUID">
       <xsl:param name="UID"/>
       <xsl:choose>
           <xsl:when test="contains($UID,',')">
               <xsl:call-template name="splitUID">
                   <xsl:with-param name="UID" select="substring-before($UID,',')"/>
               </xsl:call-template>
               <xsl:call-template name="splitUID">
                   <xsl:with-param name="UID" select="substring-after($UID,',')"/>
               </xsl:call-template>
           </xsl:when>
           <xsl:otherwise>
               <xsl:copy>
                   <xsl:apply-templates />
                   <uid>
                       <xsl:value-of select="$UID"/>
                   </uid>
               </xsl:copy>
           </xsl:otherwise>
       </xsl:choose>
   </xsl:template>

   <xsl:template match="B2BCustomers">
       <xsl:copy>
           <xsl:apply-templates select="B2BCustomer" />
       </xsl:copy>
   </xsl:template>

</xsl:stylesheet>