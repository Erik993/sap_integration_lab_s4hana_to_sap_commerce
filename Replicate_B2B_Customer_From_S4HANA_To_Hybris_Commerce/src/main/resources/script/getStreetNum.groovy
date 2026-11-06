import com.sap.it.api.mapping.MappingContext;


def String getStreetNum(String header, MappingContext context){
    
    String streetNum = context.getProperty("StreetNumber");
    
    return streetNum;
}
