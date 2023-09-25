package dogloverpink.specifications;

import java.util.ArrayList;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class LimitSpecification extends SelectorSpecification {

    @Override
    public ArrayList<Player> evalute(ArrayList<Player> players, String specificationValue, CommandSender sender) {
        ArrayList<Player> playersCopy = new ArrayList<Player>(players);
        try {
            int limit = Integer.parseInt(specificationValue);
            if (playersCopy.size() > limit) {
                playersCopy = new ArrayList<Player>(playersCopy.subList(0, limit - 1));
            }
        } catch (NumberFormatException e) {
            sender.sendMessage("§cInvalid limit specification! (Ignoring it!)");
            return playersCopy;
        }
        return playersCopy;
    }
}
