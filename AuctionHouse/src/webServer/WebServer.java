/**
 * WebServer.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package webServer;

public interface WebServer extends javax.xml.rpc.Service {
    public java.lang.String getWebServerHttpSoap11EndpointAddress();

    public webServer.WebServerPortType getWebServerHttpSoap11Endpoint() throws javax.xml.rpc.ServiceException;

    public webServer.WebServerPortType getWebServerHttpSoap11Endpoint(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
}
