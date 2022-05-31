package leaderboard_state;

import play_state.GameObject;

public class User implements Comparable<User>{

	//A felhsználó neve
	private String name;
	//A felhasználó által elért pontszám
	private int score;
	
	public User() {}
	
	/**
	 * Az osztály konstruktora
	 * @param name A user neve
	 * @param score A user által elért pont
	 */
	public User(String name, int score) {
		this.name = name;
		this.score = score;
		
	}
	
	/**
	 * Összehasonlít két User objektumot a pont attributumuk alapján
	 * @param u A user amihhez hasonlítja ezt az objektumot
	 */
	public int compareTo(User u) {
		return u.getScore() - this.score;
	}
	
	
	public int getScore() { return this.score; }
	public void setScore(int score) { this.score = score; }
	public String getName() { return this.name; }
	public void setName(String name) { this.name = name; }
	
}
