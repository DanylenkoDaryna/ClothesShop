package ua.nure.danylenko.epam.web.command;

import org.apache.log4j.Logger;

import java.util.Map;
import java.util.TreeMap;

public class CommandContainer {

    private static final Logger LOG = Logger.getLogger("servlets");

    private static Map<String, Command> commands = new TreeMap<String, Command>();

    static {
        // common commands
        commands.put("login", new LoginCommand());
        commands.put("logout", new LogoutCommand());
      //  commands.put("viewSettings", new ViewSettingsCommand());
        commands.put("noCommand", new NoCommand());
        commands.put("listProducts", new ProductsCommand());
        commands.put("ItemProducts", new ItemProductsCommand());

        // client commands
      //  commands.put("listMenu", new ListMenuCommand());

        // admin commands
      //  commands.put("listOrders", new ListOrdersCommand());

        LOG.debug("Command container was successfully initialized");
        LOG.debug("Number of commands --> " + commands.size());
    }

    /**
     * Returns command object with the given name.
     *
     * @param commandName
     *            Name of the command.
     * @return Command object.
     */
    public static Command get(String commandName) {
        if (commandName == null || !commands.containsKey(commandName)) {
            //LOG.trace("Command not found, name --> " + commandName);
            return commands.get("noCommand");
        }

        return commands.get(commandName);
    }
}
