package dogloverpink.specifications;

import java.util.ArrayList;

import org.bukkit.Location;
import org.bukkit.command.BlockCommandSender;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import dogloverpink.selectorUtils.SelectorUtils;

public class DistanceSpecification extends SelectorSpecification {

    @Override
    public ArrayList<Player> evalute(ArrayList<Player> players, String specificationValue, CommandSender sender) {
        ArrayList<Player> playersCopy = new ArrayList<Player>(players);
        try {
            if (specificationValue.startsWith(".."))
                specificationValue = specificationValue.substring(2);
            double distance = Double.parseDouble(specificationValue);
            Location senderLocation;
            if (sender instanceof Player) {
                senderLocation = ((Player) sender).getLocation();
            } else if (sender instanceof BlockCommandSender) {
                senderLocation = ((BlockCommandSender) sender).getBlock().getLocation();
            } else {
                sender.sendMessage("§cConsole can't use distance specification!");
                return playersCopy;
            }
            playersCopy.removeIf(player -> !SelectorUtils.isInRadius(senderLocation, player, distance));
        } catch (NumberFormatException e) {
            sender.sendMessage("§cInvalid distance specification! (Ignoring it!)");
            return playersCopy;
        }
        return playersCopy;

    }

}
