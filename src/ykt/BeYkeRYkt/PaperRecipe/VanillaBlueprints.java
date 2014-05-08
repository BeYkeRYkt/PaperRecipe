package ykt.BeYkeRYkt.PaperRecipe;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import ykt.BeYkeRYkt.PaperRecipe.Recipes.UpgradeRecipe;


public class VanillaBlueprints{
	
	private PaperRecipe plugin;
	
	public VanillaBlueprints(PaperRecipe plugin){
		this.plugin = plugin;
	}
	
	public boolean hasBlueprint(Player p, String material){
        List<String> pidlist = plugin.getConfig().getStringList("players." + material);
        
        if(pidlist.contains(p.getUniqueId().toString())){
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
        List<String> playerCon = plugin.getConfig().getStringList("players."+ material);
        if(!playerCon.contains(p.getUniqueId().toString())){
            playerCon.add(p.getUniqueId().toString());
            plugin.getConfig().set("players." + material, playerCon);
            p.sendMessage(Lang.TITLE.toString() + Lang.ADDED_NEW_RECIPE.toString() + material);
            plugin.saveConfig();
			p.getWorld().playSound(p.getLocation(), Sound.LEVEL_UP, 1, 1);
        }
    }
    public void removeBlueprintVanilla(Player p, String material){
        List<String> playerCon = plugin.getConfig().getStringList("players."+material);
        if(playerCon.contains(p.getUniqueId().toString())){
              for(String strings: playerCon){
                if(strings.equals(p.getUniqueId().toString())){
                    playerCon.remove(p.getUniqueId().toString());
                    plugin.getConfig().set("players." + material, playerCon);
                    p.sendMessage(Lang.TITLE.toString() + Lang.REMOVED_RECIPE.toString() + material);
                    plugin.saveConfig();
                }
            }
        }
    }
	
}