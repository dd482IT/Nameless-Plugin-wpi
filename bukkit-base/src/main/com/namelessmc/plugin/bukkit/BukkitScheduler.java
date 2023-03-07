package com.namelessmc.plugin.bukkit;

import com.namelessmc.plugin.common.command.AbstractScheduledTask;
import com.namelessmc.plugin.common.command.AbstractScheduler;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitTask;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.time.Duration;

public class BukkitScheduler extends AbstractScheduler {

	private final BukkitNamelessPlugin plugin;

	BukkitScheduler(final BukkitNamelessPlugin plugin) {
		this.plugin = plugin;
	}

	@Override
	public void runAsync(final Runnable runnable) {
		Bukkit.getScheduler().runTaskAsynchronously(this.plugin, runnable);
	}

	@Override
	public void runSync(final Runnable runnable) {
		Bukkit.getScheduler().runTask(this.plugin, runnable);
	}

	@Override
	public BukkitScheduledTask runTimer(final Runnable runnable,
																 final Duration interval) {
		long ticks = interval.toMillis() / 50;
		final BukkitTask task = Bukkit.getScheduler().runTaskTimer(this.plugin, runnable, ticks, ticks);
		return new BukkitScheduledTask(task);
	}

	@Override
	public BukkitScheduledTask runDelayed(final Runnable runnable,
																   final Duration delay) {
		long ticks = delay.toMillis() / 50;
		final BukkitTask task = Bukkit.getScheduler().runTaskLater(this.plugin, runnable, ticks);
		return new BukkitScheduledTask(task);
	}

	public static class BukkitScheduledTask extends AbstractScheduledTask {

		private final BukkitTask task;

		private BukkitScheduledTask(final BukkitTask task) {
			this.task = task;
		}

		@Override
		public void cancel() {
			this.task.cancel();
		}

	}

}
