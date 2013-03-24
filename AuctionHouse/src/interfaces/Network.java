package interfaces;

import data.Pair;
import data.Service;

/**
 * Network interface.
 * 
 * @author Paul Vlase <vlase.paul@gmail.com>
 */
public interface Network {
	public boolean startTransfer(Pair<Service, Integer> pair);
}
