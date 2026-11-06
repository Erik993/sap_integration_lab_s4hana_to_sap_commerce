import com.sap.it.api.mapping.MappingContext;

def String getCellNum(String header, MappingContext context){
    
    String internalID = context.getProperty("InternalID")
    String cellNum = context.getProperty(internalID + "_PhoneNumList");
    String cellphoneNum = '';
    
    if(cellNum.contains(",")){ // cell and tele both exist
        
        String [] cellNums = cellNum.tokenize(",");
        
        if(cellNums[0].contains('true')){
           
            String [] cellphoneNumArr1 = cellNums[0].tokenize("|");
            cellphoneNum = cellphoneNumArr1[1];
            return cellphoneNum;
            
        }else if(cellNums[0].contains('false')){
            
            String [] cellphoneNumArr2 = cellNums[1].tokenize("|");
            cellphoneNum = cellphoneNumArr2[1];
            return cellphoneNum;
            
        }
        
        return cellphoneNum;
        
    }else if(cellNum.contains('true')){ // only cell
        
        String [] cellphoneNumArr3 = cellNum.tokenize("|");
        cellphoneNum = cellphoneNumArr3[1];
        return cellphoneNum;
        
    }else{ // only tele
        
        return cellphoneNum;
        
    } 
    
	return cellNum;
}