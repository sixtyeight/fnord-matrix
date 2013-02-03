package at.metalab.fun.ledmatrix;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.Socket;

/**
 * Abstract class for subclassing by the examples.
 * 
 * @see _COPY_ME_
 * @author m68k
 * 
 */
public abstract class AbstractExample {
	private OutputStream out;
	private Simulator simulator;

	private boolean quit;
	private boolean disableSimulator;

	protected Pixel[][] frame = createFrame();

	/**
	 * Entry method for the examples.
	 * 
	 * @param args
	 * @throws Exception
	 */
	public void execute(String[] args) throws Exception {
		try {
			init(args);
			configure(args);

			while (!quit) {
				loop();
			}
		} finally {
			tearDown();
		}
	}

	/**
	 * Setup some stuff
	 * 
	 * @param args
	 */
	private void configure(String[] args) {
		if (!disableSimulator) {
			simulator = new Simulator();
		}
	}

	/**
	 * Initialize your example here. This method will be called once before
	 * entering the loop() method.
	 * 
	 * @param args
	 * @throws Exception
	 */
	protected abstract void init(String[] args) throws Exception;

	/**
	 * This method will be called in succession unless you call quit().
	 * 
	 * @throws Exception
	 */
	protected abstract void loop() throws Exception;

	private static byte[] FNORD_BYTES = null;

	static {
		try {
			FNORD_BYTES = "FNORD".getBytes("UTF-8");
		} catch (UnsupportedEncodingException unsupportedEncodingException) {
			throw new RuntimeException(unsupportedEncodingException);
		}
	}

	/**
	 * Displays the frame at the LED matrix.
	 * 
	 * @throws IOException
	 */
	protected void display() throws IOException {
		if (!disableSimulator) {
			simulator.display(frame);
		}

		if (out == null) {
			return;
		}

		out.write(FNORD_BYTES);

		Pixel pixel;

		for (int x = 23; x >= 0; x--) {
			if (x % 2 == 0) {
				for (int y = 0; y < 24; y++) {
					pixel = frame[y][x];
					out.write(pixel.r);
					out.write(pixel.g);
					out.write(pixel.b);
				}
			} else {
				for (int y = 23; y >= 0; y--) {
					pixel = frame[y][x];
					out.write(pixel.r);
					out.write(pixel.g);
					out.write(pixel.b);
				}
			}
		}

		out.flush();
	}

	/**
	 * Creates a new frame with all pixels set to black.
	 * 
	 * @return
	 */
	protected Pixel[][] createFrame() {
		Pixel[][] frame = new Pixel[24][24];

		for (int x = 0; x < 24; x++) {
			for (int y = 0; y < 24; y++) {
				frame[x][y] = new Pixel(0, 0, 0);
			}
		}

		return frame;
	}

	/**
	 * Sets all pixels to black.
	 * 
	 * @return
	 */
	protected void clear() {
		Pixel pixel;

		for (int x = 0; x < 24; x++) {
			for (int y = 0; y < 24; y++) {
				pixel = frame[x][y];
				pixel.r = 0;
				pixel.g = 0;
				pixel.b = 0;
			}
		}
	}

	/**
	 * Sets the color of the pixel at x/y to the new r, g and b values.
	 * 
	 * @param x
	 * @param y
	 * @param r
	 * @param g
	 * @param b
	 */
	protected void set(int x, int y, int r, int g, int b) {
		Pixel pixel = frame[x][y];
		pixel.r = r;
		pixel.g = g;
		pixel.b = b;
	}

	/**
	 * Exits the example after returning from loop()
	 */
	protected void quit() {
		quit = true;
	}

	/**
	 * Connects to the Daemon.
	 * 
	 * @param host
	 * @param port
	 * @throws IOException
	 */
	protected void openLedMatrix(String host, int port) throws IOException {
		Socket socket = new Socket(host, port);
		out = socket.getOutputStream();
	}

	/**
	 * Closes all resources
	 * 
	 * @throws Exception
	 */
	protected void tearDown() throws Exception {
		try {
			out.close();
		} catch (Exception ignore) {
		}

		try {
			simulator.close();
		} catch (Exception ignore) {
		}
	}
}
