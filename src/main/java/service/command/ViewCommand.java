package service.command;

import service.command.common.BaseCommand;
import view.View;

import java.util.List;

/**
 * @author charlottexiao
 */
public class ViewCommand extends BaseCommand {

    @Override
    public void handle(List<String> params) {
        new View();
    }
}
