package webServer;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import mediator.MediatorImpl;

import org.apache.log4j.Logger;

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
	static String			name	= "root";
	static String			pass	= "student";
	static Connection		conn;

	static {
		System.out.println("[WebServer:static] Begin");
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		String url = "jdbc:mysql://127.0.0.1:3306/auction_house";

		try {
			conn = DriverManager.getConnection(url, name, pass);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		System.out.println("[WebServer:static] End");
	}

	// public Object login(LoginRequest req) {
	public Object login(Object req) {
		System.out.println("[WebServer:login] Begin");

		String username = "pvlase";
		String password = "parola";

		Statement simpleStatement;
		try {
			simpleStatement = conn.createStatement();
			
			String query = 
					"SELECT *" +
					" FROM users" +
					" WHERE username = " + username +
					" AND password = " + password;
			ResultSet rs = simpleStatement.executeQuery(query);

			if (rs.first()) {
				Integer id = 1;
				System.out.println("[WebServer:login] Logged in");
				query = 
						"UPDATE users" +
						" SET as_seller = " + 1 +
						" WHERE id = " + id;

				simpleStatement.executeUpdate(query);
			} else {
				System.out.println("[WebServer:login] Error to logged in");
			}
			/*
			while (rs.next()) {
				String username = rs.getString("username");
				String password = rs.getString("password");

				System.out.println("[WebServer:login] username: " + username + " password: " + password);
			}
			*/
		} catch (SQLException e) {
			e.printStackTrace();
		}

		System.out.println("[WebServer:login] End");
		return "";
	}

	// public Object logout(LogoutRequest requestMsg) {
	public Object logout(Object requestMsg) {
		
		return "";
	}

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
