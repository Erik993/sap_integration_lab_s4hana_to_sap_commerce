import com.sap.it.api.mapping.MappingContext;

def String getPublicKey(String header, MappingContext context){
    
    String internalID = context.getProperty("InternalID");
    String rawPublicKey = context.getProperty(internalID + "_CustomerID");
    String publicKey = rawPublicKey + "|" + rawPublicKey + "|BUS1006001|null";
    
    return publicKey;
}
