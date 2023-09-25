package dogloverpink.selectorUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import dogloverpink.selectors.AllSelector;
import dogloverpink.selectors.ClosestSelector;
import dogloverpink.selectors.RandomSelector;
import dogloverpink.selectors.Selector;
import dogloverpink.selectors.SelfSelector;
import dogloverpink.specifications.DistanceSpecification;
import dogloverpink.specifications.GamemodeSpecification;
import dogloverpink.specifications.LevelSpecification;
import dogloverpink.specifications.LimitSpecification;
import dogloverpink.specifications.SelectorSpecification;
import dogloverpink.specifications.TagSpecification;
import dogloverpink.specifications.TeamSpecification;
import dogloverpink.specifications.WorldSpecification;

public class SelectorUtils {
    public static Map<String, SelectorSpecification> specifications = new HashMap<String, SelectorSpecification>();
    public static Map<Character, Selector> selectors = new HashMap<Character, Selector>();

    public static void EssXSelectorsInit() {
        addSelector('a', new AllSelector());
        addSelector('r', new RandomSelector());
        addSelector('p', new ClosestSelector());
        addSelector('s', new SelfSelector());

        //Add all specifications here
        addSpecification("gamemode", new GamemodeSpecification());
        addSpecification("tag", new TagSpecification());
        addSpecification("distance", new DistanceSpecification());
        addSpecification("team", new TeamSpecification());
        addSpecification("limit", new LimitSpecification());
        addSpecification("level", new LevelSpecification());
        addSpecification("world", new WorldSpecification());
    }

    public static void addSelector(char selectorLetter, Selector selector) {
        selectors.put(selectorLetter, selector);
    }

    public static void addSpecification(String specificationName, SelectorSpecification specification) {
        specifications.put(specificationName, specification);
    }

    public static boolean isInRadius(Location location, Player player, Double distance) {
		try {
        	return (location.distanceSquared(player.getLocation()) <= distance * distance);
		} catch (Exception e) {
			return false;
		}
	}

    public static Map<String, String> parseSelectorArguments(String selectorArguments) {
        Map<String, String> arguments = new HashMap<>();
        if (selectorArguments.length() < 3) {
            return arguments;
        }
        String[] parts = selectorArguments.substring(3, selectorArguments.length()).split(",");
        for (String part : parts) {
            String[] keyValue = part.split("=");
            if (keyValue.length == 2) {
                String value = keyValue[1];
                if (value.endsWith("]")) {
                    value = value.substring(0, value.length() - 1);
                }
                arguments.put(keyValue[0], value);
            }
        }
        return arguments;
    
    }

    public static void handleSelector(CommandSender sender, ArrayList<Player> players, String selectorString) {
        Selector selector = SelectorUtils.selectors.get(selectorString.charAt(1));
        if (selector == null) {
            sender.sendMessage("§cInvalid selector: @" + selectorString.charAt(1) + "!");
            players.clear();
            return;
        }
        selector.evalute(players, sender);
    }

    public static ArrayList<Player> handleSpecifications(CommandSender sender, ArrayList<Player> players,
            String specifications) {
        Map<String, String> selectorArguments = SelectorUtils.parseSelectorArguments(specifications);
        for (String specification : selectorArguments.keySet()) {
            SelectorSpecification selectorSpecification = SelectorUtils.specifications.get(specification);
            if (selectorSpecification == null) {
                sender.sendMessage("§cInvalid selector specification: " + specification + ", ignoring it!");
                continue;
            }
            String specificationValue = selectorArguments.get(specification);
            if (!(specificationValue.contains("&") || specificationValue.contains("|"))) {
                
                if (specificationValue.charAt(0) == '!') {
                    specificationValue = specificationValue.substring(1);
                    
                    players.removeAll(selectorSpecification.evalute(players, specificationValue, sender));
                    continue;
                } else {
                    players = selectorSpecification.evalute(players, specificationValue, sender);
                }
            } else {
                
                if (specificationValue.contains("|")) {
                    Matcher orMapper = Pattern.compile("[^|&]+(?![^&]*$)").matcher(specificationValue + "&");
                    ArrayList<Player> newPlayers = new ArrayList<Player>();
                    while (orMapper.find()) {
                        String group = orMapper.group();
                        if (group.charAt(0) == '!') {
                            group = group.substring(1);
                            
                            newPlayers = new ArrayList<>(players);
                            newPlayers.removeAll(selectorSpecification.evalute(newPlayers, group, sender));
                            continue;
                        } else {
                            
                            newPlayers.addAll(selectorSpecification.evalute(players, group, sender));
                        }
                    }
                    players = newPlayers;
                }
                Matcher andMatcher;
                if (specificationValue.contains("|")) 
                    andMatcher = Pattern.compile("(?<=&)[^&]+").matcher(specificationValue);
                else 
                    andMatcher = Pattern.compile("(?<=&|^)[^&]+").matcher(specificationValue);
                while (andMatcher.find()) {
                    String group = andMatcher.group();
                    if (group.charAt(0) == '!') {
                        group = group.substring(1);
                        
                        players.removeAll(selectorSpecification.evalute(players, group, sender));
                        continue;
                    } else {
                        
                        players.retainAll(selectorSpecification.evalute(players, group, sender));
                    }
                }
                // remove all duplicate players
                
                players = new ArrayList<Player>(players.stream().distinct().toList());
                

            }
        }
        return players;
    }
}
