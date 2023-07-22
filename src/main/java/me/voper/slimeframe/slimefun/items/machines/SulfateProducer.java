package me.voper.slimeframe.slimefun.items.machines;

import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.core.attributes.RecipeDisplayItem;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import lombok.Setter;
import lombok.experimental.Accessors;
import me.voper.slimeframe.slimefun.groups.Groups;
import me.voper.slimeframe.slimefun.items.abstracts.AbstractProcessorMachine;
import me.voper.slimeframe.slimefun.items.multiblocks.Foundry;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import java.util.List;

@Accessors(chain = true)
public class SulfateProducer extends AbstractProcessorMachine implements RecipeDisplayItem {

    @Setter
    private int production = 1;

    public SulfateProducer(SlimefunItemStack item, ItemStack[] recipe) {
        super(Groups.MACHINES, item, Foundry.RECIPE_TYPE, recipe);
    }

    @Override
    public void postRegister() {
        super.postRegister();
        registerRecipe(5, new ItemStack(Material.BASALT, 4), new SlimefunItemStack(SlimefunItems.SULFATE, 4 * production));
    }

    @Override
    protected ItemStack getProgressBar() {
        return new ItemStack(Material.DIAMOND_PICKAXE);
    }

    @Nonnull
    @Override
    public List<ItemStack> getDisplayRecipes() {
        return List.of(new ItemStack(Material.BASALT, 4), new SlimefunItemStack(SlimefunItems.SULFATE, 4 * production));
    }
}
