import com.sap.it.api.mapping.MappingContext;

def String getB2BUnitId(String header, MappingContext context){
    
    String B2BUnitId = context.getProperty("DefaultB2BUnit");
    
	return B2BUnitId;
}