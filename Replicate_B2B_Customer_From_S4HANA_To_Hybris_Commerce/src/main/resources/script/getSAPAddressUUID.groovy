import com.sap.it.api.mapping.MappingContext;


def String getSAPAddressUUID(String header, MappingContext context){
    
    String sapAddressUUID = context.getProperty("sapAddressUUID");
    
    return sapAddressUUID;
}
