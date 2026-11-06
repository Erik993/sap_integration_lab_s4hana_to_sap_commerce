import com.sap.gateway.ip.core.customdev.util.Message;

/*
 * Global exception handler for the integration flow.
 * Placed inside the Exception Subprocess of the main Integration Process.
 * Builds a clear, non-sensitive error message for support teams and the caller.
 * Does NOT log request payloads, credentials, tokens or keys.
 */
def Message processData(Message message) {

    def messageLog = messageLogFactory.getMessageLog(message);

    // CPI injects the caught exception as a property inside an exception subprocess
    def exception = message.getProperty("CamelExceptionCaught");
    def exceptionType = exception != null ? exception.getClass().getName() : "UnknownException";
    def exceptionMessage = exception != null ? exception.getMessage() : "No exception details available";

    // Use SAP_ApplicationID as searchable business identifier when available,
    // fall back to the always-present MPL ID so support can locate the message
    def applicationId = message.getHeader("SAP_ApplicationID", java.lang.String);
    def mplId = message.getHeader("SAP_MessageProcessingLogID", java.lang.String);

    def errorText = "Integration flow failed." +
                    "\n Application ID : " + (applicationId ?: "N/A") +
                    "\n MPL ID         : " + (mplId ?: "N/A") +
                    "\n Exception Type : " + exceptionType +
                    "\n Message        : " + exceptionMessage;

    // Log only the non-sensitive error summary, gated by the existing enableLog switch
    if (messageLog != null && message.getProperty("enableLog") == "true") {
        messageLog.setStringProperty("ErrorType", exceptionType);
        messageLog.addAttachmentAsString("Exception Details", errorText, "text/plain");
    }

    // Return a clear custom error message to the caller
    message.setBody(errorText);

    return message;
}
