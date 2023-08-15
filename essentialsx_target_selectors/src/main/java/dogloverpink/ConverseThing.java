package dogloverpink;

import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.Prompt;
import org.bukkit.conversations.StringPrompt;

public class ConverseThing {
	/* Why does this file exist?
	 * 
	 * Basically this puts a player into a bukkit conversable, but why you ask?
	 * When a player is in a conversable, they won't receive any outside chat messages, this means the player won't see player chat, command feedback, etc/
	 * 
	 * Now this is helpful because for example if a player were to run '/feed @a' with 75 players online,
	 * without the conversable, they would receive 75 chat messages saying "fed (playername)", but instead, after 1 command use
	 * the player is put into a conversable to prevent the other 74 chat messages from sending.
	 * 
	 * It then abandons the conversable right after all the commands have run.
	 */
    public static Prompt noChat = new StringPrompt() {
        @Override
        public String getPromptText(ConversationContext context) {
            return dogloverpink.Events.PlayerCommandExecute.lastCommand;
        }
    
        @Override
        public Prompt acceptInput(ConversationContext context, String input) {
            return Prompt.END_OF_CONVERSATION;
        }};
}
