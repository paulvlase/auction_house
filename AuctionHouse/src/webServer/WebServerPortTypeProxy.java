package webServer;

public class WebServerPortTypeProxy implements webServer.WebServerPortType {
  private String _endpoint = null;
  private webServer.WebServerPortType webServerPortType = null;
  
  public WebServerPortTypeProxy() {
    _initWebServerPortTypeProxy();
  }
  
  public WebServerPortTypeProxy(String endpoint) {
    _endpoint = endpoint;
    _initWebServerPortTypeProxy();
  }
  
  private void _initWebServerPortTypeProxy() {
    try {
      webServerPortType = (new webServer.WebServerLocator()).getWebServerHttpSoap11Endpoint();
      if (webServerPortType != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)webServerPortType)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)webServerPortType)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (webServerPortType != null)
      ((javax.xml.rpc.Stub)webServerPortType)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public webServer.WebServerPortType getWebServerPortType() {
    if (webServerPortType == null)
      _initWebServerPortTypeProxy();
    return webServerPortType;
  }
  
  public byte[] logout(byte[] byteReq) throws java.rmi.RemoteException{
    if (webServerPortType == null)
      _initWebServerPortTypeProxy();
    return webServerPortType.logout(byteReq);
  }
  
  public byte[] launchOffer(byte[] byteReq) throws java.rmi.RemoteException{
    if (webServerPortType == null)
      _initWebServerPortTypeProxy();
    return webServerPortType.launchOffer(byteReq);
  }
  
  public byte[] registerProfile(byte[] byteReq) throws java.rmi.RemoteException{
    if (webServerPortType == null)
      _initWebServerPortTypeProxy();
    return webServerPortType.registerProfile(byteReq);
  }
  
  public byte[] dropOffer(byte[] byteReq) throws java.rmi.RemoteException{
    if (webServerPortType == null)
      _initWebServerPortTypeProxy();
    return webServerPortType.dropOffer(byteReq);
  }
  
  public byte[] login(byte[] byteReq) throws java.rmi.RemoteException{
    if (webServerPortType == null)
      _initWebServerPortTypeProxy();
    return webServerPortType.login(byteReq);
  }
  
  public byte[] loadOffers(byte[] byteReq) throws java.rmi.RemoteException{
    if (webServerPortType == null)
      _initWebServerPortTypeProxy();
    return webServerPortType.loadOffers(byteReq);
  }
  
  public byte[] getProfile(byte[] byteReq) throws java.rmi.RemoteException{
    if (webServerPortType == null)
      _initWebServerPortTypeProxy();
    return webServerPortType.getProfile(byteReq);
  }
  
  public byte[] setProfile(byte[] byteReq) throws java.rmi.RemoteException{
    if (webServerPortType == null)
      _initWebServerPortTypeProxy();
    return webServerPortType.setProfile(byteReq);
  }
  
  public byte[] removeOffer(byte[] byteReq) throws java.rmi.RemoteException{
    if (webServerPortType == null)
      _initWebServerPortTypeProxy();
    return webServerPortType.removeOffer(byteReq);
  }
  
  
}