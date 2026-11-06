import com.sap.it.api.mapping.MappingContext;

def String getContactPersonLanguageExist(String header, MappingContext context){
    
    String internalID = context.getProperty("InternalID");
    String contactPersonLanguage = context.getProperty(internalID + "_Language");
    
	if(contactPersonLanguage != null && !contactPersonLanguage.isEmpty()){
        return "true";
    } else {
        return "false";
    }

}