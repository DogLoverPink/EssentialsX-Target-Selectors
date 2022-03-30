package doglover;
import org.bukkit.plugin.java.JavaPlugin;

import doglover.Events.CommandExecute;

public class Main extends JavaPlugin{
    //private static ConversationFactory factory = new ConversationFactory(doglover.Events.CommandExecute.plugin);
		@Override
		public void onEnable() {
            getServer().getPluginManager().registerEvents(new CommandExecute(this), this);

            /*for(Player all : Bukkit.getServer().getOnlinePlayers()) {
                Conversation conversation = factory.buildConversation(all);
                factory.withFirstPrompt(doglover.ConverseThing.noChat).withEscapeSequence("cancel");
        		conversation.begin();
                Bukkit.getScheduler ().runTaskLater (doglover.Events.CommandExecute.plugin, () -> all.performCommand("give @a NeededForSomeStrangeReason,IDKWhy"), 02L);
                Bukkit.getScheduler ().runTaskLater (doglover.Events.CommandExecute.plugin, () -> all.abandonConversation(conversation), 02L);
                
            }*/
    }
}
