package com.namelessmc.plugin.common.event;

import com.namelessmc.plugin.common.audiences.NamelessPlayer;
import org.checkerframework.checker.nullness.qual.NonNull;

public class NamelessPlayerEvent extends NamelessOfflinePlayerEvent {

	private final NamelessPlayer player;

	public NamelessPlayerEvent(final NamelessPlayer player) {
		super(player.uuid());
		this.player = player;
	}

	public NamelessPlayer player() {
		return this.player;
	}

}
