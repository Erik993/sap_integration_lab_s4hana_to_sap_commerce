import com.sap.it.api.mapping.MappingContext;

def String getGroupCode(String header, MappingContext context){
    
    String b2bunitId = context.getProperty("DefaultB2BUnit");
    String internalId = context.getProperty("InternalID");
    String customerNumber = context.getProperty(internalId + "_CustomerID");
    String contactPersonFunction = context.getProperty(internalId + "_groups");
    contactPersonFunction = contactPersonFunction.toString();

   if( contactPersonFunction == '0002' || contactPersonFunction == '2' ){

       return new StringBuilder().append( b2bunitId )
                .append( ',' )
                .append( 'b2badmingroup'  )
                .append( ',' )
                .append( 'b2bcustomergroup' )
                .toString()

    } else if( contactPersonFunction == '0001' || contactPersonFunction == '1' ){

        return new StringBuilder().append( b2bunitId )
                .append( ',' )
                .append( 'b2badmingroup'  )
                .toString()

    } //have to check the buyer value in S4HANA
    else  if( contactPersonFunction == '0011' || contactPersonFunction == '11'){

        return new StringBuilder().append( b2bunitId )
                .append( ',' )
                .append( 'b2bcustomergroup' )
                .toString()
    }else{

        return new StringBuilder().append( b2bunitId )
                .toString()
    }
}