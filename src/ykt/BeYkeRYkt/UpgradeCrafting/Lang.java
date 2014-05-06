package ykt.BeYkeRYkt.UpgradeCrafting;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;

/**
 * An enum for requesting strings from the language file.
 */
public enum Lang {
    TITLE("title-name", "&4[&fUpgradeCrafting&4]:"),
    BLUEPRINT("blueprint", "&9Blueprint"), 
    FOR_CREATE_ITEM("for-create-item", "&aTo create an item:"), 
    ADDED_NEW_RECIPE("added-new-recipe", "&aYou've learned a new blueprint:&e "),
    REMOVED_RECIPE("removed-recipe", "&cYou forgot the recipe:&e "),
    YOU_KNOW_THIS_BLUEPRINT("you-know-this-blueprint", "&cYou have already studied this blueprint."),
    NEED_BLUEPRINT("need-blueprint", "&cTo create an item it is necessary to study blueprint");

    private String path;
    private String def;
    private static YamlConfiguration LANG;

    /**
     * Lang enum constructor.
     *
     * @param path The string path.
     * @param start The default string.
     */
    Lang(String path, String start) {
        this.path = path;
        this.def = start;
    }

    /**
     * Set the {@code YamlConfiguration} to use.
     *
     * @param config The config to set.
     */
    public static void setFile(YamlConfiguration config) {
        LANG = config;
    }

    @Override
    public String toString() {
        if (this == TITLE)
            return ChatColor.translateAlternateColorCodes('&', LANG.getString(this.path, def)) + " ";
        return ChatColor.translateAlternateColorCodes('&', LANG.getString(this.path, def));
    }

    /**
     * Get the default value of the path.
     *
     * @return The default value of the path.
     */
    public String getDefault() {
        return this.def;
    }

    /**
     * Get the path to the string.
     *
     * @return The path to the string.
     */
    public String getPath() {
        return this.path;
    }
}