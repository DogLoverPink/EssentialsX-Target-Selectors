package dogloverpink.events;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.PluginCommand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerCommandEvent;

public class ServerCommandExecute implements Listener {

    @EventHandler
    public void onServerCommandExecute(ServerCommandEvent event) {
        String[] args = event.getCommand().split("\\s+");
        PluginCommand command = Bukkit.getPluginCommand(args[0].replaceFirst("/", ""));
        if (command == null || !command.getPlugin().getName().contains("Essentials")) {
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

        List<Player> players = new ArrayList<>();

        try {
            List<Entity> entities = Bukkit.selectEntities(event.getSender(), args[argNumWithSelector]);
            entities.forEach(entity -> {
                if (entity instanceof Player) {
                    players.add((Player) entity);
                }
            });
        } catch (IllegalArgumentException e) {
            event.getSender().sendMessage("§c"+e.getCause().getMessage());
            event.setCancelled(true);
            return;
        }
        
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
