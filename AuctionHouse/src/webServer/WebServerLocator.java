/**
 * WebServerLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package webServer;

public class WebServerLocator extends org.apache.axis.client.Service implements webServer.WebServer {

    public WebServerLocator() {
    }


    public WebServerLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public WebServerLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for WebServerHttpSoap11Endpoint
    private java.lang.String WebServerHttpSoap11Endpoint_address = "http://172.16.164.1:8080/AuctionHouseWebServer/services/WebServer.WebServerHttpSoap11Endpoint/";

    public java.lang.String getWebServerHttpSoap11EndpointAddress() {
        return WebServerHttpSoap11Endpoint_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String WebServerHttpSoap11EndpointWSDDServiceName = "WebServerHttpSoap11Endpoint";

    public java.lang.String getWebServerHttpSoap11EndpointWSDDServiceName() {
        return WebServerHttpSoap11EndpointWSDDServiceName;
    }

    public void setWebServerHttpSoap11EndpointWSDDServiceName(java.lang.String name) {
        WebServerHttpSoap11EndpointWSDDServiceName = name;
    }

    public webServer.WebServerPortType getWebServerHttpSoap11Endpoint() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(WebServerHttpSoap11Endpoint_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getWebServerHttpSoap11Endpoint(endpoint);
    }

    public webServer.WebServerPortType getWebServerHttpSoap11Endpoint(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            webServer.WebServerSoap11BindingStub _stub = new webServer.WebServerSoap11BindingStub(portAddress, this);
            _stub.setPortName(getWebServerHttpSoap11EndpointWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setWebServerHttpSoap11EndpointEndpointAddress(java.lang.String address) {
        WebServerHttpSoap11Endpoint_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (webServer.WebServerPortType.class.isAssignableFrom(serviceEndpointInterface)) {
                webServer.WebServerSoap11BindingStub _stub = new webServer.WebServerSoap11BindingStub(new java.net.URL(WebServerHttpSoap11Endpoint_address), this);
                _stub.setPortName(getWebServerHttpSoap11EndpointWSDDServiceName());
                return _stub;
            }
        }
        catch (java.lang.Throwable t) {
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        java.lang.String inputPortName = portName.getLocalPart();
        if ("WebServerHttpSoap11Endpoint".equals(inputPortName)) {
            return getWebServerHttpSoap11Endpoint();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://webServer", "WebServer");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://webServer", "WebServerHttpSoap11Endpoint"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("WebServerHttpSoap11Endpoint".equals(portName)) {
            setWebServerHttpSoap11EndpointEndpointAddress(address);
        }
        else 
{ // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(javax.xml.namespace.QName portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}
