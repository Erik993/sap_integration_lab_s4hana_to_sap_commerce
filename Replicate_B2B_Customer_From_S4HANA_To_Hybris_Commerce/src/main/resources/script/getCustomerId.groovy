import com.sap.it.api.mapping.MappingContext;

// get InternalID of S4HANA as CustomerID
def String getCustomerId(String header, MappingContext context){
    
    String internalId = context.getProperty("InternalID");
    String customerId = context.getProperty(internalId + "_CustomerID")
    
	return customerId;
}