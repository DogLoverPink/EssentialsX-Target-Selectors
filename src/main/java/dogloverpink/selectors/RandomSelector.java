package dogloverpink.selectors;

import java.util.ArrayList;
import java.util.Random;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class RandomSelector extends Selector {

        Random rand = new Random();
    
        @Override
        public void evalute(ArrayList<Player> players, CommandSender sender) {
            if (players.size() < 1)
                return;
            int randomIndex = rand.nextInt(players.size());
            Player randomPlayer = players.get(randomIndex);
            players.clear();
            players.add(randomPlayer);
    
        }
    
}
