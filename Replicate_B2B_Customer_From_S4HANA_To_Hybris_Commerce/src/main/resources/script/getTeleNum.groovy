import com.sap.it.api.mapping.MappingContext;

def String getTeleNum(String header, MappingContext context){
    
    String teleNum = context.getProperty("PhoneNumList");
    String telephoneNum = '';
    
    if(teleNum.contains(",")){ // cell and tele both exist
        
        String [] teleNums = teleNum.tokenize(",");
        
        if(teleNums[0].contains('false')){
           
            String [] telephoneNumArr1 = teleNums[0].tokenize("|");
            telephoneNum = telephoneNumArr1[1];
            return telephoneNum;
            
        }else if(teleNums[0].contains('true')){
            
            String [] telephoneNumArr2 = teleNums[1].tokenize("|");
            telephoneNum = telephoneNumArr2[1];
            return telephoneNum;
            
        }
        
        return telephoneNum;
        
    }else if(teleNum.contains('false')){ // only tele
        
        String [] telephoneNumArr3 = teleNum.tokenize("|");
        telephoneNum = telephoneNumArr3[1];
        return telephoneNum;
        
    }else{  // only cell
        
        return telephoneNum;
        
    } 
    
    
	return teleNum;
}