package ykt.BeYkeRYkt.PaperRecipe;

import java.util.Random;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;

import ykt.BeYkeRYkt.PaperRecipe.Recipes.UpgradeRecipe;

public class MainListener implements Listener{
	
	private PaperRecipe plugin;

	public MainListener(PaperRecipe plugin){
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onItemCraft(PrepareItemCraftEvent event){
		Recipe recipe = event.getRecipe();
		Player player = (Player) event.getView().getPlayer();
		if(!plugin.getManager().canPlayerCreateItem(player, recipe.getResult())){
		  event.getInventory().setResult(null);
		  player.sendMessage(Lang.TITLE.toString() + Lang.NEED_BLUEPRINT);
		  player.getWorld().playSound(player.getLocation(), Sound.ITEM_BREAK, 1, 1);
	    }
	}
	
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event){
		Player player = event.getPlayer();
		ItemStack item = event.getItem();
		
		if(item != null){
		if(item.getType() == Material.PAPER && Lang.BLUEPRINT.toString().equals(item.getItemMeta().getDisplayName())){
			String name = ChatColor.stripColor(item.getItemMeta().getLore().get(1));
			
			if(plugin.getManager().isVanillaRecipe(name) && !plugin.getVanillaBlueprints().hasBlueprint(player, name)){
				plugin.getVanillaBlueprints().addBlueprintVanilla(player, name);
			}else if(plugin.getManager().isVanillaRecipe(name) && plugin.getVanillaBlueprints().hasBlueprint(player, name)){
			     player.sendMessage(Lang.TITLE.toString() + Lang.YOU_KNOW_THIS_BLUEPRINT.toString());
			
			}else if(plugin.getManager().isUpgradeRecipe(name)){
				UpgradeRecipe recipe = plugin.getManager().getUpgradeRecipe(name);
				if(!plugin.getManager().canPlayerCreateItem(player, recipe.getResult())){
					recipe.addPlayerUnlock(player);
				}else if(plugin.getManager().canPlayerCreateItem(player, recipe.getResult())){
					player.sendMessage(Lang.TITLE.toString() + Lang.YOU_KNOW_THIS_BLUEPRINT.toString());
				}
			}
		}
		}
	}
	
	@EventHandler
    public void onDeath(EntityDeathEvent event){
        int chance = plugin.getConfig().getInt("dropChance");
        Random r= new Random();
        int rand = r.nextInt(101);
        if (rand <= chance){
        	int blueprint = r.nextInt(2);
        	
            ItemStack item = null;
            
            if(blueprint == 0){
            item = plugin.getVanillaBlueprints().createBlueprintVanilla();
            }else if(blueprint == 1){
            item = plugin.getVanillaBlueprints().createBlueprintCustom();
            }
            event.getDrops().add(item);
        }
    }
}