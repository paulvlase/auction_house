package network;

import interfaces.Command;

import java.nio.channels.SocketChannel;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import data.Message;
import data.QueueThread;

/**
 * 
 * @author Ghennadi Procopciuc
 */
public class NetworkSendEvents extends QueueThread<SocketChannel, Message> {
	private static Logger	logger	= Logger.getLogger(NetworkSendEvents.class);

	private NetworkDriver	driver;

	public NetworkSendEvents(NetworkDriver driver) {
		super("NetworkSendEvents");
		// logger.setLevel(Level.OFF);

		this.driver = driver;
	}

	protected synchronized void process(List<Command> events) {
		logger.debug("Begin");
		logger.debug("End");
	}

	@Override
	protected synchronized void process() {
		logger.debug("Begin");

		Map.Entry<SocketChannel, Message> job = getJob();
		if (job == null) {
			logger.debug("End null job");
			return;
		}

		logger.debug("Send message: " + job.getValue());
		driver.sendData(job.getValue(), job.getKey());
		logger.debug("End");
	}
}
