package dogloverpink.specifications;

import java.util.ArrayList;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public abstract class SelectorSpecification {

    public abstract ArrayList<Player> evalute(ArrayList<Player> players, String specificationValue, CommandSender sender);
    
}
