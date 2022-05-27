package doglover;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class SelectorUtils {
	
	
	public static boolean isInRadius(Location location, Player player, String distance) {
		//Debug: System.out.println(location+" "+player.getName()+" "+distance);
		try {
		if (distance.startsWith("!")) {
			if (location.distanceSquared(player.getLocation()) <= Double.valueOf(distance.substring(1)) * Double.valueOf(distance.substring(1))) 
		      return false;
		}
        if (!distance.startsWith("!")) {
        	if (!(location.distanceSquared(player.getLocation()) <= Double.valueOf(distance) * Double.valueOf(distance)))
        	  return false;
        }
		} catch (Exception e) {
			return false;
		}
		return true;
	}
	
    public static boolean doesPlayerMeetReqs(Player player, String tag, String level, String gamemode) {
    	try {
    	if (!tag.equals("none")) {
    		if (tag.startsWith("!") && player.getScoreboardTags().contains(tag.substring(1))) {
        		//Debug: System.out.println("Player "+player.getName()+" Does NOT have scoreboard tag "+tag.substring(1));
        		return false; 
        	}
    		if (!tag.startsWith("!") && !player.getScoreboardTags().contains(tag)) {
    			//Debug: System.out.println("Player "+player.getName()+" Does NOT have scoreboard tag "+tag);
    		return false; 
    	}}
    	
    	if (!level.equals("none")){
    		if (level.startsWith("!") && player.getLevel() == Integer.valueOf(level.substring(1))) {
        		System.out.println("Player "+player.getName()+" is not level "+level.substring(1));
        		return false; 
        		}
    		if (!level.startsWith("!") && player.getLevel() != Integer.valueOf(level)) {
    			//Debug: System.out.println("Player "+player.getName()+" is not level "+level);
    		return false; 
    		}}
    	
    	if (!gamemode.equals("none")) {
    		if (gamemode.startsWith("!") && player.getGameMode() == GameMode.valueOf(gamemode.toUpperCase().substring(1))) {
    			//Debug: System.out.println("Player "+player.getName()+" is not in gamemode "+gamemode.substring(1));
        		return false; 
        		}
    		
    		if (!gamemode.startsWith("!") && player.getGameMode() != GameMode.valueOf(gamemode.toUpperCase())) {
    			//Debug: System.out.println("Player "+player.getName()+" is not in gamemode "+gamemode);
    		return false; 
    		}
    	}
    	} catch(Exception e) {
    		return false;
    	}
    	return true; //If it nevers returns false, then it will return true!
    	}
    }