package net.azib.java.students.t104887.Lecture3;

/**
 * Created by IntelliJ IDEA.
 * User: BJU
 * Date: 22.02.11
 * Time: 0:59
 * To change this template use File | Settings | File Templates.
 */
public class Sheep extends Animal
{
	public Sheep(String name)
	{
		this.name=name;
		System.out.println("Named: " + this.name);
	}

	public void sayHello()
	{
		System.out.println("Sheep "+name+" said: Beee!");
	}
}
