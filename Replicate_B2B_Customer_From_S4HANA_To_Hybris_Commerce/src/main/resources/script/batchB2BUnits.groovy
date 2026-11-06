import com.sap.gateway.ip.core.customdev.util.Message
import java.util.HashMap
import groovy.util.XmlParser
import groovy.xml.XmlUtil


def Message processData(Message message) {
    def properties = message.getProperties()
    def b2bUnitsXml = properties.get("b2bUnitsXml")
    def messageLog = messageLogFactory.getMessageLog(message)

    def customPostHook = message.getProperty("B2BUnitsCustomPostHook") as String
    def customPreHook = message.getProperty("B2BUnitsCustomPreHook") as String
    def headers = ""
    if (!customPostHook.equals("") || !customPreHook.equals("")) {
        def customPostHookData = !customPostHook.equals("") ? "<header><headerName>Post-Persist-Hook</headerName><headerValue>${customPostHook}</headerValue></header>" : ""
        def customPreHookData = !customPreHook.equals("") ? "<header><headerName>Pre-Persist-Hook</headerName><headerValue>${customPreHook}</headerValue></header>" : ""
        headers = "<headers>${customPostHookData}${customPreHookData}</headers>"
    }

    // Map for baseUnits
    def b2bBaseUnitMap = new HashMap<String, groovy.util.Node>()
    
     // Map for salesUnits
    def b2bSalesUnitMap = new HashMap<String, groovy.util.Node>()

    for (entry in b2bUnitsXml) {
        if (entry.value != null && !entry.value.trim().equals("")) {
            def wholeUnit = new XmlParser().parseText(entry.value)

            // handle BaseUnits
            wholeUnit.BaseUnits.B2BUnits.B2BUnit.each { b2bUnit ->
                def uid = b2bUnit.uid.text()
                if (b2bBaseUnitMap.containsKey(uid)) {
                    def existingUnit = b2bBaseUnitMap.get(uid)
                    def existingAddressesNode = existingUnit.addresses[0] ?: existingUnit.appendNode('addresses') // return the first addresses node or create new
                    b2bUnit.addresses.Address.each { address ->
                        def newAddressNode = new groovy.util.Node(existingAddressesNode, 'Address')
            
                        // copy child of Address to new one
                        address.children().each { child ->
                            new groovy.util.Node(newAddressNode, child.name(), child.attributes(), child.value())
                        }
                    }
                } else {
                    b2bBaseUnitMap.put(uid, b2bUnit)
                }
            }

            // handle SalesUnits
            wholeUnit.SalesUnits.B2BUnits.B2BUnit.each { b2bUnit ->
                def uid = b2bUnit.uid.text()
                if (b2bSalesUnitMap.containsKey(uid)) {
                    def existingUnit = b2bSalesUnitMap.get(uid)
                    def existingAddressesNode = existingUnit.addresses[0] ?: existingUnit.appendNode('addresses') // return the first addresses node or create new
                    b2bUnit.addresses.Address.each { address ->
                        def newAddressNode = new groovy.util.Node(existingAddressesNode, 'Address')
                
                        // copy child of Address to new one
                        address.children().each { child ->
                            new groovy.util.Node(newAddressNode, child.name(), child.attributes(), child.value())
                        }
                    }
                } else {
                    b2bSalesUnitMap.put(uid, b2bUnit)
                }
            }
        }
    }

    def batchedUnitData = new StringBuilder()
    b2bBaseUnitMap.each { uid, b2bUnit ->
        def xmlStringWithHeader = XmlUtil.serialize(b2bUnit)
        def xmlStr = xmlStringWithHeader.replaceFirst(/<\?xml.*?\?>/, '')
        def singleBatchUnitData = "<batchChangeSet><batchChangeSetPart><method>POST</method>${headers}<B2BUnits>${xmlStr}</B2BUnits></batchChangeSetPart></batchChangeSet>"
        batchedUnitData.append(singleBatchUnitData)
    }
    
    b2bSalesUnitMap.each { uid, b2bUnit ->
        def xmlStringWithHeader = XmlUtil.serialize(b2bUnit)
        def xmlStr = xmlStringWithHeader.replaceFirst(/<\?xml.*?\?>/, '')
        def singleBatchUnitData = "<batchChangeSet><batchChangeSetPart><method>POST</method>${headers}<B2BUnits>${xmlStr}</B2BUnits></batchChangeSetPart></batchChangeSet>"
        batchedUnitData.append(singleBatchUnitData)
    }

    if (batchedUnitData.length() > 0) {
        batchedUnitData.insert(0, "<batchParts>")
        batchedUnitData.append("</batchParts>")
        message.setBody(batchedUnitData.toString())
        message.setProperty("b2bUnitIsEmpty", false)
        if (messageLog != null && properties.get("enableLog").equals("true")) {
            messageLog.addAttachmentAsString("Log - Batched B2BUnits", batchedUnitData.toString(), "text/xml")
        }
    } else {
        message.setProperty("b2bUnitIsEmpty", true)
    }

    return message
}