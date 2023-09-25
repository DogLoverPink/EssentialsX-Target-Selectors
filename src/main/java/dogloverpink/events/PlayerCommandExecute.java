package dogloverpink.events;

import java.util.ArrayList;
import org.bukkit.Bukkit;
import org.bukkit.command.PluginCommand;
import org.bukkit.conversations.Conversation;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import dogloverpink.selectorUtils.SelectorUtils;
import dogloverpink.selectors.AllSelector;

public class PlayerCommandExecute implements Listener {

    @EventHandler
    public void onPlayerCommandExecute(PlayerCommandPreprocessEvent event) {
        String[] args = event.getMessage().split("\\s+");
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

        Player plr = event.getPlayer();
        if (!plr.hasPermission("essxselectors.use"))
            return;
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
        if (args[argNumWithSelector].toLowerCase().startsWith("@p")) {
            if (!plr.hasPermission("essxselectors.closest"))
                return;
        }
        if (args[argNumWithSelector].toLowerCase().startsWith("@s")) {
            if (!plr.hasPermission("essxselectors.self"))
                return;
        }
        if (args[argNumWithSelector].toLowerCase().startsWith("@a")) {
            if (!plr.hasPermission("essxselectors.all"))
                return;
        }
        if (args[argNumWithSelector].toLowerCase().startsWith("@r")) {
            if (!plr.hasPermission("essxselectors.random"))
                return;
        }

        ArrayList<Player> players = new ArrayList<>(Bukkit.getOnlinePlayers());

        players = SelectorUtils.handleSpecifications(plr, players, args[argNumWithSelector]);

        SelectorUtils.handleSelector(plr, players, args[argNumWithSelector]);

        if (players.isEmpty()) {
            return;
        }
        event.setCancelled(true);
        if (args[argNumWithSelector].startsWith("@a") && players.size() > 1) {
            Conversation conversation = AllSelector.conversationFactory.buildConversation(plr);
            AllSelector.lastCommand = "§eRan §a"+fullCommand.replace("%TARGET%", args[argNumWithSelector]);
            int count = 0;
            for (Player target : players) {
                String cmdToRun = fullCommand.replace("%TARGET%", target.getName()).trim();
                cmdToRun = cmdToRun.substring(1);
                plr.performCommand(cmdToRun);
                if (count == 0) {
                    AllSelector.conversationFactory.withFirstPrompt(AllSelector.noChat).withEscapeSequence("cancel");
                    conversation.begin();
                }
            }
            conversation.abandon();
            
        } else {
            for (Player target : players) {
                String cmdToRun = fullCommand.replace("%TARGET%", target.getName()).trim();
                cmdToRun = cmdToRun.substring(1);
                plr.performCommand(cmdToRun);
            }
        }
    }

}
