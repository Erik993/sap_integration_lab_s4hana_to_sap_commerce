import com.sap.it.api.mapping.MappingContext;


def String getRegion(String header, MappingContext context){
    
    String region = context.getProperty("Region");
    
    return region;
}
