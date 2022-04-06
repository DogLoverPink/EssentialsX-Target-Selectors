package doglover.Events;

import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.TabCompleteEvent;

public class TabEvent implements Listener {
    @EventHandler
     public void onTabComplete(TabCompleteEvent event) {
         List<String> completions = event.getCompletions();
         boolean isEssentialsCommand = false;
         for (String entry: completions) {
             if (entry.equals("*")) {
                 isEssentialsCommand = true;
                 break;
             }
         }
         if (!isEssentialsCommand) return;
         completions.remove("*");
         completions.remove("**");
         if (event.getSender() instanceof Player) {
         completions.add("@a");
         completions.add("@r");
         completions.add("@p");
         completions.add("@s");
         } else {
            completions.add("@a");
            completions.add("@r");
         }
         event.setCompletions(completions);

    }
}

