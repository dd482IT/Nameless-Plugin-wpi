package com.namelessmc.plugin.common.command;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.time.Duration;

public abstract class AbstractScheduler {

	public abstract void runAsync(final Runnable runnable);

	public abstract void runSync(final Runnable runnable);

	public abstract AbstractScheduledTask runTimer(final Runnable runnable, final Duration interval);

	public abstract AbstractScheduledTask runDelayed(final Runnable runnable, final Duration delay);

}
