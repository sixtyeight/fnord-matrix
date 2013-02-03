package at.metalab.fun.ledmatrix;

/**
 * @author m68k
 * 
 */
public class _COPY_ME_ extends AbstractExample {

	/**
	 * Entry point. Program arguments will be passed on to the init() method.
	 * 
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		new _COPY_ME_().execute(args);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see at.metalab.fun.ledmatrix.AbstractExample#init(java.lang.String[])
	 */
	@Override
	protected void init(String[] args) throws Exception {
		openLedMatrix("127.0.0.1", 1337); // connect to the daemon
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see at.metalab.fun.ledmatrix.AbstractExample#loop()
	 */
	@Override
	protected void loop() throws Exception {
		set(0, 0, 255, 0, 0); // red dot, upper left corner
		set(23, 0, 0, 255, 0); // green dot, upper right corner
		set(0, 23, 0, 0, 255); // blue dot, lower left corner
		set(23, 23, 255, 255, 255); // white dot, lower right corner

		display(); // display the frame with the dots

		Thread.sleep(5000); // wait for 5 seconds

		clear(); // clear the frame
		display(); // display the blank frame

		quit(); // exit gracefully
	}
}
