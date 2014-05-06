package ykt.BeYkeRYkt.UpgradeCrafting.Recipes;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;

import ykt.BeYkeRYkt.UpgradeCrafting.UpgradeCrafting;

public class RecipeManager{
	
	private UpgradeCrafting plugin;
	private ArrayList<UpgradeRecipe> customRecipes = new ArrayList<UpgradeRecipe>();
	private RecipeLoader loader;
	
	 public enum CraftType{
		SHAPED, SHAPELESS;
			//FURNACE;
	  }
	
	public RecipeManager(UpgradeCrafting plugin){
		this.plugin = plugin;
		this.loader = new RecipeLoader(this);
		loader.load();
	}
	
	public RecipeLoader getLoader(){
		return loader;
	}
	
	public List<UpgradeRecipe> getRecipes(){
		return customRecipes;
	}
	
	public UpgradeCrafting getPlugin(){
		return plugin;
	}
	
	/**
	 * 
	 * RUS: Добавление рецепта.
	 * ENG: ???
	 * 
	 * @param recipe
	 * @return
	 */
	public boolean addRecipe(UpgradeRecipe recipe){
		if(!customRecipes.contains(recipe)){
			customRecipes.add(recipe);
			Bukkit.addRecipe(recipe.getBukkitRecipe());
			plugin.getLogger().info("Add new recipe " + recipe.getName());
			return true;
		}
		return false;
	}
	
    /**
     * 
     * RUS: Удаление предмета.
     * ENG: ???
     * 
     * SOURCE: https://forums.bukkit.org/threads/how-to-remove-recipes.122712/ @Desht
     * 
     * @param recipe
     * @return
     */
    public boolean removeRecipe(UpgradeRecipe recipe){
    	Iterator<Recipe> iter = plugin.getServer().recipeIterator();
    	while (iter.hasNext()) {
    	  Recipe r = iter.next();
    	  // May not be safe to depend on == here for recipe comparison
    	  // Probably safer to compare the recipe result (an ItemStack)
    	  if (r.getResult() == recipe.getResult()) {
    	    iter.remove();
    	    return true;
    	  }
    	}
		return false;
    }
	
    /**
     * 
     * RUS: UpgradeRecipe через рецепт результат (getResult();).
     * ENG: ???
     * 
     * @param item
     * @return
     */
    public UpgradeRecipe getUpgradeRecipe(ItemStack item){
    	for(UpgradeRecipe recipe: customRecipes){
    		if(recipe.getResult().equals(item)){
    			return recipe;
    		}
    	}
		return null;
    }
    
    /**
     * 
     * RUS: UpgradeRecipe через рецепт баккита.
     * ENG: ???
     * 
     * @param bukkitrecipe
     * @return
     */
    public UpgradeRecipe getUpgradeRecipe(Recipe bukkitrecipe){

    	for(UpgradeRecipe recipe: customRecipes){
    		if(recipe.getBukkitRecipe().equals(bukkitrecipe)){
    			return recipe;
    		}
    	}
    	
		return null;
    	
    }
    
    /**
     * 
     * RUS: UpgradeRecipe через материал.
     * ENG: ???
     * 
     * @param material
     * @return
     */
    public UpgradeRecipe getUpgradeRecipe(Material material){

    	for(UpgradeRecipe recipe: customRecipes){
    		if(recipe.getBukkitRecipe().getResult().getType().equals(material)){
    			return recipe;
    		}
    	}
    	
		return null;
    	
    }
    
    /**
     * 
     * RUS: UpgradeRecipe через имя.
     * ENG: ???
     * 
     * @param filename
     * @return
     */
    public UpgradeRecipe getUpgradeRecipe(String filename){
    	for(UpgradeRecipe recipe: customRecipes){
    		if(recipe.getName().equals(filename)){
    			return recipe;
    		}
    	}
		return null;
    }
    
    /**
     * RUS: Очистка всех рецептов.
     * ENG: ???
     * 
     */
	public void clearRecipes(){
		for(UpgradeRecipe recipe: customRecipes){
			removeRecipe(recipe);
		}
		customRecipes.clear();
	}
	
	/**
	 * 
	 * RUS: Перезагрузка ._.
	 * ENG: ???
	 * 
	 */
	public void reload(){
		clearRecipes();
		loader.load();
	}
	
	/**
	 * 
	 * RUS: Проверка кастомного рецепта через результат (getResult();).
	 * ENG: ???
	 * 
	 * @param name
	 * @return
	 */
	public boolean isUpgradeRecipe(ItemStack itemStack){
		for(UpgradeRecipe recipes: customRecipes){
			if(recipes.getResult().equals(itemStack)){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 
	 * RUS: Проверка кастомного рецепта через имя.
	 * ENG: ???
	 * 
	 * @param name
	 * @return
	 */
	public boolean isUpgradeRecipe(String name){
		for(UpgradeRecipe recipes: customRecipes){
			if(recipes.getName().equals(name)){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 
	 * RUS: Может ли игрок создать предмет ?
	 * ENG: ???
	 * 
	 * @param player
	 * @param recipe
	 * @return
	 */
	public boolean canPlayerCreateItem(Player player, Recipe recipe){
		//Если он есть в списке кастомных
		if(isUpgradeRecipe(recipe.getResult())){
			UpgradeRecipe uprecipe = getUpgradeRecipe(recipe.getResult());
			//Если он по дефолту закрыт, но игрок открыл его через чертеж
			if(!uprecipe.isUnlockedDefault() && uprecipe.getUUIDStrings().contains(player.getUniqueId().toString())){
			return true;
			
			//Если он по дефолту открыт
			}else if(uprecipe.isUnlockedDefault()){
			return true;
			}
		
		//Если он есть в списке ванильных рацептов
		}else if(isVanillaRecipe(recipe.getResult().getType().name())){
			//Если игрок его открыл
			if(plugin.getBlueprints().hasBlueprint(player, recipe.getResult().getType().name())){
				return true;
			}
		
		//Если его нету ни в ванильных и ни в кастомных 
		}else if(!isVanillaRecipe(recipe.getResult().getType().name()) && !isUpgradeRecipe(recipe.getResult())){
			return true;
		}
		return false;
	}

	
	/**
	 * 
	 * RUS: Проверка имени материала в конфиге.
	 * ENG: ??? 
	 * 
	 * @param material
	 * @return
	 */
	public boolean isVanillaRecipe(String material) {
		List<String> list = plugin.getConfig().getStringList("minecraft-recipes-need-blueprints");
		for(String name: list){
		
		if(name.equals(material)){
			return true;
		}
		}
		
		return false;
	}
}