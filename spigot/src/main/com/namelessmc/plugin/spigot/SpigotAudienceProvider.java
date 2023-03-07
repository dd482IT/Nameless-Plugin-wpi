package com.namelessmc.plugin.spigot;

import com.namelessmc.plugin.bukkit.BukkitNamelessPlugin;
import com.namelessmc.plugin.bukkit.audiences.BukkitNamelessConsole;
import com.namelessmc.plugin.bukkit.audiences.BukkitNamelessPlayer;
import com.namelessmc.plugin.common.audiences.AbstractAudienceProvider;
import com.namelessmc.plugin.common.audiences.NamelessConsole;
import com.namelessmc.plugin.common.audiences.NamelessPlayer;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.Collection;
import java.util.UUID;
import java.util.stream.Collectors;

public class SpigotAudienceProvider extends AbstractAudienceProvider {

	private final BukkitAudiences audiences;

	SpigotAudienceProvider(final BukkitNamelessPlugin bukkitPlugin) {
		this.audiences = BukkitAudiences.create(bukkitPlugin);
	}

	private void dispatchCommand(final String command) {
		Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command);
	}

	@Override
	public NamelessConsole console() {
		return new BukkitNamelessConsole(audiences.console());
	}

	@Override
	public Audience broadcast() {
		return audiences.all();
	}

	public NamelessPlayer bukkitToNamelessPlayer(final Player bukkitPlayer) {
		return bukkitPlayer == null
				? null
				: new BukkitNamelessPlayer(this.audiences.player(bukkitPlayer), bukkitPlayer);
	}

	@Override
	public NamelessPlayer player(UUID uuid) {
		return bukkitToNamelessPlayer(Bukkit.getPlayer(uuid));
	}

	@Override
	public NamelessPlayer playerByUsername(String username) {
		return bukkitToNamelessPlayer(Bukkit.getPlayerExact(username));
	}

	@Override
	public Collection<NamelessPlayer> onlinePlayers() {
		return Bukkit.getOnlinePlayers().stream()
				.map(p -> new BukkitNamelessPlayer(this.audiences.player(p), p))
				.collect(Collectors.toUnmodifiableList());
	}
}
