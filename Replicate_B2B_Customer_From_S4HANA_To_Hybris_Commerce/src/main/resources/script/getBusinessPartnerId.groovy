import com.sap.it.api.mapping.MappingContext;

// get sapBusinessPartnerId
def String getSapBusinessPartnerId(String header, MappingContext context){
    
    String sapBusinessPartnerId = context.getProperty("InternalID");
    
	return sapBusinessPartnerId;
}