package service;

import service.command.ConvertCommand;
import service.command.HelpCommand;
import service.command.ViewCommand;
import service.command.common.BaseCommand;
import service.command.common.CommandType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * @author charlottexiao
 */
public class Interpreter {

    private final Map<CommandType, BaseCommand> commandMap;

    public Interpreter() {
        this.commandMap = new ConcurrentHashMap<>();
        commandMap.put(CommandType.HELP, new HelpCommand());
        commandMap.put(CommandType.CONVERT, new ConvertCommand());
        commandMap.put(CommandType.VIEW, new ViewCommand());
    }


    /**
     * Use a simple CLI format
     *
     * @param args 参数
     */
    public void handleArgs(String[] args) {
        BaseCommand command = commandMap.get(CommandType.valueFor(commandFor(args)));
        command.handle(paramsFor(args));
    }

    private String commandFor(String[] args) {
        return (args.length == 0) ? "" : args[0];
    }

    private List<String> paramsFor(String[] args) {
        return (args.length == 0) ?
                new ArrayList<>() :
                Arrays.stream(args).skip(0).collect(Collectors.toList());
    }

}
