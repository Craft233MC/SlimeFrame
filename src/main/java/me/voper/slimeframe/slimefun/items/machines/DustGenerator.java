package me.voper.slimeframe.slimefun.items.machines;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.attributes.RecipeDisplayItem;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import me.mrCookieSlime.CSCoreLibPlugin.general.Inventory.ChestMenu;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import me.voper.slimeframe.slimefun.items.abstracts.AbstractSelectorMachine;
import me.voper.slimeframe.utils.MachineUtils;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public class DustGenerator extends AbstractSelectorMachine implements RecipeDisplayItem {

    private static final String BLOCK_KEY = "dust_selector";
    private static final List<ItemStack> DUSTS = new ArrayList<>(List.of(
            MachineUtils.SELECTOR,
            SlimefunItems.ALUMINUM_DUST,
            SlimefunItems.COPPER_DUST,
            SlimefunItems.GOLD_DUST,
            SlimefunItems.IRON_DUST,
            SlimefunItems.LEAD_DUST,
            SlimefunItems.MAGNESIUM_DUST,
            SlimefunItems.SILVER_DUST,
            SlimefunItems.TIN_DUST,
            SlimefunItems.ZINC_DUST
    ));
    
    public DustGenerator(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
        this.outputAmount = 2;
    }

    @Override
    protected ItemStack getProgressBar() {
        return new ItemStack(Material.STONE_PICKAXE);
    }

    @Override
    public void postRegister() {
        registerRecipe(1, new ItemStack(Material.COBBLESTONE), new ItemStack(Material.REDSTONE));
        registerRecipe(1, new ItemStack(Material.COBBLED_DEEPSLATE), new ItemStack(Material.REDSTONE));
    }

    @Override
    protected ChestMenu.MenuClickHandler onSelectorClick(BlockMenu menu) {
        return (player, slot, itemstack, clickAction) -> {
            int dustSelector = Integer.parseInt(BlockStorage.getLocationInfo(menu.getLocation(), BLOCK_KEY));
            if (++dustSelector >= DUSTS.size()) {
                BlockStorage.addBlockInfo(menu.getBlock(), BLOCK_KEY, String.valueOf(0));
                dustSelector = 0;
            } else {
                BlockStorage.addBlockInfo(menu.getBlock(), BLOCK_KEY, String.valueOf(dustSelector));
            }
            menu.replaceExistingItem(getSelectorSlot(), DUSTS.get(dustSelector));
            return false;
        };
    }

    @Override
    protected void onNewInstance(BlockMenu menu, Block b) {
        super.onNewInstance(menu, b);
        if (BlockStorage.hasBlockInfo(b) || BlockStorage.getLocationInfo(b.getLocation(), BLOCK_KEY) == null) {
            BlockStorage.addBlockInfo(b, BLOCK_KEY, String.valueOf(0));
        } else {
            String dustSelector = BlockStorage.getLocationInfo(b.getLocation(), BLOCK_KEY);
            if (dustSelector == null) {
                return;
            }
            menu.replaceExistingItem(getSelectorSlot(), DUSTS.get(Integer.parseInt(dustSelector)));
        }
    }

    @Override
    protected boolean checkCraftConditions(BlockMenu menu) {
        return menu.getItemInSlot(getSelectorSlot()).getType() != Material.FIREWORK_STAR;
    }

    @Override
    protected void onCraftConditionsNotMet(BlockMenu menu) {
        MachineUtils.replaceExistingItemViewer(menu, getStatusSlot(), new CustomItemStack(Material.BARRIER, ChatColor.RED + "Select a dust to generate!"));
    }
    
    @Nonnull
    @Override
    public List<ItemStack> getDisplayRecipes() {
        List<ItemStack> dusts = new ArrayList<>(DUSTS);
        dusts.remove(MachineUtils.SELECTOR);
        
        List<ItemStack> displayRecipes = new ArrayList<>();
        for (ItemStack dust : dusts) {
            ItemStack clone = dust.clone();
            clone.setAmount(production * outputAmount);
            displayRecipes.add(new ItemStack(Material.COBBLESTONE));
            displayRecipes.add(clone);
            displayRecipes.add(new ItemStack(Material.COBBLED_DEEPSLATE));
            displayRecipes.add(clone);
        }

        return displayRecipes;
    }
}
