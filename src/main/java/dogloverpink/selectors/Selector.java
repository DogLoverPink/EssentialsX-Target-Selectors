package dogloverpink.selectors;

import java.util.ArrayList;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public abstract class Selector {

    public abstract void evalute(ArrayList<Player> players, CommandSender sender);
}
