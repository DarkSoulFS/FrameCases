package net.framedev;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import net.framedev.animations.nmsanimation.listeners.CaseAnimationEndListener;
import net.framedev.events.ClickBlock;
import net.framedev.animations.CaseGUIAnimation;
import net.framedev.choice.ChoiceClick;
import net.framedev.events.EquItems;
import net.framedev.guis.ClickGUI;
import net.framedev.events.SetCase;
import net.framedev.api.Holograms;
import net.framedev.guis.GUI;
import net.framedev.others.*;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public class Main extends JavaPlugin implements Listener {

    public static List<ItemStack> items = new ArrayList<>(); // Список предметов для анимации
    private static Main instance;
    public static boolean isOpen = false;
    public static String openCaseName;
    public ProtocolManager protocolManager;

    @Override
    public void onEnable() {

        try {
            if (Bukkit.getServer().getPluginManager().getPlugin("HolographicDisplays") == null || Bukkit.getServer().getPluginManager().getPlugin("ProtocolLib") == null) {
                Bukkit.getLogger().warning("Install HolographicDisplays and ProtocolLib plugins.");
                Bukkit.getPluginManager().disablePlugin(this);
            }
        } catch (NullPointerException ignored) {

        }
        instance = this;
        protocolManager = ProtocolLibrary.getProtocolManager();
        new Update().checkForUpdates();
        saveDefaultConfig();
        saveConfig();
        getServer().getPluginManager().registerEvents(this, this);
        getServer().getPluginManager().registerEvents(new ClickBlock(), this);
        getServer().getPluginManager().registerEvents(new ClickGUI(), this);
        getServer().getPluginManager().registerEvents(new SetCase(), this);
        getServer().getPluginManager().registerEvents(new GUI(this), this);
        getServer().getPluginManager().registerEvents(new ChoiceClick(), this);
        getServer().getPluginManager().registerEvents(new CaseGUIAnimation(), this);
        getServer().getPluginManager().registerEvents(new EquItems(), this);
        getServer().getPluginManager().registerEvents(new CaseAnimationEndListener(), this);
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
