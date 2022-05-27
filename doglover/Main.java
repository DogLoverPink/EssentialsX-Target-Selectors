package doglover;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import doglover.Events.PlayerCommandExecute;
import doglover.Events.ServerCommandExecute;
import doglover.Events.TabEvent;

public class Main extends JavaPlugin{
    //private static ConversationFactory factory = new ConversationFactory(doglover.Events.ServerCommandExecute.plugin);
		@Override
		public void onEnable() {
            getServer().getPluginManager().registerEvents(new ServerCommandExecute(), this);
            getServer().getPluginManager().registerEvents(new PlayerCommandExecute(this), this);
            getServer().getPluginManager().registerEvents(new TabEvent(), this);
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
                Bukkit.getScheduler ().runTaskLater (doglover.Events.ServerCommandExecute.plugin, () -> all.performCommand("give @a NeededForSomeStrangeReason,IDKWhy"), 02L);
                Bukkit.getScheduler ().runTaskLater (doglover.Events.ServerCommandExecute.plugin, () -> all.abandonConversation(conversation), 02L);
                
            }*/
    }
}
