package mjaroslav.bots.core.amadeus.lib;

import java.io.File;
import java.io.FilenameFilter;
import java.nio.file.Path;
import mjaroslav.bots.core.amadeus.AmadeusCore;
import mjaroslav.bots.core.amadeus.utils.AmadeusUtils;

public class FileHelper {
    public static final String EXT_DATABASE = "db";
    public static final String EXT_LANG = "lang";
    public static final String EXT_JSON = "json";
    public static final String EXT_ROLE = "role";

    public static final FilenameFilter LANGEXTFILTER = AmadeusUtils.getFilenameExtFilter(EXT_LANG);
    public static final FilenameFilter ROLEEXTFILTER = AmadeusUtils.getFilenameExtFilter(EXT_ROLE);

    public static File fileBotToken() {
        return new File("bot.token");
    }

    public static Path folderBot(AmadeusCore core) {
        return core.info.getFolder().toPath();
    }

    public static Path folderDatabases(AmadeusCore core) {
        return folderBot(core).resolve("databases");
    }

    public static Path folderPermissions(AmadeusCore core) {
        return folderBot(core).resolve("permissions");
    }

    public static Path folderLanguages(AmadeusCore core) {
        return folderBot(core).resolve("languages");
    }

    public static Path folderConfigurations(AmadeusCore core) {
        return folderBot(core).resolve("configurations");
    }

    public static File fileDatabase(AmadeusCore core, String name) {
        return folderDatabases(core).resolve(name + ".db").toFile();
    }

    public static File filePermissionsDatabase(AmadeusCore core) {
        return folderDatabases(core).resolve("permissions.db").toFile();
    }

    public static File filePermissionsRole(AmadeusCore core, long guildId) {
        return folderPermissions(core).resolve(guildId + "." + EXT_ROLE).toFile();
    }

    public static File filePermissionsRolePrivate(AmadeusCore core) {
        return folderPermissions(core).resolve("private_messages." + EXT_ROLE).toFile();
    }

    public static File fileLanguagesDatabase(AmadeusCore core) {
        return folderDatabases(core).resolve("languages." + EXT_DATABASE).toFile();
    }

    public static File fileLanguageCommands(AmadeusCore core, String name) {
        return folderLanguages(core).resolve(name + "." + EXT_JSON).toFile();
    }

    public static File fileLanguage(AmadeusCore core, String name) {
        return folderLanguages(core).resolve(name + "." + EXT_LANG).toFile();
    }
}
