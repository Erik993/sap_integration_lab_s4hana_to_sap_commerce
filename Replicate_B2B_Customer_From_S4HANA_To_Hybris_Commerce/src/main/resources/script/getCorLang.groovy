import com.sap.it.api.mapping.MappingContext;

// get correspondence language code from organizaiton
def String getCorLangCode(String header, MappingContext context){
    
    String correspondenceLanguageCode = context.getProperty("CorrespondenceLanguageCode").toLowerCase();
    
	return correspondenceLanguageCode;
}