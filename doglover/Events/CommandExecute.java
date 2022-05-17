package doglover.Events;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.command.BlockCommandSender;
import org.bukkit.command.CommandSender;
import org.bukkit.conversations.Conversation;
import org.bukkit.conversations.ConversationFactory;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.server.ServerCommandEvent;

import doglover.Main;

public class CommandExecute implements Listener{
    public static Main plugin;
	public CommandExecute(Main plugin) {
		CommandExecute.plugin = plugin;
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
            if (args.length > 1 && args[1].contains("@")) argWithAt = 1;
            if (args.length > 2 && args[2].contains("@")) argWithAt = 2;
            if (argWithAt == 0) return;
            String fullCommand = "";
		    for (int i = 0; i < args.length; i++) {
			    if (i > 2)fullCommand = fullCommand+" "+args[i];
		}


        if ((args[argWithAt].equalsIgnoreCase("@s") || args[argWithAt].equalsIgnoreCase("@p")) && (plr.hasPermission("essxselectors.self") || plr.hasPermission("essxselectors.closest"))) {
                event.setCancelled(true);
                if (argWithAt == 1 ){
                    if (args.length > 2) { fullCommand = args[2]+" "+fullCommand;}
                    plr.performCommand(args[0].replaceFirst("/", "")+" "+plr.getName()+" "+fullCommand);
                }
                if (argWithAt == 2) {
                plr.performCommand(args[0].replaceFirst("/", "")+" "+args[1]+" "+plr.getName()+" "+fullCommand);}
                
        }

        if (args[argWithAt].equalsIgnoreCase("@r") && plr.hasPermission("essxselectors.random")) {
            Player[] allPlayers = new Player[Bukkit.getOnlinePlayers().size()];
            int i = 0;
            for(Player all : Bukkit.getServer().getOnlinePlayers()) {
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

            if (args[argWithAt].equalsIgnoreCase("@a") && plr.hasPermission("essxselectors.all")) {
                Conversation conversation = factory.buildConversation((Player) plr);
                for(Player all : Bukkit.getServer().getOnlinePlayers()) {
                    event.setCancelled(true);
                    boolean checkedForBigArgs = false;
                    if (argWithAt == 1 && !checkedForBigArgs){
                        if (args.length > 2) { fullCommand = args[2]+" "+fullCommand; checkedForBigArgs = true;}
                        lastCommand = "§eRan §a"+args[0].replaceFirst("/", "")+" @a "+fullCommand;
                        plr.performCommand(args[0].replaceFirst("/", "")+" "+all.getName()+" "+fullCommand);
                    }
                    if (argWithAt == 2) {
                        lastCommand = "§eRan §a"+args[0].replaceFirst("/", "")+" "+args[1]+" @a "+fullCommand;
                    plr.performCommand(args[0].replaceFirst("/", "")+" "+args[1]+" "+all.getName()+" "+fullCommand);}
                    
                    factory.withFirstPrompt(doglover.ConverseThing.noChat).withEscapeSequence("cancel");
        			conversation.begin();
                }
                
                plr.abandonConversation(conversation);
            }
            }
    @EventHandler
    public void onConsoleCommandThingy(ServerCommandEvent event) {
        String[] args = event.getCommand().split("\\s");
        if (Bukkit.getPluginCommand(args[0].replaceFirst("/", "")) == null ||!Bukkit.getPluginCommand(Bukkit.getPluginCommand(args[0].replaceFirst("/", "")).getName()).getPlugin().getName().equals("Essentials")) return;
         CommandSender plr = event.getSender();
         //plr.sendMessage(
             //"Info: \n"+
            // "args: "+Arrays.toString(args)+
             //"\nPlugin: "+Bukkit.getPluginCommand(args[0].replaceFirst("/", "")).getPlugin()+
             //"\nReal thingy:"+Bukkit.getPluginCommand(args[0].replaceFirst("/", "")).getName()+
             //"\nPermission:"+Bukkit.getPluginCommand(Bukkit.getPluginCommand(args[0].replaceFirst("/", "")).getName()).getPermission());
            int argWithAt = 0;
            if (args.length > 1 && args[1].contains("@")) argWithAt = 1;
            if (args.length > 2 && args[2].contains("@")) argWithAt = 2;
            if (argWithAt == 0) return;
            String fullCommand = "";
		    for (int i = 0; i < args.length; i++) {
			    if (i > 2)fullCommand = fullCommand+" "+args[i];
            }

            if (args[argWithAt].equalsIgnoreCase("@r")) {
                if (Bukkit.getOnlinePlayers().size() == 0) return;
                Player[] allPlayers = new Player[Bukkit.getOnlinePlayers().size()];
                int i = 0;
                for(Player all : Bukkit.getServer().getOnlinePlayers()) {
                    allPlayers[i] = all;
                    i++;
                }
                 
                    Random random = new Random();
                    Player randomPlayer = allPlayers[random.nextInt(allPlayers.length)];
                    
    
                    event.setCancelled(true);
                    if (argWithAt == 1 ){
                        if (args.length > 2) { fullCommand = args[2]+" "+fullCommand;}
                        Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), args[0].replaceFirst("/", "")+" "+randomPlayer.getName()+" "+fullCommand);
                    }
                    if (argWithAt == 2) {
                        Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), args[0].replaceFirst("/", "")+" "+args[1]+" "+randomPlayer.getName()+" "+fullCommand);}
                    
            }
            if (event.getSender() instanceof BlockCommandSender) {
            	BlockCommandSender blockCommandSender = (BlockCommandSender) event.getSender();
                Block theCommandBlock = blockCommandSender.getBlock();
                if (args[argWithAt].equalsIgnoreCase("@p")) {
                    event.setCancelled(true);
                    Player[] allPlayers = new Player[Bukkit.getOnlinePlayers().size()];
                    int i = 0;
                    List<Location> locations = new ArrayList<Location>();
                    for(Player all : Bukkit.getServer().getOnlinePlayers()) {
                    allPlayers[i] = all;
                    locations.add(all.getLocation());
                        i++;
                    }

                    Location myLocation = theCommandBlock.getLocation();
                    Location closest = locations.get(0);
                    double closestDist = closest.distance(myLocation);
                    for (Location loc : locations) {
                        if (loc.distance(myLocation) < closestDist) {
                            closestDist = loc.distance(myLocation);
                            closest = loc;
                        }
                    }
                    Player closetPlayer = Bukkit.getPlayer("NoOnlinePlayers");
                    for(Player all : Bukkit.getServer().getOnlinePlayers()) {
                        if (all.getLocation().equals(closest)) closetPlayer = all;
                    }


                    if (argWithAt == 1 ){
                        if (args.length > 2) { fullCommand = args[2]+" "+fullCommand;}
                        Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(),args[0].replaceFirst("/", "")+" "+closetPlayer.getName()+" "+fullCommand);
                    }
                    if (argWithAt == 2) {
                        Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(),args[0].replaceFirst("/", "")+" "+args[1]+" "+closetPlayer.getName()+" "+fullCommand);}
                    
            }
              }
            if (args[argWithAt].equalsIgnoreCase("@a")) {
                boolean checkedForBigArgs = false;
                for(Player all : Bukkit.getServer().getOnlinePlayers()) {
                    if (argWithAt == 1){
                        if (args.length > 2 && !checkedForBigArgs) {
                            fullCommand = args[2]+" "+fullCommand;checkedForBigArgs=true;}
                            plr.sendMessage("§eRunning §a"+args[0].replaceFirst("/", "")+" @a"+fullCommand);
                         Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), args[0].replaceFirst("/", "")+" "+all.getName()+" "+fullCommand);
                        }
                    event.setCancelled(true);
                    if (argWithAt == 2) {
                        plr.sendMessage("§eRunning §a"+args[0].replaceFirst("/", "")+" "+args[1]+" @a "+fullCommand);
                        Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), args[0].replaceFirst("/", "")+" "+args[1]+" "+all.getName()+" "+fullCommand);
}
                }
            }
    }
   }
