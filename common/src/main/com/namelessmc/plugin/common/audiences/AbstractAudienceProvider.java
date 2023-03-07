package com.namelessmc.plugin.common.audiences;

import net.kyori.adventure.audience.Audience;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.Collection;
import java.util.UUID;

public abstract class AbstractAudienceProvider {

	public abstract NamelessConsole console();

	public abstract Audience broadcast();

	public abstract NamelessPlayer player(final UUID uuid);

	public abstract NamelessPlayer playerByUsername(final String username);

	public abstract Collection<NamelessPlayer> onlinePlayers();

}
