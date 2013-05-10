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
import webServer.messages.ErrorMessage;
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

	public byte[] login(byte[] req) {
		byte[] res;
		Object obj = WebMessage.deserialize(req);

		if (!(obj instanceof LoginRequest)) {
			System.out.println("[WebServer:login] Wrong message... waiting LoginRequest");
		}
		
		LoginRequest loginRequest = (LoginRequest) obj;
		LoginCred loginCred = loginRequest.getLoginCred();

		System.out.println("[WebServer:login] Begin (loginRequest: " + loginRequest.getLoginCred().getUsername());

		Statement simpleStatement;
		try {
			simpleStatement = conn.createStatement();
			
			String query = 
					"SELECT *" +
					" FROM users" +
					" WHERE username = " + loginCred.getUsername() +
					" AND password = " + loginCred.getPassword();
			ResultSet rs = simpleStatement.executeQuery(query);

			if (rs.first()) {
				Integer id = rs.getInt("id");
				
				Integer role = 0;
				if (loginCred.getRole() == UserRole.SELLER) {
					role = 1;
				}

				if (role == 0) {
					query = 
							"UPDATE users" +
									" SET as_buyer = " + 1 +
									" WHERE id = " + id;
				} else {
					query = 
							"UPDATE users" + 
									" SET as_seller = " + 1 +
									" WHERE id = " + id;
				}

				simpleStatement.executeUpdate(query);
				
				UserProfile userProfile = new UserProfile();
				userProfile.setUsername(rs.getString("username"));
				userProfile.setFirstName(rs.getString("first_name"));
				userProfile.setLastName(rs.getString("last_name"));
				userProfile.setPassword(rs.getString("password"));
				userProfile.setRole(loginCred.getRole());
				userProfile.setLocation(rs.getString("location"));
				
				res = WebMessage.serialize(new LoginResponse(userProfile));
				
				System.out.println("[WebServer:login] Logged in");
			} else {
				System.out.println("[WebServer:login] Wrong username or password");
				res = WebMessage.serialize(new ErrorMessage("Wrong username or password"));
			}
		} catch (SQLException e) {
			System.out.println("Something bad happend at server");
			res = WebMessage.serialize(new ErrorMessage("Something bad happend at server"));

			e.printStackTrace();
		}
		
		return res;
	}

	public byte[] logout(byte[] req) {
		System.out.println("[WebServer:logout] Begin");

		Object obj = WebMessage.deserialize(req);

		if (!(obj instanceof LogoutRequest)) {
			System.out.println("[WebServer:logout] Wrong message... waiting LogoutRequest");
		}
		
		LogoutRequest logoutRequest = (LogoutRequest) obj;
		LoginCred cred = logoutRequest.getCred();

		try {
			Statement simpleStatement = conn.createStatement();
		
			if (cred.getRole() == UserRole.BUYER) {
				String query = 
						"UPDATE users" +
								" SET as_buyer = " + 0 +
								" WHERE username = " + cred.getId();
				simpleStatement.executeUpdate(query);
				
				query = 
						"UPDATE services" +
								" SET active = " + 0 +
								" WHERE id = " + cred.getId() +
								" AND user_role = " + cred.getRole();
				simpleStatement.executeUpdate(query);
			} else {
				String query = 
						"UPDATE users" +
								" SET as_seller = " + 0 +
								" WHERE username = " + cred.getId();
				simpleStatement.executeUpdate(query);
				
				query = 
						"UPDATE services" +
								" SET active = " + 0 +
								" WHERE id = " + cred.getId() +
								" AND user_role = " + cred.getRole();
				simpleStatement.executeUpdate(query);
			}
		} catch (SQLException e) {
			System.out.println("Something bad happend at server");
			e.printStackTrace();
		}

		System.out.println("[WebServer:logout] End");
		return WebMessage.serialize(new OkResponse());
	}

	public byte[] launchOffer(byte[] req) {
		System.out.println("[WebServer:logout] Begin");
		
		byte[] res = new byte[1];
		LaunchOfferRequest launchOfferReq;

//		Service service = req.getService();
//		ArrayList<UserEntry> userEntries;
//
//		UserEntry userEntry = new UserEntry();
//		userEntry.setUsername(req.getUsername());
//		UserProfile profile = users.get(req.getUsername());
//		userEntry.setName(profile.getFirstName() + " " + profile.getLastName());
//		userEntry.setAddress(onlineUsers.get(req.getUsername()));
//
//		if (req.getUserRole() == UserRole.BUYER) {
//			sellers.putIfAbsent(service.getName(), new ArrayList<UserEntry>());
//			userEntries = sellers.get(service.getName());
//
//			buyers.putIfAbsent(service.getName(), new ArrayList<UserEntry>());
//			ArrayList<UserEntry> buyersUserEntries = buyers.get(service.getName());
//			buyersUserEntries.add(userEntry);
//
//			buyers.put(service.getName(), buyersUserEntries);
//		} else {
//			userEntry.setTime(service.getTime());
//			buyers.putIfAbsent(service.getName(), new ArrayList<UserEntry>());
//			userEntries = buyers.get(service.getName());
//
//			sellers.putIfAbsent(service.getName(), new ArrayList<UserEntry>());
//			ArrayList<UserEntry> sellersUserEntries = sellers.get(service.getName());
//			sellersUserEntries.add(userEntry);
//
//			sellers.put(service.getName(), sellersUserEntries);
//		}
//
//		service.setUsers(userEntries);
//
//		return new LaunchOfferResponse(service);

		System.out.println("[WebServer:logout] End");
		return res;
	}

	public byte[] dropOffer(DropOfferRequest req) {
		logger.debug("Begin");

		UserEntry userEntry = new UserEntry();
		userEntry.setName(req.getUsername());
		userEntry.setAddress(onlineUsers.get(req.getUsername()));

		if (req.getUserRole() == UserRole.BUYER) {
			ArrayList<UserEntry> buyersUserEntries = buyers.get(req.getServiceName());
			buyersUserEntries.remove(userEntry);

			buyers.put(req.getServiceName(), buyersUserEntries);
		} else {
			ArrayList<UserEntry> sellersUserEntries = sellers.get(req.getServiceName());
			sellersUserEntries.remove(userEntry);

			sellers.put(req.getServiceName(), sellersUserEntries);
		}

		logger.debug("End (OK response)");
		return new OkResponse();
	}

	public byte[] getProfile(GetProfileRequest req) {
		logger.debug("Begin");
		UserProfile profile = users.get(req.getUsername());
		logger.debug("End (OK response)");
		return new GetProfileResponse(profile);
	}

	public Object setProfile(SetProfileRequest req) {
		logger.debug("Begin");
		UserProfile profile = req.getUserProfile();

		users.put(profile.getUsername(), profile);
		logger.debug("End (OK response");
		return new OkResponse();
	}

	public byte[] registerProfile(RegisterProfileRequest req) {
		logger.debug("Begin");
		UserProfile profile = req.getUserProfile();

		users.put(profile.getUsername(), profile);
		logger.debug("End (OK response)");
		return new OkResponse();
	}
}
