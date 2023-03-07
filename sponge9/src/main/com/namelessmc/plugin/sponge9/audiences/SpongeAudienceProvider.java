package com.namelessmc.plugin.sponge9.audiences;

import com.namelessmc.plugin.common.audiences.AbstractAudienceProvider;
import com.namelessmc.plugin.common.audiences.NamelessConsole;
import com.namelessmc.plugin.common.audiences.NamelessPlayer;
import net.kyori.adventure.audience.Audience;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.entity.living.player.server.ServerPlayer;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

public class SpongeAudienceProvider extends AbstractAudienceProvider {

	public SpongeAudienceProvider() {}

	@Override
	public NamelessConsole console() {
		return new SpongeNamelessConsole();
	}

	@Override
	public Audience broadcast() {
		return Sponge.server().broadcastAudience();
	}

	private NamelessPlayer spongeToNamelessPlayer(final Optional<ServerPlayer> optionalPlayer) {
		if (optionalPlayer.isPresent()) {
			final ServerPlayer player = optionalPlayer.get();
			return new SpongeNamelessPlayer(player);
		}
		return null;
	}

	@Override
	public NamelessPlayer player(final UUID uuid) {
		return spongeToNamelessPlayer(Sponge.server().player(uuid));
	}

	@Override
	public NamelessPlayer playerByUsername(final String username) {
		return spongeToNamelessPlayer(Sponge.server().player(username));
	}

	@Override
	public Collection<NamelessPlayer> onlinePlayers() {
		return Sponge.server().onlinePlayers().stream()
				.map(SpongeNamelessPlayer::new)
				.collect(Collectors.toUnmodifiableList());
	}
}
