package dogloverpink.events;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.PluginCommand;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.TabCompleteEvent;

public class TabEvent implements Listener {
    @EventHandler
    public void onTabComplete(TabCompleteEvent event) {
        List<String> completions = event.getCompletions();
        if (!completions.contains("*"))
            return;
        String command = event.getBuffer().split("\\s+")[0];
        if (command.startsWith("/")) {
            command = command.substring(1);
        }
        PluginCommand cmd = Bukkit.getServer().getPluginCommand(command);
        if (cmd == null || cmd.getPlugin() == null || !cmd.getPlugin().getName().startsWith("Essentials")) {
            return;
        }
        completions.remove("*");
        completions.remove("**");
        if (event.getSender() instanceof Player) {
            completions.add("@a");
            completions.add("@r");
            completions.add("@p");
            completions.add("@s");
            completions.add("@a[");
            completions.add("@r[");
            completions.add("@p[");
            completions.add("@s[");
        } else {
            completions.add("@a");
            completions.add("@r");
            completions.add("@a[");
            completions.add("@r[");
        }
        event.setCompletions(completions);

    }

    public static String getSelectorWord(String buffer) {
        for (String word : buffer.split("\\s+")) {
            if (word.startsWith("@")) {
                return word;
            }
        }
        return null;
    }
}
