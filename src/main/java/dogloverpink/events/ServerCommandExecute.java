package dogloverpink.events;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerCommandEvent;

import dogloverpink.selectorUtils.SelectorUtils;

public class ServerCommandExecute implements Listener {

    @EventHandler
    public void onServerCommandExecute(ServerCommandEvent event) {
        String[] args = event.getCommand().split("\\s+");
        PluginCommand command = Bukkit.getPluginCommand(args[0].replaceFirst("/", ""));
        if (command == null) {
            return;
        }
        PluginCommand otherCommand = Bukkit.getPluginCommand(command.getName());

        if (command == null
                || otherCommand == null
                || otherCommand.getPlugin() == null
                || !otherCommand.getPlugin().getName().contains("Essentials")) {
            return;
        }
        int argNumWithSelector = -1;
        for (int i = 0; i < args.length; i++) {
            if (args[i].startsWith("@")) {
                argNumWithSelector = i;
            }
        }

        if (argNumWithSelector == -1)
            return;

        String fullCommand = "";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < args.length; i++) {
            if (i == argNumWithSelector) {
                sb.append("%TARGET% ");
            } else {
                sb.append(args[i] + " ");
            }
        }
        fullCommand = sb.toString().trim();

        ArrayList<Player> players = new ArrayList<Player>(Bukkit.getOnlinePlayers());
        
        
        CommandSender sender = event.getSender();
        players = SelectorUtils.handleSpecifications(sender, players, args[argNumWithSelector]);
        
        SelectorUtils.handleSelector(sender, players, args[argNumWithSelector]);
        
        if (players.isEmpty()) {
            return;
        }
        event.setCancelled(true);
        for (Player target : players) {
            String cmdToRun = fullCommand.replace("%TARGET%", target.getName()).trim();
            
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), cmdToRun);
        }

    }
    
}
