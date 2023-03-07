package com.namelessmc.plugin.oldbukkit.audiences;

import com.namelessmc.plugin.bukkit.audiences.BukkitNamelessConsole;
import com.namelessmc.plugin.common.audiences.AbstractAudienceProvider;
import com.namelessmc.plugin.common.audiences.NamelessConsole;
import com.namelessmc.plugin.common.audiences.NamelessPlayer;
import net.kyori.adventure.audience.Audience;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.*;
import java.util.stream.Collectors;

public class OldBukkitAudienceProvider extends AbstractAudienceProvider {

	private void dispatchCommand(final String command) {
		Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command);
	}

	@Override
	public NamelessConsole console() {
		return new BukkitNamelessConsole(new LegacyCommandSenderAudience(Bukkit.getConsoleSender()));
	}

	@Override
	public Audience broadcast() {
		final Player[] bukkitPlayers = Bukkit.getOnlinePlayers();
		final List<Audience> audiences = new ArrayList<>(bukkitPlayers.length + 1);
		for (Player player : bukkitPlayers) {
			audiences.add(new LegacyCommandSenderAudience(player));
		}
		audiences.add(new LegacyCommandSenderAudience(Bukkit.getConsoleSender()));
		return Audience.audience(audiences);
	}

	public NamelessPlayer bukkitToNamelessPlayer(final Player bukkitPlayer) {
		return bukkitPlayer == null
				? null
				: new OldBukkitNamelessPlayer(bukkitPlayer);
	}

	@Override
	public NamelessPlayer player(UUID uuid) {
		for (Player player : Bukkit.getOnlinePlayers()) {
			if (player.getUniqueId().equals(uuid)) {
				return bukkitToNamelessPlayer(player);
			}
		}
		return null;
	}

	@Override
	public NamelessPlayer playerByUsername(String username) {
		return bukkitToNamelessPlayer(Bukkit.getPlayerExact(username));
	}

	@Override
	public Collection<NamelessPlayer> onlinePlayers() {
		return Arrays.stream(Bukkit.getOnlinePlayers())
				.map(OldBukkitNamelessPlayer::new)
				.collect(Collectors.toUnmodifiableList());
	}
}
