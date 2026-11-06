import com.sap.it.api.mapping.MappingContext;


def String getTown(String header, MappingContext context){
    
    String town = context.getProperty("Town");
    
    return town;
}
