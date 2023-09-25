package dogloverpink.specifications;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Team;

public class TeamSpecification extends SelectorSpecification {

    @Override
    public ArrayList<Player> evalute(ArrayList<Player> players, String specificationValue, CommandSender sender) {
        ArrayList<Player> playersCopy = new ArrayList<Player>(players);
        playersCopy.removeIf(player -> !isPlayerOnTeam(player, specificationValue));
        return playersCopy;
    }

    public static boolean isPlayerOnTeam(Player p, String teamName) {
        Team d = Bukkit.getServer().getScoreboardManager().getMainScoreboard().getTeam(teamName);

        if (d == null)
            return false;

        if (d.getEntries().contains(p.getName()))
            return true;

        return false;// default return
    }
}
