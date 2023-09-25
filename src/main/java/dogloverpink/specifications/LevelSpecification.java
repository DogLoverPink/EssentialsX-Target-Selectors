package dogloverpink.specifications;

import java.util.ArrayList;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class LevelSpecification extends SelectorSpecification {

    @Override
    public ArrayList<Player> evalute(ArrayList<Player> players, String specificationValue, CommandSender sender) {
        ArrayList<Player> playersCopy = new ArrayList<Player>(players);
        try {
            int level = Integer.parseInt(specificationValue);
            playersCopy.removeIf(player -> player.getLevel() != level);
        } catch (NumberFormatException e) {
            sender.sendMessage("§cInvalid level specification! (Ignoring it!)");
            return playersCopy;
        }
        return playersCopy;
    }
}
