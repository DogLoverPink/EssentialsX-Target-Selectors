package dogloverpink.specifications;

import java.util.ArrayList;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class WorldSpecification extends SelectorSpecification {

    @Override
    public ArrayList<Player> evalute(ArrayList<Player> players, String specificationValue, CommandSender sender) {
        ArrayList<Player> playersCopy = new ArrayList<Player>(players);
        playersCopy.removeIf(player -> !player.getWorld().getName().equalsIgnoreCase(specificationValue));
        return playersCopy;
    }
}
