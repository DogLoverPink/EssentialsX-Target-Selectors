package dogloverpink.selectors;

import java.util.ArrayList;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SelfSelector extends Selector {

    @Override
    public void evalute(ArrayList<Player> players, CommandSender sender) {
        if (!(sender instanceof Player)) {
            players.clear();
            sender.sendMessage("§cYou must be a player to use this selector");
            return;
        }
        players.clear();
        players.add((Player) sender);
    }
    
}
