package ykt.BeYkeRYkt.PaperRecipe.Recipes;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;

import ykt.BeYkeRYkt.PaperRecipe.Recipes.RecipeManager.CraftType;

public class RecipeLoader{
	
	private RecipeManager manager;
	
	public RecipeLoader(RecipeManager manager){
		this.manager = manager;
	}
	
	public RecipeManager getManager(){
		return manager;
	}
	
	public void load(){
        File dir = new File(manager.getPlugin().getDataFolder(), "Recipes");
		if(!dir.exists()){
		    dir.mkdirs();
		    load();
		}else{
		String[] dirlist = dir.list();
		int amount = dirlist.length;
		dir.mkdir();
		
		for(int i = 0; i < amount; ++i){
			String File = dirlist[i];
			if(File.contains(".yml")){
				File recipe = new File(dir, File);
				String name = File.replace(".yml", "");
				
				ConfigurationSection config = YamlConfiguration.loadConfiguration(recipe);
				String type = config.getString("type-recipe");
				if(type.equalsIgnoreCase(CraftType.SHAPED.toString())){
					loadShaped(config, name);
					
				}else if(type.equalsIgnoreCase(CraftType.SHAPELESS.toString())){
				    loadShapeless(config, name);
					
				}
			}   
	    }
		}
	}
	
	
	/**
	 * 
	 * ENG: Shapeless loader
	 * RUS: Shapeless короч
	 * 
	 * @param config - config recipe
	 */
	private void loadShapeless(ConfigurationSection config, String filename) {
	    String display = config.getString("item-name");
	    String material = config.getString("item-material");
	    List<String> lore = config.getStringList("item-lore");
	    int damage = config.getInt("item-damage");
	    int amount = config.getInt("item-amount");
	    
	    //For LeatherArmorMeta
	    Color color = config.getColor("armor-color");
	
        ItemStack resultStack = new ItemStack(Material.getMaterial(material), amount, (short) damage);
        
        //===============CUSTOMIZE===========//
        if(display != null || lore != null || color != null){
          if(!material.startsWith("LEATHER")){
          ItemMeta meta = resultStack.getItemMeta();
          
          // О боже...
          if(display != null){
        	display = ChatColor.translateAlternateColorCodes('&', display);
        	meta.setDisplayName(display);  
          }
          
          //Мои глаза! D:
          if(lore != null){
        	
          List<String> modLore = new ArrayList<String>();
           
          for(String lores: lore){
              lores = ChatColor.translateAlternateColorCodes('&', lores);
              modLore.add(lores);
          }
          
          meta.setLore(modLore);
          }
          
          //Ставим мету
          resultStack.setItemMeta(meta);
        
          }else if(material.startsWith("LEATHER_")){
        	LeatherArmorMeta meta = (LeatherArmorMeta) resultStack.getItemMeta();

            if(display != null){
          	display = ChatColor.translateAlternateColorCodes('&', display);
          	meta.setDisplayName(display);  
            }

            if(lore != null){
          	
            List<String> modLore = new ArrayList<String>();
             
            for(String lores: lore){
                lores = ChatColor.translateAlternateColorCodes('&', lores);
                modLore.add(lores);
            }
            
            meta.setLore(modLore);
            }
        	  
            if(color != null){
            	meta.setColor(color);
            }
            
            resultStack.setItemMeta(meta);
          }
        }
        //==============END====================//
        
        ShapelessRecipe recipe = new ShapelessRecipe(resultStack);

        for(String list: config.getStringList("ingredients")){
        	recipe.addIngredient(Material.getMaterial(list));
        }
        
        UpgradeRecipe uprecipe = new UpgradeRecipe(filename, recipe, config.getBoolean("is_unlock_default"));
        getManager().addRecipe(uprecipe);
	}

	
	/**
	 * 
	 * ENG: Shaped loader
	 * RUS: Shaped короч
	 * 
	 * @param config - config recipe
	 */
	private void loadShaped(ConfigurationSection config, String filename) {
		    String display = config.getString("item-name");
		    String material = config.getString("item-material");
		    List<String> lore = config.getStringList("item-lore");
		    int damage = config.getInt("item-damage");
		    int amount = config.getInt("item-amount");
		    
		    //For LeatherArmorMeta
		    Color color = config.getColor("armor-color");
		
            ItemStack resultStack = new ItemStack(Material.getMaterial(material), amount, (short) damage);
            
            //===============CUSTOMIZE===========//
            if(display != null || lore != null || color != null){
              if(!material.startsWith("LEATHER")){
              ItemMeta meta = resultStack.getItemMeta();


              if(display != null){
            	display = ChatColor.translateAlternateColorCodes('&', display);
            	meta.setDisplayName(display);  
              }

              if(lore != null){
            	
              List<String> modLore = new ArrayList<String>();
               
              for(String lores: lore){
                  lores = ChatColor.translateAlternateColorCodes('&', lores);
                  modLore.add(lores);
              }
              
              meta.setLore(modLore);
              }
              
              //Ставим мету
              resultStack.setItemMeta(meta);
            
              }else if(material.startsWith("LEATHER_")){
            	LeatherArmorMeta meta = (LeatherArmorMeta) resultStack.getItemMeta();

                if(display != null){
              	display = ChatColor.translateAlternateColorCodes('&', display);
              	meta.setDisplayName(display);  
                }
                
                if(lore != null){
              	
                List<String> modLore = new ArrayList<String>();
                 
                for(String lores: lore){
                    lores = ChatColor.translateAlternateColorCodes('&', lores);
                    modLore.add(lores);
                }
                
                meta.setLore(modLore);
                }
            	  
                if(color != null){
                	meta.setColor(color);
                }
                
                resultStack.setItemMeta(meta);
              }
            }
            //==============END====================//
            
            ShapedRecipe recipe = new ShapedRecipe(resultStack);
            List<String> shape = (List<String>) config.get("shape");
            recipe.shape(shape.toArray(new String[shape.size()]));

            
            for(String key : config.getConfigurationSection("ingredients").getKeys(false)){
                
            	String mat = config.getString("ingredients." + key);
               
            	recipe.setIngredient(key.charAt(0), Material.getMaterial(mat));
            }
            
            UpgradeRecipe uprecipe = new UpgradeRecipe(filename, recipe, config.getBoolean("is_unlock_default"));
            getManager().addRecipe(uprecipe);
	}
	
}