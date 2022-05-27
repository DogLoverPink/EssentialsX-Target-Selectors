package doglover.Events;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.conversations.Conversation;
import org.bukkit.conversations.ConversationFactory;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import doglover.Main;
import doglover.SelectorUtils;

public class PlayerCommandExecute implements Listener {
	public static Main plugin;
	public PlayerCommandExecute(Main plugin) {
		PlayerCommandExecute.plugin = plugin;
	}
    private static ConversationFactory factory = new ConversationFactory(plugin);
    public static String lastCommand;
 @EventHandler
     public void onCommandThingy(PlayerCommandPreprocessEvent event) {
         String[] args = event.getMessage().split("\\s");
         if (Bukkit.getPluginCommand(args[0].replaceFirst("/", "")) == null ||!Bukkit.getPluginCommand(Bukkit.getPluginCommand(args[0].replaceFirst("/", "")).getName()).getPlugin().getName().equals("Essentials")) return;
         Player plr = event.getPlayer();
         if (!plr.hasPermission("essxselectors.use")) return;
         //plr.sendMessage(
             //"Info: \n"+
            // "args: "+Arrays.toString(args)+
             //"\nPlugin: "+Bukkit.getPluginCommand(args[0].replaceFirst("/", "")).getPlugin()+
             //"\nReal thingy:"+Bukkit.getPluginCommand(args[0].replaceFirst("/", "")).getName()+
             //"\nPermission:"+Bukkit.getPluginCommand(Bukkit.getPluginCommand(args[0].replaceFirst("/", "")).getName()).getPermission());
            int argWithAt = 0;
            if (args.length > 1 && args[1].startsWith("@")) argWithAt = 1;
            if (args.length > 2 && args[2].startsWith("@")) argWithAt = 2;
            
            if (argWithAt == 0) return;
            
            String fullCommand = "";
		    for (int i = 0; i < args.length; i++) {
			    if (i > 2)fullCommand = fullCommand+" "+args[i];
		}
		    if (args[argWithAt].toLowerCase().startsWith("@p")) {
		    	if (!plr.hasPermission("essxselectors.closest")) return; }
		    if (args[argWithAt].toLowerCase().startsWith("@s")) {
		    	if (!plr.hasPermission("essxselectors.self")) return; }
		    if (args[argWithAt].toLowerCase().startsWith("@a")) {
		    	if (!plr.hasPermission("essxselectors.all")) return; }
		    if (args[argWithAt].toLowerCase().startsWith("@r")) {
		    	if (!plr.hasPermission("essxselectors.random")) return; }

        if (args[argWithAt].toLowerCase().startsWith("@s")) {
                event.setCancelled(true);
                if (argWithAt == 1 ){
                    if (args.length > 2) { fullCommand = args[2]+" "+fullCommand;}
                    plr.performCommand(args[0].replaceFirst("/", "")+" "+plr.getName()+" "+fullCommand);
                }
                if (argWithAt == 2) {
                plr.performCommand(args[0].replaceFirst("/", "")+" "+args[1]+" "+plr.getName()+" "+fullCommand);}  
                return;
        }

        String distance = "none";
        String tag = "none";
        String level = "none";
        String gamemode = "none";
        int limit = 99999;
        
        //For example, let's pretend the entered specifications was this @a[level=5,distance=!10,tag="Kill"]
	      String target = args[argWithAt].substring(2);//Becomes [level=5,distance=!10,tag="Kill"]
        if (target.startsWith("[") || target.endsWith("]")) {//Check if it has specifications
      	  

        target = target.replace(",", ",,");//Becomes [level=5,,distance=!10,,tag="Kill"]
        target = target.replaceFirst("\\[", ",");//Becomes ,level=5,,distance=!10,,tag="Kill"]
        target = target.substring(0, target.length()-1);//Becomes ,level=5,,distance=!10,,tag="Kill"
        target += ","; //Becomes ,level=5,,distance=!10,,tag="Kill",
        
        Matcher m = Pattern.compile(",(.*?),").matcher(target);//Basic Regex, gets everything in between "," and ","
        
        
        while (m.find()) {
      	  String value;
            String selector = m.group().substring(1, m.group().length()-1).split("=")[0].toLowerCase().replaceAll("\\s", "");//Gets the value before the "="
            if (!selector.equals("tag")) {
              try {
          	  value = m.group().substring(1, m.group().length()-1).split("=")[1].toLowerCase();//Gets the value after the "=", and sets to lower case
              } catch(IndexOutOfBoundsException e) {
            	  plr.sendMessage("§cHmm, an error occured. Are you missing a closing bracket or \"=value\"?");
            	  continue;
              }
              } else value = m.group().substring(1, m.group().length()-1).split("=")[1]; //Tags are case sensitive, no lowercase
             
            switch(selector) {
            case "distance":
          	  if (value.startsWith("..")) value = value.substring(2);//when doing the "distance" selector in vannila mincraft, you need to add ".."
          	  if (value.startsWith("!..")) value = "!"+value.substring(3);
          	  try {
          		  @SuppressWarnings("unused")
					int theLevel = Integer.valueOf(value.replaceFirst("!", ""));
          		  distance = value;
          	  }catch (Exception e) {
          		  plr.sendMessage("§cExpected an integer for \"distance\" tag, but found \"§e"+value+"§c\" instead! §e(Ignoring it)");
          		  }
          	  //Debug: System.out.println("Distance being set to "+value);
          	  distance = value;
          	  break;
          	  
            case "tag":
          	  if (value.startsWith("\"") && value.endsWith("\"")) value = value.substring(1, value.length()-1);//Remove qoutes
          	  //Debug: System.out.println("tag being set to "+value);
          	  tag = value;
          	  break;
          	  
            case "level":
          	  //Debug: System.out.println("level being set to "+value);
          	  try {
          		  @SuppressWarnings("unused")
          		  int theLevel = Integer.valueOf(value.replaceFirst("!", ""));
          		  level = value;
          	  } catch (Exception e) {
          		  plr.sendMessage("§cExpected an integer for \"level\" tag, but found \"§e"+value+"§c\" instead! §e(Ignoring it)");
          	  }
          	  break;
          	  
            case "gamemode":
          	  if (value.startsWith("\"") && value.endsWith("\"")) value = value.substring(1, value.length()-1);//Remove qoutes
          	  //Debug: System.out.println("gamemode being set to "+value);
          	  try {
          		  @SuppressWarnings("unused")
          		  GameMode gm = GameMode.valueOf(value.toUpperCase().replaceFirst("!", ""));
          		  gamemode = value;
          	  } catch (Exception e) {
          		  plr.sendMessage("§cExpected a gamemode for \"gamemode\" tag, but found \"§e"+value+"§c\" instead! §e(Ignoring it)");
          	  }
          	  break;
          	  
            case "limit":
          	  try {
          		  limit = Integer.valueOf(value);
          	  } catch (Exception e) {
          		  limit = 99999;
          		  plr.sendMessage("§cExpected an integer for \"limit\" tag, but found \"§e"+value+"§c\" instead! §e(Ignoring it)");
          	  }
          	  //Debug: System.out.println("limit being set to "+value);
          	  break;
            default:
          	  plr.sendMessage("§cInvalid selector \"§e"+selector+"§c\", Ignoring it.");
          	  //Debug: System.out.println("Thats not a valid selector you dummy!");
            }
            
        }}
        
        Collection<Player> playerList = new ArrayList<Player>();
        
        if (args[argWithAt].length() < 3) {
      	  playerList = (Collection<Player>) Bukkit.getOnlinePlayers();
        } else {
        for (Player loopPlr : Bukkit.getOnlinePlayers()) {
      	  if(SelectorUtils.doesPlayerMeetReqs(loopPlr, tag, level, gamemode)) playerList.add(loopPlr);
        }
        }
	    
	    
	    //Debug: System.out.println("PlayerList size is "+playerList.size());
	    args[argWithAt] = args[argWithAt].substring(0, 2);
	    //Debug: System.out.println(args[argWithAt]);
	    
	    //limit stuff below
	    if (playerList.size() > limit) {
	    	Collection<Player> toRemove = new ArrayList<Player>();
	    	for (int i = 0; i < playerList.size(); i++) {
	    		if (i >= limit) toRemove.add((Player) playerList.toArray()[i]); //You can't edit a list you are looping thru
	    	}
	    	for (Player removeplr : toRemove) playerList.remove(removeplr);
	    }
	    if (!distance.equals("none")) {
      		Collection<Player> removeList = new ArrayList<Player>();
      	for (Player loopPlr: playerList) {
              if (!SelectorUtils.isInRadius(plr.getLocation(), loopPlr, distance)) removeList.add(loopPlr);
             }
      	for (Player playerToRemove: removeList) playerList.remove(playerToRemove);
      	}
      	//^ Special @p check because
      	
      	if (playerList.size() == 0) return;
      	//If no targets, stop
	    
      if (args[argWithAt].equalsIgnoreCase("@r")) {
          if (playerList.size() == 0) return;
          Player[] allPlayers = new Player[playerList.size()];
          int i = 0;
          for(Player all : playerList) {
              allPlayers[i] = all;
              i++;
          }
           
              Random random = new Random();
              Player randomPlayer = allPlayers[random.nextInt(allPlayers.length)];
              

              event.setCancelled(true);
              if (argWithAt == 1 ){
                  if (args.length > 2) { fullCommand = args[2]+" "+fullCommand;}
                  plr.performCommand(args[0].replaceFirst("/", "")+" "+randomPlayer.getName()+" "+fullCommand);
              }
              if (argWithAt == 2) {
                  plr.performCommand(args[0].replaceFirst("/", "")+" "+args[1]+" "+randomPlayer.getName()+" "+fullCommand);}
              
      }
          if (args[argWithAt].equalsIgnoreCase("@p")) {
          	
              event.setCancelled(true);
              
              Player[] allPlayers = new Player[playerList.size()];
              int i = 0;
              List<Location> locations = new ArrayList<Location>();
              for(Player all : playerList) {
              allPlayers[i] = all;
              locations.add(all.getLocation());
                  i++;
              }
              //^ Put all players locations in a arraylist

              Location myLocation = plr.getLocation();
              Location closest = locations.get(0);
              double closestDist = closest.distance(myLocation);
              for (Location loc : locations) { //Loop to find closest target
                  if (loc.distance(myLocation) < closestDist) {
                      closestDist = loc.distance(myLocation);
                      closest = loc;
                  }
              }
              Player closetPlayer = Bukkit.getPlayer("NoOnlinePlayers"); //Needs to be declared, and setting to null would throw errors
              for(Player all : playerList) {
                  if (all.getLocation().equals(closest)) closetPlayer = all;
              }


              if (argWithAt == 1 ){
                  if (args.length > 2) { fullCommand = args[2]+" "+fullCommand;}
                  Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(),args[0].replaceFirst("/", "")+" "+closetPlayer.getName()+" "+fullCommand);
              }
              if (argWithAt == 2) {
                  Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(),args[0].replaceFirst("/", "")+" "+args[1]+" "+closetPlayer.getName()+" "+fullCommand);}
              
        }
      if (args[argWithAt].equalsIgnoreCase("@a")) {
          boolean checkedForBigArgs = false; 
          Conversation conversation = factory.buildConversation((Player) plr);
          for(Player all : playerList) {
              if (argWithAt == 1){
                  if (args.length > 2 && !checkedForBigArgs) {
                      fullCommand = args[2]+" "+fullCommand; checkedForBigArgs=true;}
                      lastCommand = "§eRan §a"+args[0].replaceFirst("/", "")+" @a"+fullCommand;
                   plr.performCommand(args[0].replaceFirst("/", "")+" "+all.getName()+" "+fullCommand);
                  }
              event.setCancelled(true);
              if (argWithAt == 2) {
            	  lastCommand = "§eRan §a"+args[0].replaceFirst("/", "")+" @a"+fullCommand;
                  plr.performCommand(args[0].replaceFirst("/", "")+" "+args[1]+" "+all.getName()+" "+fullCommand);
}
              factory.withFirstPrompt(doglover.ConverseThing.noChat).withEscapeSequence("cancel");
  			  conversation.begin();
          }
          plr.abandonConversation(conversation);
      }
            }
}
