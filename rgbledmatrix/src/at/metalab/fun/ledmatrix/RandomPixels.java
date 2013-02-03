package at.metalab.fun.ledmatrix;

import java.util.Random;

public class RandomPixels extends AbstractExample {
	private final static Random RANDOM = new Random(System.currentTimeMillis());

	public static void main(String[] args) throws Exception {
		new RandomPixels().execute(args);
	}

	@Override
	protected void init(String[] args) throws Exception {
		openLedMatrix("127.0.0.1", 1337); // connect to the daemon
	}

	@Override
	protected void loop() throws Exception {
		Pixel pixel;
		for (int x = 0; x < 24; x++) {
			for (int y = 0; y < 24; y++) {
				pixel = frame[x][y];
				pixel.r = RANDOM.nextInt(256);
				pixel.g = RANDOM.nextInt(256);
				pixel.b = RANDOM.nextInt(256);
			}
		}

		display(); // display the frame
		quit(); // exit gracefully
	}
}
