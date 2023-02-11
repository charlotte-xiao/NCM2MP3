package service.command;

import service.command.common.BaseCommand;

import java.util.List;

/**
 * @author charlottexiao
 */
public class HelpCommand extends BaseCommand {

    @Override
    public void handle(List<String> params) {
        System.out.println("Usage: java -jar NCM2MP3.jar [command]");
        System.out.println("If don't add command, there will open NCM2MP3 GUI directly");
        System.out.println("[Command List]");
        System.out.println("-v,-view                      : open NCM View GUI(default command)");
        System.out.println("-c,--convert [path] ...       : convert NCM File in path to ./output directory");
        System.out.println("-h,-help                      : Help about any command");
        System.exit(0);
    }
}
