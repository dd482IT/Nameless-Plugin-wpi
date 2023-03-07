package com.namelessmc.plugin.common.event;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.UUID;

public class NamelessPlayerQuitEvent extends NamelessOfflinePlayerEvent {

	public NamelessPlayerQuitEvent(final UUID uuid) {
		super(uuid);
	}

}
