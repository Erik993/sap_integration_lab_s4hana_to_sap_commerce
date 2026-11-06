import com.sap.it.api.mapping.MappingContext;


def String getExtraFax(String header, MappingContext context){
    
    String fax = context.getProperty("Fax");
    
    return fax;
}
