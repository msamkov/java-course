package net.azib.java.students.t074918;

import net.azib.java.students.t074918.Animal;

/**
 * Dog world dogs descriptions
 *
 * @author Mart Mangus
 */
public class Dog extends Animal {

	/**
	 * @param name
	 * @param age
	 */
	public Dog(String name, int age) {
		super(name, age);
	}

	@Override
	public void makeNoise() {
		System.out.println("auh, auh!");
	}

}
