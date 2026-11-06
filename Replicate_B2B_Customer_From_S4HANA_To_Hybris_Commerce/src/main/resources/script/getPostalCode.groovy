import com.sap.it.api.mapping.MappingContext;


def String getPostalCode(String header, MappingContext context){
    
    String postalCode = context.getProperty("PostalCode");
    
    return postalCode;
}
