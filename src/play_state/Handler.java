package play_state;

import java.util.ArrayList;

public class Handler {
	
	/*
	 * Az �sszes GameObjectet t�rol� ArrayList
	 */
	ArrayList<GameObject> object = new ArrayList<GameObject>();
	
	/**
	 * Megh�vja az �sszes GameObject tick met�dus�t
	 */
	public void tick() {
		GameObject tempObject;
		for (int i = 0; i < object.size(); i++) {
			tempObject = object.get(i);
			
			tempObject.tick();

		}
	}
	
	/**
	 * Hozz�ad egy GameObjectet az object t�mbh�z
	 * @param o A hozz�adand� GameObject
	 */
	public void addObject(GameObject o) {
		object.add(o);
	}
	
	/**
	 * Elt�vol�t egy GameObjectet az object t�mbb�l
	 * @param o Az elt�vol�tand� GameObject
	 */
	public void removeObject(GameObject o) {
		object.remove(o);
	}
	
}
