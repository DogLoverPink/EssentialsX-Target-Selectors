package dogloverpink.selectors;

import java.util.ArrayList;

import org.bukkit.Location;
import org.bukkit.command.BlockCommandSender;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ClosestSelector extends Selector {


    @Override
    public void evalute(ArrayList<Player> players, CommandSender sender) {
        Location senderLocation;
        if (sender instanceof Player) {
            senderLocation = ((Player) sender).getLocation();
        } else if (sender instanceof BlockCommandSender) {
            senderLocation = ((BlockCommandSender) sender).getBlock().getLocation();
        } else {
            players.clear();
            sender.sendMessage("§cYou must be a player or command block to use this selector");
            return;
        }
        players.removeIf(player -> !player.getWorld().equals(senderLocation.getWorld()));
        Player closestPlayer = getClosestPlayer(players, senderLocation);
        players.clear();
        if (closestPlayer != null) {
            players.add(closestPlayer);
        }

    }

    public static Player getClosestPlayer(ArrayList<Player> players, Location loc) {
        Player result = null;
        double lastDistance = Double.MAX_VALUE;
        for (Player p : players) {
            double distance = loc.distanceSquared(p.getLocation());
            if (distance < lastDistance) {
                lastDistance = distance;
                result = p;
            }
        }
        return result;
    }
}
