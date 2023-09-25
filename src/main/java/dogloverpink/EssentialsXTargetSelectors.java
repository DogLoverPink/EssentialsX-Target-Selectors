package dogloverpink;

import org.bukkit.plugin.java.JavaPlugin;

import dogloverpink.events.PlayerCommandExecute;
import dogloverpink.events.ServerCommandExecute;
import dogloverpink.events.TabEvent;
import dogloverpink.selectorUtils.SelectorUtils;
import dogloverpink.selectors.AllSelector;

import org.bukkit.event.EventHandler;

public class EssentialsXTargetSelectors extends JavaPlugin {

    @EventHandler

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new PlayerCommandExecute(), this);
        getServer().getPluginManager().registerEvents(new ServerCommandExecute(), this);
        getServer().getPluginManager().registerEvents(new TabEvent(), this);
        AllSelector.plugin = this;
        SelectorUtils.EssXSelectorsInit();
    }

}
