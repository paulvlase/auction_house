package webServiceClient;

import java.util.ArrayList;
import java.util.Map;
import java.util.Random;

import data.Service;
import data.UserEntry;
import interfaces.Command;
import interfaces.MediatorWeb;

/**
 * Ofertele sterse in urma disparitie unui nou utilizator, sau a dezactivarii
 * de catre un cumparator a unor noi oferte.
 *
 * @author pvlase
 *
 */
public class LogOutEvent implements Command {
	private MediatorWeb med;
	private ArrayList<String> usersCache;
	
	private static Random random = new Random();

	public LogOutEvent(MediatorWeb med, ArrayList<String> usersCache) {
		this.med = med;
		this.usersCache = usersCache;
	}

	public void execute() {
		if (usersCache.size() == 0) {
			return;
		}

		Integer index = random.nextInt(usersCache.size());

		String username = usersCache.get(index);
		usersCache.remove(index);
		
		for (Map.Entry<String, Service> offer : med.getOffers().entrySet()) {
			Service service = offer.getValue();
			ArrayList<UserEntry> users = service.getUsers();
			
			if (users == null) {
				continue;
			}

			for (UserEntry user: users) {
				if (user.getName().equals(username)) {
					users.remove(users);
					break;
				}
			}
			
			med.changeServiceNotify(service);
		}
	}
}
