import com.sap.it.api.mapping.MappingContext;


def String getCountry(String header, MappingContext context){
    
    String country = context.getProperty("Country");
    
    return country;
}