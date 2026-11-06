import com.sap.it.api.mapping.MappingContext;


def String getStreetName(String header, MappingContext context){
    
    String streetName = context.getProperty("StreetName");
    
    return streetName;
}