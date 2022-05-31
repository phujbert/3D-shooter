package leaderboard_state;

import play_state.GameObject;

public class User implements Comparable<User>{

	//A felhszn�l� neve
	private String name;
	//A felhaszn�l� �ltal el�rt pontsz�m
	private int score;
	
	public User() {}
	
	/**
	 * Az oszt�ly konstruktora
	 * @param name A user neve
	 * @param score A user �ltal el�rt pont
	 */
	public User(String name, int score) {
		this.name = name;
		this.score = score;
		
	}
	
	/**
	 * �sszehasonl�t k�t User objektumot a pont attributumuk alapj�n
	 * @param u A user amihhez hasonl�tja ezt az objektumot
	 */
	public int compareTo(User u) {
		return u.getScore() - this.score;
	}
	
	
	public int getScore() { return this.score; }
	public void setScore(int score) { this.score = score; }
	public String getName() { return this.name; }
	public void setName(String name) { this.name = name; }
	
}
