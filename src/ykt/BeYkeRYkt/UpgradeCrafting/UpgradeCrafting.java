package ykt.BeYkeRYkt.UpgradeCrafting;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import ykt.BeYkeRYkt.UpgradeCrafting.Recipes.RecipeManager;

public class UpgradeCrafting extends JavaPlugin{
	
	private RecipeManager manager;
	private static UpgradeCrafting plugin;
	private static YamlConfiguration LANG;
	private static File LANG_FILE;
	private String lang;
	private Blueprints blueprints;
	
	@Override
	public void onEnable(){		
		
		this.plugin = this;
		
		try {
			FileConfiguration fc = getConfig();
			if (!new File(getDataFolder(), "config.yml").exists()) {
				fc.options().header(
						"UpgradeCrafting v" + getDescription().getVersion()
								+ " Configuration"
								+ "\nby BeYkeRYkt");
				
				List<String> list = new ArrayList<String>(); 
				list.add("WOOD");
				list.add("WORKBENCH");
				list.add("FURNACE");
				
				fc.addDefault("Lang", "English");
				fc.addDefault("dropChance", 50);
				fc.addDefault("minecraft-recipes-need-blueprints", list);
				
				fc.options().copyDefaults(true);
				saveConfig();
				fc.options().copyDefaults(false);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

     
	this.lang = getConfig().getString("Lang");	
	this.manager = new RecipeManager(this);
	this.blueprints = new Blueprints(this);
	
	loadLang();	
	
	Bukkit.getPluginManager().registerEvents(new MainListener(this), this);
    this.getLogger().info(this.getDescription().getName() + " has been Enabled!");
	}
	
	
	@Override
	public void onDisable(){
		
	}
	
	public static UpgradeCrafting getInstance(){
		return plugin;
	}
	
	public RecipeManager getManager(){
		return manager;
	}
	
	public Blueprints getBlueprints(){
		return blueprints;
	}
	
	/**
	 * Load the lang.yml file.
	 * @return The lang.yml config.
	 */
	
	public void loadLang() {
	    File lang = new File(getDataFolder() + "/lang/", this.lang + ".yml");
	    File dir = new File(getDataFolder(), "/lang/");
	    if (!lang.exists()) {
	        try {
	            getDataFolder().mkdir();
	            dir.mkdir();
	            lang.createNewFile();
	            InputStream defConfigStream = this.getResource("/lang/" + this.lang + ".yml");
	            if (defConfigStream != null) {
	                YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
	                defConfig.save(lang);
	                Lang.setFile(defConfig);
	            }
	        } catch(IOException e) {
	            e.printStackTrace(); // So they notice
	            getLogger().severe("[UpgradeCrafting] Couldn't create language file.");
	            getLogger().severe("[UpgradeCrafting] This is a fatal error. Now reloading...");
	            dir.mkdir();
	            loadLang(); // Without it loaded, we can't send them messages
	            return;
	        }
	    }
	    YamlConfiguration conf = YamlConfiguration.loadConfiguration(lang);
	    for(Lang item:Lang.values()) {
	        if (conf.getString(item.getPath()) == null) {
	            conf.set(item.getPath(), item.getDefault());
	        }
	    }
	    Lang.setFile(conf);
	    LANG = conf;
	    LANG_FILE = lang;
	    try {
	        conf.save(getLangFile());
	    } catch(IOException e) {
	        getLogger().log(Level.WARNING, "UpgradeCrafting: Failed to save " + this.lang + ".yml.");
	        getLogger().log(Level.WARNING, "UpgradeCrafting: Report this stack trace to BeYkeRYkt.");
	        e.printStackTrace();
	    }
	}
	
	/**
	* Gets the lang.yml config.
	* @return The lang.yml config.
	*/
	public YamlConfiguration getLang() {
       return LANG;
	}

	 
	/**
	* Get the lang.yml file.
	* @return The lang.yml file.
	*/
	public File getLangFile() {
	    return LANG_FILE;
	}
	
}