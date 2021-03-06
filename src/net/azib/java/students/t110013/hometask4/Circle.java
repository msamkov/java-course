package net.azib.java.students.t110013.hometask4;

import net.azib.java.lessons.collections.Shape;

/**
 * @author Vadim
 */
public class Circle extends Shape {
	private double radius;

	public Circle(double radius) {
		if (radius < 0)
			throw new IllegalArgumentException("Negative integers are not supported!");

		this.radius = radius;
	}

	@Override
	public boolean equals(Object o) {
		if (o == this)
			return true;
		if (!(o instanceof Circle))
			return false;
		return Double.compare(radius, ((Circle)o).radius) == 0;
	}

	@Override
	public int hashCode() {
		long temp = radius != +0.0d ? Double.doubleToLongBits(radius) : 0L;
		return (int) (temp ^ (temp >>> 32));
	}

	@Override
	public String toString() {
		return "Circle(radius " + radius + ", area " + area() + ", circumference " + 2 * Math.PI * radius + ")";
	}

	@Override
	public double area() {
		return Math.PI * radius * radius;
	}
}
