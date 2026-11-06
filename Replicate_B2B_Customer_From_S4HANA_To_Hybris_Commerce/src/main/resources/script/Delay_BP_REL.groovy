import com.sap.gateway.ip.core.customdev.util.Message;

def Message processData(Message message) {

    def properties = message.getProperties() as Map<String, Object>;
    int timeDifference = properties.get("timeDifference").toInteger();

    message.sleep(timeDifference);

       return message;
}