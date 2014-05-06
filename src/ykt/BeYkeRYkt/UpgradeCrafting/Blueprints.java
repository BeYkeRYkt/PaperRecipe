package ykt.BeYkeRYkt.UpgradeCrafting;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import ykt.BeYkeRYkt.UpgradeCrafting.Recipes.UpgradeRecipe;

public class Blueprints{
	
	private UpgradeCrafting plugin;
	
	public Blueprints(UpgradeCrafting plugin){
		this.plugin = plugin;
	}
	
	public boolean hasBlueprint(Player p, String material){
        List<String> pidlist = plugin.getConfig().getStringList("players." + p.getUniqueId());
        
        if(pidlist.contains(material)){
        	return true;
        }
        
        return false;
    }
	
	public ItemStack createBlueprintVanilla(){
        ItemStack IS = new ItemStack(Material.PAPER);
        ItemMeta IM = IS.getItemMeta();
        IM.setDisplayName(Lang.BLUEPRINT.toString());
        List<String> materials = plugin.getConfig().getStringList("minecraft-recipes-need-blueprints");
        Random r = new Random();
        int number = r.nextInt(materials.size());
        String name = materials.get(number);
        
        List<String> lore = new ArrayList<String>();
        lore.add(Lang.FOR_CREATE_ITEM.toString());
        lore.add(ChatColor.YELLOW + name);
        
        IM.setLore(lore);
        IS.setItemMeta(IM);
        return IS;
    }

	public ItemStack createBlueprintCustom(){
        ItemStack IS = new ItemStack(Material.PAPER);
        ItemMeta IM = IS.getItemMeta();
        IM.setDisplayName(Lang.BLUEPRINT.toString());
        List<UpgradeRecipe> materials = plugin.getManager().getRecipes();
        Random r = new Random();
        int number = r.nextInt(materials.size());
        String name = materials.get(number).getName();
        
        List<String> lore = new ArrayList<String>();
        lore.add(Lang.FOR_CREATE_ITEM.toString());
        lore.add(ChatColor.YELLOW + name);
        
        IM.setLore(lore);
        IS.setItemMeta(IM);
        return IS;
    }
	
    public void addBlueprintVanilla(Player p, String material){
        List<String> playerCon = plugin.getConfig().getStringList("players."+p.getUniqueId());
        if(!playerCon.contains(material)){
            playerCon.add(material);
            plugin.getConfig().set("players." + p.getUniqueId(), playerCon);
            p.sendMessage(Lang.TITLE.toString() + Lang.ADDED_NEW_RECIPE.toString() + material);
            plugin.saveConfig();
        }
    }
    public void removeBlueprintVanilla(Player p, String material){
        List<String> playerCon = plugin.getConfig().getStringList("players."+p.getUniqueId());
        if(playerCon.contains(material)){
              for(String strings: playerCon){
                if(strings.equals(material)){
                    playerCon.remove(material);
                    plugin.getConfig().set("players." + p.getUniqueId(), playerCon);
                    p.sendMessage(Lang.TITLE.toString() + Lang.REMOVED_RECIPE.toString() + material);
                    plugin.saveConfig();
                }
            }
        }
    }
	
}