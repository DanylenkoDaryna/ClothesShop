package ua.nure.danylenko.epam.web.command;

import org.apache.log4j.Logger;
import ua.nure.danylenko.epam.web.command.admin.AddNewProductCommand;
import ua.nure.danylenko.epam.web.command.admin.UpdateCatalogueCommand;
import ua.nure.danylenko.epam.web.command.admin.UpdatingUPOCommand;
import ua.nure.danylenko.epam.web.command.client.DeleteAccountCommand;
import ua.nure.danylenko.epam.web.command.client.EditAccountCommand;
import ua.nure.danylenko.epam.web.command.client.GetMyCabinetCommand;

import java.util.Map;
import java.util.TreeMap;

public class CommandContainer {

    private static final Logger WEB_LOG = Logger.getLogger("servlets");

    private static Map<String, Command> commands = new TreeMap<>();

    static {
        // common commands
        commands.put("login", new LoginCommand());
        commands.put("logout", new LogoutCommand());
        commands.put("register", new RegisterCommand());
      //  commands.put("viewSettings", new ViewSettingsCommand());
        commands.put("noCommand", new NoCommand());
        commands.put("listProducts", new ProductsCommand());
        commands.put("ItemProducts", new ItemProductsCommand());
        commands.put("SimpleFilter", new SimpleFilterCommand());

         //client commands
        commands.put("getMyCabinet", new GetMyCabinetCommand());
        commands.put("deleteAccount", new DeleteAccountCommand());
        commands.put("editAccount", new EditAccountCommand());
        commands.put("ordering", new OrderingCommand());

         //admin commands
        commands.put("UpdatingUPOCommand", new UpdatingUPOCommand());
        commands.put("updateCatalogue", new UpdateCatalogueCommand());
        commands.put("addNewProduct", new AddNewProductCommand());

        WEB_LOG.info("Command container was successfully initialized");
        WEB_LOG.info("Number of commands --> " + commands.size());
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
            WEB_LOG.trace("Command not found, name --> " + commandName);
            return commands.get("noCommand");
        }

        return commands.get(commandName);
    }
}
