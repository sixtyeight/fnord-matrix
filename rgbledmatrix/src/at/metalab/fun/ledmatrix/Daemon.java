package at.metalab.fun.ledmatrix;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Logger;

/**
 * Simple Daemon which acts as a bridge to the RGB-LED-Matrix.
 * This needs to be run as root to be able to access the.
 * @author m68k
 *
 */
public class Daemon {

	private final static Logger LOG = Logger.getLogger(Daemon.class
			.getCanonicalName());

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		ServerSocket server = new ServerSocket(1337);

		/*
		 *  # set baudrate on tty
		 *  sudo stty -F /dev/ttyACM0 9600
		 */
		final FileOutputStream out = new FileOutputStream(new File(
				"/dev/ttyACM0"));

		while (true) {
			final Socket socket = server.accept();
			LOG.info("client connected");

			new Thread() {
				public void run() {
					byte[] data = new byte[24 * 24 * 3 * 10];

					try {
						InputStream inputStream = socket.getInputStream();

						while (true) {
							int bytesRead = inputStream.read(data);

							out.write(data, 0, bytesRead);
							out.flush();
						}
					} catch (Exception exception) {
						LOG.severe("caught "
								+ exception.getClass().getCanonicalName()
								+ ": " + exception.getMessage());
					} finally {
						LOG.info("client disconnected");
					}
				};
			}.start();
		}
	}

}
