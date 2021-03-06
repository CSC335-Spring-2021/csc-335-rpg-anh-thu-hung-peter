import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import characters.CivCharacter;
/**
 * This class serves as a player in the game and stores current data. Player can be
 * human and computer, and data includes each player's country, current gold, current
 * units and their positions.
 * 
 * @author Anh Nguyen Phung
 * @author Hung Le Ba
 * @author Thu Tra
 * @author Peter Vo
 *
 */

public class CivPlayer implements Serializable{
	
	private static final long serialVersionUID = 1L;
	/** the dimension of the Civilization's map */
	private static final int DIMENSION = 10; 
	/** the initial gold count of each player */
	private static final int INITIAL_GOLD = 10;
	/** current total unit count of the player */
	private int unitCount;
	/** map of the all the units for each type of character the player has */
	private Map<String, List<CivCharacter>> unitMap; 
	/** map of all the characters of the player and their coordinates in the game board */
	private Map<CivCharacter, Integer> positionMap; 
	/** the type of player (human or computer)*/ 
	private String name;
	/** player's gold */ 
	private int gold;
	
	/**
	 * Constructor. Creates an instance of CivPlayer
	 * 
	 * @param name name of the player ("Human" or "Computer")
	 *
	 */
	public CivPlayer(String name) {
		unitMap = new HashMap<String, List<CivCharacter>>();
		unitMap.put("Archer", new ArrayList<CivCharacter>());
		unitMap.put("Catapult", new ArrayList<CivCharacter>());
		unitMap.put("Guard", new ArrayList<CivCharacter>());
		unitMap.put("Knight", new ArrayList<CivCharacter>());
		unitMap.put("Warrior", new ArrayList<CivCharacter>());
		positionMap = new HashMap<CivCharacter, Integer>();
		unitCount = 0;
		this.name = name;
		gold = INITIAL_GOLD;
	}

	/**
	 * Get current unit count of the player
	 * 
	 * @return the current unit count
	 *
	 */
	public int getUnitCount() {
		return unitCount;
	}

	/**
	 * Get current name of the player ("Human" or "Computer")
	 * 
	 * @return the current name
	 *
	 */
	public String getName() {
		return name;
	}

	/**
	 * Get current gold count of the player
	 * 
	 * @return the current gold count
	 *
	 */
	public int getGold() {
		return gold;
	}
	
	/**
	 * Add to current gold count of the player
	 * 
	 * @param add the amount of gold to be added
	 *
	 */
	public void addGold(int add) {
		gold += add;
	}
	
	/**
	 * Get the map of all units and their positions
	 * 
	 * @return a HashMap of (unit, position) entries
	 *
	 */
	public Map<CivCharacter, Integer> getPositionMap() {
		return positionMap;
	}
	
	/**
	 * Get the map of character types and all current units of each type
	 * 
	 * @return a HashMap of (character type, list of units with this type) entries
	 *
	 */
	public Map<String, List<CivCharacter>> getUnitMap() {
		return unitMap;
	}

	/**
	 * Add a new unit to current player
	 * 
	 * @param character the new character to be added
	 * @param row the row position to be added on the board
	 * @param col the column position to be added on the board
	 *
	 */
	public void addUnit(CivCharacter character, int row, int col) {
		List<CivCharacter> characters = unitMap.get(character.getName());
		characters.add(character);
		positionMap.put(character, row * DIMENSION + col);
		unitCount++;
		gold -= character.getCost();

	}

	/**
	 * remove a character from the current player's units 
	 * 
	 * @param character a specific character to be removed
	 */
	public void removeUnit(CivCharacter character) {
		List<CivCharacter> characters = unitMap.get(character.getName());
		characters.remove(character);
		positionMap.remove(character);
		unitCount--;
	}
	
	/**
	 * Update a current unit's position in the positionMap
	 * 
	 * @param character the current character to be updated
	 * @param row new row position to be moved over to
	 * @param col new column position to be moved over to
	 */
	public void updateUnit(CivCharacter character, int row, int col) {
		positionMap.put(character, row * DIMENSION + col);
	}	

}
