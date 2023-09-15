package net.framedev;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import net.framedev.animations.nmsanimation.listeners.CaseAnimationEndListener;
import net.framedev.events.ClickBlock;
import net.framedev.choice.ChoiceClick;
import net.framedev.events.EquItems;
import net.framedev.guis.ClickGUI;
import net.framedev.events.SetCase;
import net.framedev.api.Holograms;
import net.framedev.others.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public class Main extends JavaPlugin {

    public static List<ItemStack> items = new ArrayList<>(); // Список предметов для анимации
    private static Main instance;
    public static boolean isOpen = false;
    public static String openCaseName;
    public ProtocolManager protocolManager;

    @Override
    public void onEnable() {
    	PluginManager pm = getServer().getPluginManager();
        try {
            if (pm.getPlugin("HolographicDisplays") == null || pm.getPlugin("ProtocolLib") == null) {
                getLogger().warning("No install HolographicDisplays and ProtocolLib plugins.");
                pm.disablePlugin(this);
            }
        } catch (NullPointerException | NoClassDefFoundError ignored) {

        }
        instance = this;
        protocolManager = ProtocolLibrary.getProtocolManager();
        new Update().checkForUpdates();
        saveDefaultConfig();
        saveConfig();
        pm.registerEvents(new ClickBlock(), this);
        pm.registerEvents(new ClickGUI(), this);
        pm.registerEvents(new SetCase(), this);
        pm.registerEvents(new ChoiceClick(), this);
        pm.registerEvents(new EquItems(), this);
        pm.registerEvents(new CaseAnimationEndListener(), this);
        getCommand("framecases").setExecutor(new Commands());
        getCommand("framecases").setTabCompleter(new TabCommands());
        Holograms.hologram(Holograms.locationCase());
        BlockParticles.startStarfallAnimation();
        runMetrics();
    }

    public void runMetrics() {
        Metrics metrics = new Metrics(this, 19797);
        metrics.addCustomChart(new Metrics.SimplePie("holograms", () -> String.valueOf(this.getConfig().getStringList("holograms"))));
    }

    public static Main getInstance() {
        return instance;
    }
}
