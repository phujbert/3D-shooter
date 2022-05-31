package leaderboard_state;

import game_files.*;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.jdom2.Attribute;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.JDOMParseException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

public class LeaderBoardState extends GameState {

	//A beolvasott xml dokumentum
	private Document jdomDoc;
	//Az xml dokumentum root element-je
	private Element root;
	//Az element-ek listája
	private List<Element> listOfUsers;
	//Az element-ekbõl létrehozott User-ek listája
	private List <User> userList;
	
	//Ennyi oldalon fér el a userList lista
	private int pages;
	//A jelenlegi oldal
	private int current = 0;
	
	public LeaderBoardState(GameStateManager gsm) {
		super(gsm);

	}
	
	/**
	 * Meghívja az osztály az addUser metódusát a paraméterben megkapott user-el
	 * @param gsm A GameStateManager
	 * @param user A user
	 */
	public LeaderBoardState(GameStateManager gsm, User user) {
		super(gsm);
		this.addUser(user);
	}

	public void init() {
		this.readLeaderBoard();
		this.updateList();
		
	}
	
	/**
	 * Beolvassa az xml fájlt
	 */
	private void readLeaderBoard() {
		try {
			SAXBuilder builder = new SAXBuilder();
			File xmlFile = new File("leaderboard.xml");
	        jdomDoc = (Document) builder.build(xmlFile);
	        root = jdomDoc.getRootElement();
	        userList = new ArrayList<User>();
		} catch (JDOMException | IOException e) {
			JDOMParseException a = (JDOMParseException)e;
			System.out.println(a.getCause());
        }
	}
	
	
	/**
	 * Feltölti a userList-et a lostOfUser elemeivel
	 */
	private void updateList() {
		userList.clear();
        listOfUsers = root.getChildren("User");
		for (Element userElement: listOfUsers) {
        	User user = new User();
            user.setScore(Integer.parseInt(userElement.getChildText("score")));
            user.setName(userElement.getChildText("name"));
            userList.add(user);
        }
		pages = (int)(Math.floor(userList.size() / 10)) + 1;
		if (userList.size() > 0) Collections.sort(userList);

	}
	
	
	/**
	 * Kiírja a listOfuser elemiet az xml fájlba
	 */
	private void writeLeaderBoard() {
		try {
			XMLOutputter xmlOutput = new XMLOutputter();
			xmlOutput.setFormat(Format.getPrettyFormat());
			xmlOutput.output(jdomDoc, new FileWriter("leaderboard.xml"));
		} catch (IOException io) {
			System.out.println(io.getMessage());
		}
		
	}
	
	
	/**
	 * Létrehoz egy új xml element-et
	 * @param id Az element id-ja
	 * @param name Az element neve
	 * @param score Az element pontja
	 * @return A létrejött element
	 */
	private static Element createUserXMLElement(String id, String name, String score) {
		Element user = new Element("User");
		user.setAttribute(new Attribute("id", id));
		user.addContent(new Element("name").setText(name));
		user.addContent(new Element("score").setText(score));
		return user;
	}
	
	/**
	 * Hozzáad egy user-t a listOfUser listához,
	 * Ha már van ilyen user akkor azt frissíti, ha nagyobb a pontja, mint az eredeti
	 * Ha még nincs ilyen, akkor hozzáadja a listához
	 * @param user A hozzáadandó user
	 */
	private void addUser(User user) {
		for (Element userElement: listOfUsers) {
        	if (userElement.getChildText("name").equals(user.getName())) {
        		if (Integer.parseInt(userElement.getChildText("score")) < user.getScore()) {
        			userElement.getChild("score").setText(String.valueOf(user.getScore()));
            		this.updateList();
        		}
        		return;
        	}
        }
		root.addContent(createUserXMLElement(String.valueOf(listOfUsers.size()), user.getName(), String.valueOf(user.getScore())));
		this.updateList();
	}

	public void tick() {}

	/**
	 * Kirajzolja a leaderboardot
	 */
	public void render(Graphics g) {
		g.setColor(Color.white);
		g.setFont(new Font("arial", 0, 70));
		g.drawString("Leaderboard", 200, 100);
		
		g.setFont(new Font("arial", 0, 40));
		User tempUser;
		if (current != (pages - 1)) {
			for (int i = current * 10; i < (current + 1) * 10; i++) {
				tempUser = userList.get(i);
				String s = (i + 1) + ". " + tempUser.getName() + ": " + String.valueOf(tempUser.getScore());
				g.drawString(s, 200, 200 + (i - (current * 10)) * 40);
			}
		} else {
			for (int i = current * 10; i < userList.size(); i++) {
				tempUser = userList.get(i);
				String s = (i + 1) + ". " + tempUser.getName() + ": " + String.valueOf(tempUser.getScore());
				g.drawString(s, 200, 200 + (i - (current * 10)) * 40);
			}
		}
		
		
	}

	
	/**
	 * A LeaderBoardState alatti key input kezelése
	 * Az enter gomb lenyomására kimenti a leaderboard-ot és kilép a LeaderBoardState-bõl
	 */
	public void keyPressed(int key) {
		if (key == KeyEvent.VK_ENTER) {
			this.writeLeaderBoard();
			this.gsm.states.pop();
		}
		
		if (key == KeyEvent.VK_DOWN) current = Game.clamp(current + 1, 0, pages - 1);
		if (key == KeyEvent.VK_UP) current = Game.clamp(current - 1, 0, pages - 1);
	}

	public void keyReleased(int key) {}

	
}
