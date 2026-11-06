import com.sap.it.api.mapping.MappingContext;


def String getCompany(String header, MappingContext context){
    
    String company = context.getProperty("Company");
    
    return company;
}
