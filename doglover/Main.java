package doglover;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import doglover.Events.CommandExecute;

public class Main extends JavaPlugin{
    //private static ConversationFactory factory = new ConversationFactory(doglover.Events.CommandExecute.plugin);
		@Override
		public void onEnable() {
            getServer().getPluginManager().registerEvents(new CommandExecute(this), this);
            if (getServer().getPluginManager().getPlugin("Essentials") == null) {
                Bukkit.getLogger().warning("-------------------");
                Bukkit.getLogger().warning("WARN FROM ESSENTIALS SELECTORS:");
                Bukkit.getLogger().warning("EssentialsX not detected!");
                Bukkit.getLogger().warning("Please Download EssentialsX for Essentials Selector to work!");
                Bukkit.getLogger().warning("Offical EssentialsX download: https://essentialsx.net");
                Bukkit.getLogger().warning("Or ignore this, it doesn't actually affect your server at all :)");
                Bukkit.getLogger().warning("-------------------");
            }

            /*for(Player all : Bukkit.getServer().getOnlinePlayers()) {
                Conversation conversation = factory.buildConversation(all);
                factory.withFirstPrompt(doglover.ConverseThing.noChat).withEscapeSequence("cancel");
        		conversation.begin();
                Bukkit.getScheduler ().runTaskLater (doglover.Events.CommandExecute.plugin, () -> all.performCommand("give @a NeededForSomeStrangeReason,IDKWhy"), 02L);
                Bukkit.getScheduler ().runTaskLater (doglover.Events.CommandExecute.plugin, () -> all.abandonConversation(conversation), 02L);
                
            }*/
    }
}
