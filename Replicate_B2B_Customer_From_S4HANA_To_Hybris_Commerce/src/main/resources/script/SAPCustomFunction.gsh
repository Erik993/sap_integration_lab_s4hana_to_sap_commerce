import com.sap.it.api.mapping.*;
import com.sap.gateway.ip.core.customdev.util.Message;
import java.util.HashMap;

//Add MappingContext as an additional argument to read or set Headers and properties.

def String getProperty(String property_name, MappingContext context) {
    def propValue= context.getProperty(property_name);
    return propValue;
}
