import com.sap.it.api.mapping.MappingContext;


def String getDistrict(String header, MappingContext context){
    
    String district = context.getProperty("District");
    
    return district;
}
