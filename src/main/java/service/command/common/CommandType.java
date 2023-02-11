package service.command.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * @author charlottexiao
 */
public enum CommandType {

    HELP("-h", "--help"),

    CONVERT("-c", "--convert"),

    VIEW("-v", "--view");

    private String name;

    private String alias;

    CommandType(String name, String alias) {
        this.name = name;
        this.alias = alias;
    }

    public static CommandType valueFor(String command) {
        return Arrays.stream(CommandType.values())
                .filter((type) -> (type.name.equals(command) || type.alias.equals(command))).findFirst().orElse(VIEW);
    }


}
