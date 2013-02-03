package at.metalab.fun.ledmatrix;

/**
 * Simple pixel class. Each pixel has an r, g and b value (0 - 255).
 * @author m68k
 *
 */
public class Pixel {
	public int r;
	public int g;
	public int b;

	public Pixel(int r, int g, int b) {
		super();
		this.r = r;
		this.g = g;
		this.b = b;
	}

	@Override
	public String toString() {
		return "";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + b;
		result = prime * result + g;
		result = prime * result + r;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Pixel other = (Pixel) obj;
		if (b != other.b)
			return false;
		if (g != other.g)
			return false;
		if (r != other.r)
			return false;
		return true;
	}
}
