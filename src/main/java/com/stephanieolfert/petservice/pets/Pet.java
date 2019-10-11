package com.stephanieolfert.petservice.pets;

import java.util.logging.Logger;

public class Pet {
	
	private static final Logger LOG = Logger.getLogger( Pet.class.getName() );

	private long id;
	private String name;
	private int type;
	private int age;
	private char sex;
	private String description;
	private String owner_email;
	private String image_url;
	
	private final int TYPE_CAT = 0;
	private final int TYPE_DOG = 1;
	private final int TYPE_BIRD = 2;
	private final int TYPE_FISH = 3;
	
	public Pet() {
	}
	
	public Pet(String name, int type, int age, char sex, String description, String owner_email, String image_url) {
		super();
		this.name = name;
		this.type = type;
		this.age = age;
		this.sex = sex;
		this.description = description;
		this.owner_email = owner_email;
		this.image_url = image_url;
	}

	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public char getSex() {
		return sex;
	}
	public void setSex(char sex) {
		this.sex = sex;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getOwner_email() {
		return owner_email;
	}
	public void setOwner_email(String owner_email) {
		this.owner_email = owner_email;
	}
	public String getImage_url() {
		return image_url;
	}
	public void setImage_url(String image_url) {
		this.image_url = image_url;
	}
	
	
	public boolean validateFields() {
		boolean isValid = true;
		
		if (type == 0) {
			LOG.warning("Type of pet is invalid");
			isValid = false;
		}
		if (age < 0) {
			LOG.warning("Age cannot be less than 0");
			isValid = false;
		}
		if (Character.toLowerCase(sex) != 'm' && Character.toLowerCase(sex) != 'f') {
			LOG.warning("Sex must be either 'm' or 'f'");
			isValid = false;
		}
		if (description == null || description.length() == 0) {
			LOG.warning("Must include a description");
			isValid = false;
		}
		if (owner_email == null || owner_email.length() == 0) {
			LOG.warning("Must include owner email");
			isValid = false;
		}
		if (image_url == null || image_url.length() == 0) {
			LOG.warning("Must include image url");
			isValid = false;
		}
		
		return isValid;
	}

	@Override
	public String toString() {
		return "Pet [id=" + id + ", name=" + name + ", type=" + type + ", age=" + age + ", sex=" + sex
				+ ", description=" + description + ", owner_email=" + owner_email + ", image_url=" + image_url
				+ ", TYPE_CAT=" + TYPE_CAT + ", TYPE_DOG=" + TYPE_DOG + ", TYPE_BIRD=" + TYPE_BIRD + ", TYPE_FISH="
				+ TYPE_FISH + ", getId()=" + getId() + ", getName()=" + getName() + ", getType()=" + getType()
				+ ", getAge()=" + getAge() + ", getSex()=" + getSex() + ", getDescription()=" + getDescription()
				+ ", getOwner_email()=" + getOwner_email() + ", getImage_url()=" + getImage_url()
				+ ", validateFields()=" + validateFields() + ", getClass()=" + getClass() + ", hashCode()=" + hashCode()
				+ ", toString()=" + super.toString() + "]";
	}
	
	
}
