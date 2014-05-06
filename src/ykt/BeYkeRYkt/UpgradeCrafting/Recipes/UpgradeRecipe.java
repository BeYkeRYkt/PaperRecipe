package ykt.BeYkeRYkt.UpgradeCrafting.Recipes;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;

import ykt.BeYkeRYkt.UpgradeCrafting.Lang;
import ykt.BeYkeRYkt.UpgradeCrafting.UpgradeCrafting;


public class UpgradeRecipe{
	
	private List<String> players = new ArrayList<String>();
	private Recipe recipe;
	private FileConfiguration config;
	private String name;
	private File file;
	private boolean unlock;
	
	public UpgradeRecipe(String filename, Recipe recipe, boolean unlocked_default){
		this.unlock = unlocked_default;
		this.recipe = recipe;
		this.name = filename;
		
		String folder = UpgradeCrafting.getInstance().getDataFolder() + "/Recipes/";
		this.file = new File(folder, name + ".yml");
		
		this.config = YamlConfiguration.loadConfiguration(file);
		
		//Load UUID
		List<String> list = getConfig().getStringList("players-uuid");
		if(list != null){
		players = getConfig().getStringList("players-uuid");
		}
		
		
	}
	
	public boolean isUnlockedDefault(){
		return unlock;
	}
	
	public String getName(){
		return name;
	}
	
	public Recipe getBukkitRecipe(){
		return recipe;
	}
	
	public FileConfiguration getConfig(){
		return config;
	}
	
	public ItemStack getResult(){
		return recipe.getResult();
	}
	
	public List<String> getUUIDStrings(){
		return players;
	}
	
	public void addPlayerUnlock(Player player){
		if(!getUUIDStrings().contains(player.getUniqueId().toString())){
			getUUIDStrings().add(player.getUniqueId().toString());
			save("players-uuid", getUUIDStrings());
			player.sendMessage(Lang.TITLE.toString() + Lang.ADDED_NEW_RECIPE.toString() + getName());
		}
	}
	
	public void removePlayerUnlock(Player player){
		if(getUUIDStrings().contains(player.getUniqueId().toString())){
			getUUIDStrings().remove(player.getUniqueId().toString());
			save("players-uuid", getUUIDStrings());
            player.sendMessage(Lang.TITLE.toString() + Lang.REMOVED_RECIPE.toString() + getName());
		}
	}
	
	public void save(Object o, Object value){
		try {
		getConfig().set((String) o, value);
		getConfig().options().copyDefaults(true);
		getConfig().save(file);
		getConfig().options().copyDefaults(false);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}