package service.command.common;

import java.util.List;

/**
 * @author charlottexiao
 */
public abstract class BaseCommand {

    public abstract void handle(List<String> params);

}
