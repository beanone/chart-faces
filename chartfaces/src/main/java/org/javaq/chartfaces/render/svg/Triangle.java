package org.javaq.chartfaces.render.svg;


/**
 * A utility class that can be used to conveniently create triangle shapes that
 * has a circumcenter at (0,0).
 * 
 * To create a triangle, first call the construct with a size. This will create
 * a equal-lateral triangle using the size as the radius of the smallest circle
 * surround the triangle.
 * 
 * If an isosceles triangle is needed, call the setTopAngle() or setBaseAngle()
 * methods. The top angle is the angle opposing the base of the triangle, and
 * the base angle, an angle on either side of the base. Regardless of which
 * angle is set, the resulting triangle will still have a circumcenter at (0,0)
 * and the base will be parallel to the X-axis.
 * 
 * For any other triangle, set both the top angle and the base angle. The
 * resulting triangle will always have its base parallel to the X-axis and the
 * top vertex in the first quadrant of the coordinate system.
 * 
 * To return a triangle back to isosceles, set one of its angles to 0 and leave
 * the other non-zero.
 * 
 * To return a triangle back to equal-lateral, set both angle to 0.
 * 
 * If a negative value is set to any of the angles, the absolute value will be
 * used instead.
 * 
 * Call the center method to get the Paths that has the triangle centered at
 * (0,0).
 * 
 * To flip a triangle, use one of the
 * {@link PathsUtil#flip(double, double, Paths...)} or
 * {@link PathsUtil#flip(double, Paths...)} methods on the resulting
 * {@link Paths}.
 * 
 * To rotate a triangle, use the
 * {@link PathsUtil#rotate(double, double, double, Paths...)} method on the
 * resulting {@link Paths}.
 * 
 * To translate a triangle, use the
 * {@link PathsUtil#translate(double, double, Paths...)} method on the resulting
 * {@link Paths}.
 * 
 * @see PathsUtil
 * @see Paths
 * @author Hongyan Li
 * 
 */
public class Triangle {
	private static final double UNIT_RADIAN = Math.PI / 180.0;
	private double baseAngle;
	private final double radius;
	private double topAngle;

	/**
	 * Construct this given the radius of the circle enclosing this triangle.
	 * 
	 * @param radius
	 *            must be a number greater than 0.
	 */
	public Triangle(final double radius) {
		if (radius <= 0) {
			throw new IllegalArgumentException("Invalid radius: " + radius
					+ "!");
		}
		this.radius = radius;
	}

	public double getBaseAngle() {
		return this.baseAngle;
	}

	/**
	 * Calculates the paths for the triangle and returns it. The base of the
	 * triangle is parallel to the X-axis. The topAngle is used as the angle of
	 * the top vertex and the baseAngle is used as the base angle on the right
	 * side.
	 * 
	 * @return the paths for the triangle.
	 */
	public Paths getPaths() {
		final double[] xArray = new double[3];
		final double[] yArray = new double[3];

		double angleTop = this.topAngle, angleBase = this.baseAngle;
		if (this.topAngle == 0 && this.baseAngle == 0) {
			angleTop = 60.0;
			angleBase = 60.0;
		} else if (this.topAngle == 0) {
			angleTop = 180.0 - 2.0 * this.baseAngle;
		} else if (this.baseAngle == 0) {
			angleBase = 90.0 - this.topAngle / 2.0;
		}

		final double baseRadian = (180.0 - angleTop) * Triangle.UNIT_RADIAN;
		final double b = this.radius * Math.sin(baseRadian);
		final double a = this.radius * Math.cos(baseRadian);
		xArray[1] = -b;
		xArray[2] = b;
		yArray[2] = a;
		yArray[1] = yArray[2];

		double bigBaseAngle = angleBase;
		double smallBaseAngle = 180.0 - angleTop - angleBase;
		boolean flip = false;
		if (angleBase < smallBaseAngle) {
			bigBaseAngle = smallBaseAngle;
			smallBaseAngle = angleBase;
			flip = true;
		}
		final double longSide = b * 2.0 / Math.sin(angleTop * Triangle.UNIT_RADIAN)
				* Math.sin(bigBaseAngle * Triangle.UNIT_RADIAN);
		final double smallRadian = smallBaseAngle * Triangle.UNIT_RADIAN;
		xArray[0] = longSide * Math.cos(smallRadian) - b;
		yArray[0] = longSide * Math.sin(smallRadian) + a;

		final Paths paths = Paths.newPaths(xArray, yArray);

		if (flip) {
			PathsUtil.flip(0, paths);
		}

		return paths;
	}

	public double getRadius() {
		return this.radius;
	}

	public double getTopAngle() {
		return this.topAngle;
	}

	public void setBaseAngle(final double angle) {
		this.baseAngle = Math.abs(angle);
	}

	public void setTopAngle(final double angle) {
		this.topAngle = Math.abs(angle);
	}
}