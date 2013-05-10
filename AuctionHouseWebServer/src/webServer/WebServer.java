package webServer;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import webServer.messages.DropOfferRequest;
import webServer.messages.GetProfileRequest;
import webServer.messages.GetProfileResponse;
import webServer.messages.LaunchOfferRequest;
import webServer.messages.LaunchOfferResponse;
import webServer.messages.LoginRequest;
import webServer.messages.LoginResponse;
import webServer.messages.LogoutRequest;
import webServer.messages.OkResponse;
import webServer.messages.RegisterProfileRequest;
import webServer.messages.SetProfileRequest;
import config.WebServiceServerConfig;
import data.LoginCred;
import data.Service;
import data.UserEntry;
import data.UserProfile;
import data.UserProfile.UserRole;

public class WebServer {

	// public Object login(LoginRequest req) {
	// return "";
	// }
	//
	// public Object logout(LogoutRequest requestMsg) {
	// return "";
	// }
	//
	// public Object launchOffer(LaunchOfferRequest req) {
	// return "";
	// }
	//
	// public Object dropOffer(DropOfferRequest req) {
	// return "";
	// }
	//
	// public Object getProfile(GetProfileRequest req) {
	// return "";
	// }
	//
	// public Object setProfile(SetProfileRequest req) {
	// return "";
	// }
	//
	// public Object registerProfile(RegisterProfileRequest req) {
	// return "";
	// }

	public float celsiusToFarenheit(float celsius) {
		return (celsius * 9 / 5) + 32;
	}

	public float farenheitToCelsius(float farenheit) {
		return (farenheit - 32) * 5 / 9;
	}

	public Object farenheitToCelsius1(Object farenheit) {
		return 1;
	}
	
	
}
