package data;

import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.JProgressBar;

/**
 * If user field is null, then this offer is inactive, otherwise it's active. If
 * time is 0, this offer is expired. If price is 0, this offer is a demand,
 * otherwise it's supply.
 * 
 * @author Ghennadi Procopciuc
 */
public class Service {
	private String					name;
	private long					time;
	private double					price;
	private ArrayList<UserEntry>	users;

	public Service(String name, ArrayList<UserEntry> users) {
		this.name = name;
		this.users = users;
		this.time = 0;
		this.price = 0;
	}

	public Service(String serviceName) {
		this(serviceName, null);
	}

	public void addUserEntry(UserEntry user) {
		if (users == null) {
			users = new ArrayList<UserEntry>();
		}

		users.add(user);
	}

	public ArrayList<UserEntry> getUsers() {
		return users;
	}

	public void setUsers(ArrayList<UserEntry> users) {
		this.users = users;
	}

	public String getName() {
		return name;
	}

	public void setName(String serviceName) {
		this.name = serviceName;
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public ArrayList<ArrayList<Object>> getAsTable() {
		ArrayList<ArrayList<Object>> data = new ArrayList<ArrayList<Object>>();
		ArrayList<Object> row;
		Boolean first = true;

		if (users == null) {
			/**
			 * Columns from row : Service Name, Status, User, Offer made, Time,
			 * Price
			 */
			row = new ArrayList<Object>(Arrays.asList(getName(), "Inactive", "", "", "", ""));
			data.add(row);
		} else {
			for (UserEntry user : users) {
				row = new ArrayList<Object>();
				if (first) {
					first = false;

					row = new ArrayList<Object>(Arrays.asList(getName(), "Active", user.getName(),
							user.getOffer(), user.getTime(), user.getPrice()));
				} else {
					row = new ArrayList<Object>(Arrays.asList("", "", user.getName(),
							user.getOffer(), user.getTime(), user.getPrice()));
				}

				data.add(row);
			}
		}
		
		return data;
	}

	@Override
	public boolean equals(Object arg0) {
		if (!(arg0 instanceof Service)) {
			return false;
		}

		return ((Service) arg0).getName().equals(name);
	}
}
