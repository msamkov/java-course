package net.azib.java.students.t030636.first;

/**
 * Animal
 *
 * @author t030636
 */
public abstract class Animal implements IAnimal {
	private String name ;
	private byte age;

	/* (non-Javadoc)
	 * @see net.azib.java.students.t030636.first.IAnimal#getAge()
	 */
	public byte getAge() {
		return age;
	}
	
	public void growUp() {
		age++ ;
	}

	/* (non-Javadoc)
	 * @see net.azib.java.students.t030636.first.IAnimal#getName()
	 */
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setAge(byte age) {
		this.age = age;
	}

	public Animal(String name, byte age) {
		this.name = name;
		this.age = age;
	}


	/* (non-Javadoc)
	 * @see net.azib.java.students.t030636.first.IAnimal#getType()
	 */
	public abstract String getType();
	
	@Override
	public String toString() {
		return "I'm "+ getType()+ ", my name is " + name + " and i'm " + age + " years old.";
	}
		
}
