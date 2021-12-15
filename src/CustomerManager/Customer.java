package CustomerManager;

import java.io.Serializable;

public class Customer implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private String name;
	private String jumin;
	private String tel;
	private boolean gender;
	private String hobby;
	
	public Customer() {}

	public Customer(String name, String jumin, String tel, boolean gender, String hobby) {
		this.name = name;
		this.jumin = jumin;
		this.tel = tel;
		this.gender = gender;
		this.hobby = hobby;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getJumin() {
		return jumin;
	}

	public void setJumin(String jumin) {
		this.jumin = jumin;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public boolean isGender() {
		return gender;
	}

	public void setGender(boolean gender) {
		this.gender = gender;
	}

	public String getHobby() {
		return hobby;
	}

	public void setHobby(String hobby) {
		this.hobby = hobby;
	}

	@Override
	public String toString() {
		return "Customer [ " + name + " ]";
	}

}
