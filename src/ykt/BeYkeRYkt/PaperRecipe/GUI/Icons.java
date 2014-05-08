package ykt.BeYkeRYkt.PaperRecipe.GUI;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;


import ykt.BeYkeRYkt.PaperRecipe.Lang;
import ykt.BeYkeRYkt.PaperRecipe.PaperRecipe;
import ykt.BeYkeRYkt.PaperRecipe.Recipes.UpgradeRecipe;

public class Icons{
	
	public static ItemStack getChangeLang(){
		ItemStack item = new ItemStack(Material.WORKBENCH);
		ItemMeta meta = item.getItemMeta();
		
		meta.setDisplayName(Lang.ICON_CHANGE_LANG.toString());
		
		List<String> langs = getLangList();
		langs.add(0, Lang.AVAILABLE_LANGUAGES.toString());

		meta.setLore(langs);
		item.setItemMeta(meta);
		return item;
	}
	
	public static ItemStack getLang(String name){
		ItemStack item = new ItemStack(Material.WORKBENCH);
		ItemMeta meta = item.getItemMeta();
		
		meta.setDisplayName(name);
		File lang = new File(PaperRecipe.getInstance().getDataFolder() + "/lang/",  ChatColor.stripColor(name) + ".yml");
		YamlConfiguration conf = YamlConfiguration.loadConfiguration(lang);
		
		List<String> langs = new ArrayList<String>();
		langs.add(Lang.AUTHOR_OF_TRANSLATION.toString());
		langs.add(ChatColor.YELLOW + conf.getString("author"));
		

		meta.setLore(langs);
		item.setItemMeta(meta);
		return item;
	}
	
	public static ItemStack getNextPage(int pageIndex){
		ItemStack item = new ItemStack(Material.TORCH);
		ItemMeta meta = item.getItemMeta();
		
		meta.setDisplayName(String.valueOf(pageIndex));
		item.setItemMeta(meta);
		return item;
	}
	
	
	public static ItemStack getRecipes(){
		ItemStack item = new ItemStack(Material.BOOK);
		ItemMeta meta = item.getItemMeta();
		
		meta.setDisplayName(Lang.ICON_RECIPES.toString());
		item.setItemMeta(meta);
		return item;
	}
	
	public static ItemStack getRecipe(String name){
		ItemStack item = new ItemStack(Material.MAP);
		ItemMeta meta = item.getItemMeta();
		List<String> list = new ArrayList<String>();
		
		
		if(PaperRecipe.getInstance().getManager().isUpgradeRecipe(name)){
			list.add(ChatColor.GREEN + "Type:" + ChatColor.YELLOW + "Custom");
		}else if(PaperRecipe.getInstance().getManager().isVanillaRecipe(name)){
			list.add(ChatColor.GREEN + "Type:" + ChatColor.YELLOW + "Vanilla");
		}
		
		meta.setDisplayName(name);
		item.setItemMeta(meta);
		return item;
	}
	
	
	public static ItemStack getGiveBlueprint(){
		ItemStack item = new ItemStack(Material.PAPER);
		ItemMeta meta = item.getItemMeta();
		
		meta.setDisplayName(Lang.ICON_BLUEPRINTS.toString());
		item.setItemMeta(meta);
		return item;
	}
	
	public static ItemStack getGiveBlueprint(String name){
		ItemStack item = new ItemStack(Material.PAPER);
		ItemMeta meta = item.getItemMeta();
		
		meta.setDisplayName(name);
		item.setItemMeta(meta);
		return item;
	}
	
	public static List<String> getLangList(){
		File dir = new File(PaperRecipe.getInstance().getDataFolder(), "lang");
		List<String> lang = new ArrayList<String>();

		String[] dirlist = dir.list();
		int amount = dirlist.length;
		dir.mkdir();
		
		for(int i = 0; i < amount; ++i){
			String File = dirlist[i];
			if(File.contains(".yml")){
				String name = File.replace(".yml", "");
				lang.add(i, ChatColor.YELLOW + name);
			}
		}
		return lang;
	}
	
	
	public static List<String> getRecipesList(){
		List<String> list = new ArrayList<String>();
		
		
		for(UpgradeRecipe recipes: PaperRecipe.getInstance().getManager().getRecipes()){
			list.add(recipes.getName());
		}
		
		for(String materials: PaperRecipe.getInstance().getConfig().getStringList("minecraft-recipes-need-blueprints")){
			list.add(materials);
		}
		return list;
	}
	
	
}