package ykt.BeYkeRYkt.PaperRecipe.GUI;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import ykt.BeYkeRYkt.PaperRecipe.PaperRecipe;


public class GUIListener implements Listener{
	
	
	/**
	 * 
	 * FOR GUI
	 * 
	 */
	
	@EventHandler
	public void onStandartClick(InventoryClickEvent event){
		Player player = (Player) event.getWhoClicked(); 
		ItemStack clicked = event.getCurrentItem(); 
		Inventory inventory = event.getInventory(); 
		String name = inventory.getTitle();
		
		if(clicked != null && clicked.getType() != Material.AIR){
			if(PaperRecipe.getInstance().getName().equals(name)){
				
				if(Icons.getChangeLang().getItemMeta().getDisplayName().equals(clicked.getItemMeta().getDisplayName())){
					player.closeInventory();
					
					
					CustomGUIMenu menu = new CustomGUIMenu(Icons.getChangeLang().getItemMeta().getDisplayName(), 27);
					
					for(String lang: Icons.getLangList()){
						menu.addItem(Icons.getLang(lang) ,Icons.getLangList().indexOf(lang));
					}
					
					player.openInventory(menu.getInventory());
					
					//opening...
				}else if(Icons.getRecipes().getItemMeta().getDisplayName().equals(clicked.getItemMeta().getDisplayName())){
					player.closeInventory();
					//open
				}else if(Icons.getGiveBlueprint().getItemMeta().getDisplayName().equals(clicked.getItemMeta().getDisplayName())){
					player.closeInventory();
					//open
				}
				
				event.setCancelled(true);
			}else if(Icons.getLangList().contains(clicked.getItemMeta().getDisplayName())){
				
				PaperRecipe.getInstance().getConfig().options().copyDefaults(true);
				PaperRecipe.getInstance().getConfig().set("Lang", ChatColor.stripColor(clicked.getItemMeta().getDisplayName()));
				PaperRecipe.getInstance().getConfig().options().copyDefaults(false);
				PaperRecipe.getInstance().reloadPlugin();
				player.closeInventory();
				
				event.setCancelled(true);
			}
		}
	}
	
}