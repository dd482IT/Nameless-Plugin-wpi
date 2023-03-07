package com.namelessmc.plugin.sponge7;

import com.namelessmc.plugin.common.command.AbstractScheduledTask;
import com.namelessmc.plugin.common.command.AbstractScheduler;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.spongepowered.api.scheduler.Task;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

public class SpongeScheduler extends AbstractScheduler {

	private final SpongeNamelessPlugin plugin;

	SpongeScheduler(final SpongeNamelessPlugin plugin) {
		this.plugin = plugin;
	}

	@Override
	public void runAsync(Runnable runnable) {
		Task.builder()
				.execute(runnable)
				.async()
				.submit(this.plugin);
	}

	@Override
	public void runSync(Runnable runnable) {
		Task.builder()
				.execute(runnable)
				.submit(this.plugin);
	}

	@Override
	public SpongeScheduledTask runTimer(Runnable runnable, Duration interval) {
		final Task task = Task.builder()
				.execute(runnable)
				.delay(interval.toNanos(), TimeUnit.NANOSECONDS)
				.interval(interval.toNanos(), TimeUnit.NANOSECONDS)
				.submit(this.plugin);
		return new SpongeScheduledTask(task);
	}

	@Override
	public SpongeScheduledTask runDelayed(Runnable runnable, Duration delay) {
		final Task task = Task.builder()
				.execute(runnable)
				.delay(delay.toNanos(), TimeUnit.NANOSECONDS)
				.submit(this.plugin);
		return new SpongeScheduledTask(task);
	}

	public static class SpongeScheduledTask extends AbstractScheduledTask {

		private final Task task;

		private SpongeScheduledTask(final Task task) {
			this.task = task;
		}

		@Override
		public void cancel() {
			this.task.cancel();
		}

	}

}
