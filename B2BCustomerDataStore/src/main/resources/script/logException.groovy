import com.sap.gateway.ip.core.customdev.util.Message;
def Message processData(Message message) {
	 message.setBody("<NOTFOUND/>");
       return message;
}