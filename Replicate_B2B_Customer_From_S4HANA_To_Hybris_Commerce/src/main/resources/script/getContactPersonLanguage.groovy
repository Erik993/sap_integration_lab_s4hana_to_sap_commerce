import com.sap.it.api.mapping.MappingContext;

def String getContactPersonLanguage(String header, MappingContext context){
    
    String internalID = context.getProperty("InternalID");
    String contactPersonLanguage = context.getProperty(internalID + "_Language");
    
	return contactPersonLanguage;
}