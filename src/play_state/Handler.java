package play_state;

import java.util.ArrayList;

public class Handler {
	
	/*
	 * Az összes GameObjectet tároló ArrayList
	 */
	ArrayList<GameObject> object = new ArrayList<GameObject>();
	
	/**
	 * Meghívja az összes GameObject tick metódusát
	 */
	public void tick() {
		GameObject tempObject;
		for (int i = 0; i < object.size(); i++) {
			tempObject = object.get(i);
			
			tempObject.tick();

		}
	}
	
	/**
	 * Hozzáad egy GameObjectet az object tömbhöz
	 * @param o A hozzáadandó GameObject
	 */
	public void addObject(GameObject o) {
		object.add(o);
	}
	
	/**
	 * Eltávolít egy GameObjectet az object tömbbõl
	 * @param o Az eltávolítandó GameObject
	 */
	public void removeObject(GameObject o) {
		object.remove(o);
	}
	
}
