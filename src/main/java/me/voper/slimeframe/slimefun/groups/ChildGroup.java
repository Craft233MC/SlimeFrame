package me.voper.slimeframe.slimefun.groups;

import io.github.thebusybiscuit.slimefun4.api.SlimefunAddon;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.libraries.commons.lang.Validate;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

public class ChildGroup extends ItemGroup {

    private final MasterGroup master;

    @ParametersAreNonnullByDefault
    public ChildGroup(NamespacedKey key, MasterGroup master, ItemStack item) {
        this(key, master, item, 3);
    }

    @ParametersAreNonnullByDefault
    public ChildGroup(NamespacedKey key, MasterGroup master, ItemStack item, int tier) {
        super(key, item, tier);

        Validate.notNull(master, "The master group cannot be null");

        this.master = master;
        master.addSubGroup(this);
    }

    @Override
    public final boolean isVisible(@Nonnull Player p) {
        return false;
    }

    @Override
    public final boolean isAccessible(@Nonnull Player p) {
        return true;
    }

    public final @Nonnull MasterGroup getParent() {
        return master;
    }

    @Override
    public final void register(@Nonnull SlimefunAddon addon) {
        super.register(addon);

        if (!master.isRegistered()) {
            master.register(addon);
        }
    }

}
