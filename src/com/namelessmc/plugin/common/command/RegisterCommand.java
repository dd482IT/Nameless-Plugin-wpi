package com.namelessmc.plugin.common.command;

import com.namelessmc.java_api.ApiError;
import com.namelessmc.java_api.NamelessAPI;
import com.namelessmc.java_api.NamelessException;
import com.namelessmc.java_api.exception.CannotSendEmailException;
import com.namelessmc.java_api.exception.InvalidUsernameException;
import com.namelessmc.java_api.exception.UsernameAlreadyExistsException;
import com.namelessmc.java_api.exception.UuidAlreadyExistsException;
import com.namelessmc.plugin.common.CommonObjectsProvider;
import com.namelessmc.plugin.common.LanguageHandler.Term;
import com.namelessmc.plugin.common.Permission;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class RegisterCommand extends CommonCommand {

	public RegisterCommand(final @NotNull CommonObjectsProvider provider) {
		super(provider,
				"register",
				Term.COMMAND_REGISTER_USAGE,
				Permission.COMMAND_REGISTER);
	}

	@Override
	public void execute(final CommandSender sender, final String[] args) {
		if (args.length != 1) {
			sender.sendMessage(this.getUsage());
			return;
		}

		if (!sender.isPlayer()) {
			sender.sendMessage(getLanguage().getComponent(Term.COMMAND_NOTAPLAYER));
			return;
		}

		this.getScheduler().runAsync(() -> {
			final Optional<NamelessAPI> optApi = this.getApi();
			if (!optApi.isPresent()) {
				sender.sendMessage(this.getLanguage().getComponent(Term.COMMAND_REGISTER_OUTPUT_FAIL_GENERIC));
				return;
			}
			final NamelessAPI api = optApi.get();

			try {
				final Optional<String> link =
						this.getApiProvider().useUsernames()
								? api.registerUser(sender.getName(), args[0])
								: api.registerUser(sender.getName(), args[0], sender.getUniqueId());
				if (link.isPresent()) {
					sender.sendMessage(getLanguage().getComponent(Term.COMMAND_REGISTER_OUTPUT_SUCCESS_LINK, "url", link.get()));
				} else {
					sender.sendMessage(getLanguage().getComponent(Term.COMMAND_REGISTER_OUTPUT_SUCCESS_EMAIL));
				}
			} catch (final ApiError e) {
				// TODO all these API errors should be converted to thrown exceptions in the java api
				if (e.getError() == ApiError.EMAIL_ALREADY_EXISTS) {
					sender.sendMessage(getLanguage().getComponent(Term.COMMAND_REGISTER_OUTPUT_FAIL_EMAILUSED));
				} else if (e.getError() == ApiError.INVALID_EMAIL_ADDRESS) {
					sender.sendMessage(getLanguage().getComponent(Term.COMMAND_REGISTER_OUTPUT_FAIL_EMAILINVALID));
				} else {
					sender.sendMessage(getLanguage().getComponent(Term.COMMAND_REGISTER_OUTPUT_FAIL_GENERIC));
					getExceptionLogger().logException(e);
				}
			} catch (final NamelessException e) {
				sender.sendMessage(getLanguage().getComponent(Term.COMMAND_REGISTER_OUTPUT_FAIL_GENERIC));
				getExceptionLogger().logException(e);
			} catch (final InvalidUsernameException e) {
				sender.sendMessage(getLanguage().getComponent(Term.COMMAND_REGISTER_OUTPUT_FAIL_USERNAMEINVALID));
			} catch (final CannotSendEmailException e) {
				sender.sendMessage(getLanguage().getComponent(Term.COMMAND_REGISTER_OUTPUT_FAIL_CANNOTSENDEMAIL));
			} catch (final UuidAlreadyExistsException | UsernameAlreadyExistsException e) {
				sender.sendMessage(getLanguage().getComponent(Term.COMMAND_REGISTER_OUTPUT_FAIL_ALREADYEXISTS));
			}
		});
	}

}
