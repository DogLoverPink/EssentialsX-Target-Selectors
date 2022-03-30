package doglover;

import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.Prompt;
import org.bukkit.conversations.StringPrompt;

public class ConverseThing {
    public static Prompt noChat = new StringPrompt() {
        @Override
        public String getPromptText(ConversationContext context) {
            return doglover.Events.CommandExecute.lastCommand;
        }
    
        @Override
        public Prompt acceptInput(ConversationContext context, String input) {
            return Prompt.END_OF_CONVERSATION;
        }};
}
