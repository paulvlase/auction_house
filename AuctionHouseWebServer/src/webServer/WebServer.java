package webServer;

import java.io.IOException;
import java.net.InetSocketAddress;
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
	static String		name	= "root";
	static String		pass	= "student";
	static Connection	conn;

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
			return WebMessage.serialize(new ErrorMessage("Wrong message... waiting LoginRequest"));
		}

		LoginRequest loginRequest = (LoginRequest) obj;
		LoginCred loginCred = loginRequest.getLoginCred();

		System.out.println("[WebServer:login] Begin (loginRequest: " + loginRequest.getLoginCred().getUsername());

		Statement simpleStatement;
		try {
			simpleStatement = conn.createStatement();

			String query = "SELECT *" + " FROM users" + " WHERE username = " + loginCred.getUsername()
					+ " AND password = " + loginCred.getPassword();
			ResultSet rs = simpleStatement.executeQuery(query);

			if (rs.first()) {
				Integer id = rs.getInt("id");

				Integer role = 0;
				if (loginCred.getRole() == UserRole.SELLER) {
					role = 1;
				}

				if (role == 0) {
					query = "UPDATE users" + " SET as_buyer = " + 1 + " WHERE id = " + id;
				} else {
					query = "UPDATE users" + " SET as_seller = " + 1 + " WHERE id = " + id;
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
			return WebMessage.serialize(new ErrorMessage("Wrong message... waiting LogoutRequest"));
		}

		LogoutRequest logoutRequest = (LogoutRequest) obj;
		LoginCred cred = logoutRequest.getCred();

		try {
			Statement simpleStatement = conn.createStatement();

			if (cred.getRole() == UserRole.BUYER) {
				String query = "UPDATE users" + " SET as_buyer = " + 0 + " WHERE username = " + cred.getId();
				simpleStatement.executeUpdate(query);

				query = "UPDATE services" + " SET active = " + 0 + " WHERE id = " + cred.getId() + " AND user_role = "
						+ cred.getRole();
				simpleStatement.executeUpdate(query);
			} else {
				String query = "UPDATE users" + " SET as_seller = " + 0 + " WHERE username = " + cred.getId();
				simpleStatement.executeUpdate(query);

				query = "UPDATE services" + " SET active = " + 0 + " WHERE id = " + cred.getId() + " AND user_role = "
						+ cred.getRole();
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

		byte[] res;
		Object obj = WebMessage.deserialize(req);

		if (!(obj instanceof LaunchOfferRequest)) {
			System.out.println("[WebServer:logout] Wrong message... waiting LaunchOfferRequest");
			return WebMessage.serialize(new ErrorMessage("Wrong message... waiting LaunchOfferRequest"));
		}

		LaunchOfferRequest launchOfferReq = (LaunchOfferRequest) obj;
		LoginCred cred = launchOfferReq.getCred();
		Service service = launchOfferReq.getService();

		Integer role = 0;
		if (cred.getRole() == UserRole.SELLER) {
			role = 1;
		}

		try {
			Statement st = conn.createStatement();
			String query = "SELECT *" + " FROM services" + " WHERE user_id = " + cred.getId() + " AND name = "
					+ service.getName() + " AND user_role = " + role;
			ResultSet rs = st.executeQuery(query);

			if (rs.first()) {
				// Activez un serviciu
				query = "UPDATE services SET active = " + 1 + " WHERE user_id = " + cred.getId() + " AND name = "
						+ service.getName() + " AND user_role = " + role;

				query = "SELECT * FROM services s JOIN users u ON s.user_id = u.id WHERE s.name = " + service.getName()
						+ " AND s.user_role = " + UserRole.SELLER + " AND s.active = " + 1;
				rs = st.executeQuery(query);

				ArrayList<UserEntry> sellers = new ArrayList<UserEntry>();
				while (rs.next()) {
					UserEntry userEntry = new UserEntry();
					userEntry.setUsername(rs.getString("u.username"));
					userEntry.setName(rs.getString("u.first_name") + rs.getString("u.last_name"));
					InetSocketAddress address = new InetSocketAddress(rs.getString("u.address"), rs.getInt("u.port"));
					userEntry.setAddress(address);

					sellers.add(userEntry);
				}
				rs.close();
				service.setUsers(sellers);
			} else {
				// Adaug un serviciu, si daca role-ul e SELLER il activez
				if (cred.getRole() == UserRole.SELLER) {
					query = "INSERT INTO services(name, time, price, user_id, active, user_role) VALUES ("
							+ service.getName() + ", " + service.getTime() + ", " + service.getPrice() + ", "
							+ cred.getId() + ", " + 1 + ", " + cred.getRole() + ")";
					st.executeUpdate(query);

					query = "SELECT * FROM services s JOIN users u ON s.user_id = u.id WHERE s.name = "
							+ service.getName() + " AND s.user_role = " + UserRole.BUYER + " AND s.active = " + 1;
					rs = st.executeQuery(query);

					ArrayList<UserEntry> buyers = new ArrayList<UserEntry>();
					while (rs.next()) {
						UserEntry userEntry = new UserEntry();
						userEntry.setUsername(rs.getString("u.username"));
						userEntry.setName(rs.getString("u.first_name") + rs.getString("u.last_name"));
						InetSocketAddress address = new InetSocketAddress(rs.getString("u.address"),
								rs.getInt("u.port"));
						userEntry.setAddress(address);

						buyers.add(userEntry);
					}
					rs.close();
					service.setUsers(buyers);
				} else {
					query = "INSERT INTO services(name, time, price, user_id, active, user_role) VALUES ("
							+ service.getName() + ", " + service.getTime() + ", " + service.getPrice() + ", "
							+ cred.getId() + ", " + 0 + ", " + cred.getRole() + ")";
					st.executeUpdate(query);
				}
			}
		} catch (SQLException e) {
			System.out.println("Something bad happend at server");
			e.printStackTrace();
		}

		System.out.println("[WebServer:logout] End");
		return WebMessage.serialize(new LaunchOfferResponse(service));
	}

	public byte[] dropOffer(byte[] byteReq) {
		System.out.println("Begin");

		Object obj = WebMessage.deserialize(byteReq);
		if (!(obj instanceof DropOfferRequest)) {
			System.out.println("[WebServer:dropOffer] Wrong message... waiting DropOfferRequest");
			// TODO: Fix this
			// return WebMessage.serialize(new
			// ErrorMessage("Wrong message... waiting LaunchOfferRequest"));
			return WebMessage.serialize(new OkResponse());
		}
		
		DropOfferRequest req = (DropOfferRequest) obj;
		
		try {
		Statement st = conn.createStatement();
		String query = "SELECT id FROM users WHERE username = " + req.getUsername();
		ResultSet rs = st.executeQuery(query);
		
		Integer id = rs.getInt("id");

		Integer role = 0;
		if (req.getUserRole() == UserRole.SELLER) {
			role = 1;
		}
		
		query = "UPDATE services SET active = " + 0 + " WHERE name = " + req.getServiceName() + " AND user_id = " + id + " AND user_role = " + role;
		st.executeUpdate(query);
		} catch (SQLException e) {
			System.out.println("Something bad happend at server");
			e.printStackTrace();
		}

		System.out.println("[WebServer: dropOffer] End");
		return WebMessage.serialize(new OkResponse());
	}

	public byte[] getProfile(byte[] byteReq) {
		System.out.println("[WebServer:getProfile] Begin");
		Object obj = WebMessage.deserialize(byteReq);

		if (!(obj instanceof GetProfileRequest)) {
			System.out.println("[WebServer:getProfile] Wrong message... waiting GetProfileRequest");
			// return WebMessage.serialize(new
			// ErrorMessage("Wrong message... waiting LaunchOfferRequest"));
			return WebMessage.serialize(new GetProfileResponse(null));
		}
		GetProfileRequest req = (GetProfileRequest) obj;

		UserProfile profile = null;
		try {
			Statement st = conn.createStatement();
			String query = "SELECT * FROM users WHERE username = " + req.getUsername();
			ResultSet rs = st.executeQuery(query);

			if (rs.next()) {
				profile = new UserProfile();
				profile.setUsername(rs.getString("username"));
				profile.setFirstName(rs.getString("first_name"));
				profile.setLastName(rs.getString("last_name"));
				// TODO: Anybody can see anyone's password;
				profile.setPassword(rs.getString("password"));
				profile.setLocation(rs.getString("location"));
				// profile.setAvatar(avatar);
			}
		} catch (SQLException e) {
			profile = null;
			System.out.println("Something bad happend at server");
			e.printStackTrace();
		}

		System.out.println("[WebServer:getProfile] End");
		return WebMessage.serialize(new GetProfileResponse(profile));
	}

	public byte[] setProfile(byte[] byteReq) {
		System.out.println("[WebServer:setProfile] Begin");

		Object obj = WebMessage.deserialize(byteReq);
		if (!(obj instanceof SetProfileRequest)) {
			System.out.println("[WebServer:setProfile] Wrong message... waiting SetProfileRequest");
			// TODO: Fix this
			// return WebMessage.serialize(new
			// ErrorMessage("Wrong message... waiting LaunchOfferRequest"));
			return WebMessage.serialize(new OkResponse());
		}

		SetProfileRequest req = (SetProfileRequest) obj;
		UserProfile profile = req.getUserProfile();

		try {
			Statement st = conn.createStatement();
			String query = "UPDATE users SET first_name = " + profile.getFirstName() + ", last_name = "
					+ profile.getLastName() + ", password = " + profile.getPassword() + ", location = "
					+ profile.getLocation() + " WHERE username = " + profile.getUsername();
			st.executeUpdate(query);
		} catch (SQLException e) {
			System.out.println("Something bad happend");
			e.printStackTrace();
		}
		System.out.println("[WebServer:setProfile] End");
		return WebMessage.serialize(new OkResponse());
	}

	public byte[] registerProfile(byte[] byteReq) {
		System.out.println("Begin");
		
		Object obj = WebMessage.deserialize(byteReq);
		if (!(obj instanceof RegisterProfileRequest)) {
			System.out.println("[WebServer:registerProfile] Wrong message... waiting RegisterProfileRequest");
			// TODO: Fix this
			// return WebMessage.serialize(new
			// ErrorMessage("Wrong message... waiting LaunchOfferRequest"));
			return WebMessage.serialize(new OkResponse());
		}
		RegisterProfileRequest req = (RegisterProfileRequest) obj;
		UserProfile profile = req.getUserProfile();

		try {
			Statement st = conn.createStatement();
			String query = "INSERT INTO users(username, first_name, last_name, password, location) VALUES (" + profile.getUsername() + ", " + profile.getFirstName() + ", " + profile.getLastName() + ", " + profile.getPassword() + ", "
					+ profile.getLocation() + ")";
			st.executeUpdate(query);
		} catch (SQLException e) {
			System.out.println("Something bad happend");
			e.printStackTrace();
		}

		System.out.println("[WebServer:registerProfile] End");
		return WebMessage.serialize(new OkResponse());
	}
}
