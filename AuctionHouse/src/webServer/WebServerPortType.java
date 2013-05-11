/**
 * WebServerPortType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package webServer;

public interface WebServerPortType extends java.rmi.Remote {
    public byte[] logout(byte[] byteReq) throws java.rmi.RemoteException;
    public byte[] launchOffer(byte[] byteReq) throws java.rmi.RemoteException;
    public byte[] registerProfile(byte[] byteReq) throws java.rmi.RemoteException;
    public byte[] dropOffer(byte[] byteReq) throws java.rmi.RemoteException;
    public byte[] login(byte[] byteReq) throws java.rmi.RemoteException;
    public byte[] loadOffers(byte[] byteReq) throws java.rmi.RemoteException;
    public byte[] getProfile(byte[] byteReq) throws java.rmi.RemoteException;
    public byte[] setProfile(byte[] byteReq) throws java.rmi.RemoteException;
}
