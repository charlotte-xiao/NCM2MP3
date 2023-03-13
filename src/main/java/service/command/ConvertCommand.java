package service.command;

import executor.AsyncTaskExecutor;
import service.Converter;
import service.command.common.BaseCommand;
import utils.Utils;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author charlottexiao
 */
public class ConvertCommand extends BaseCommand {

    @Override
    public void handle(List<String> params) {
        File outputPath = new File("." + File.separator + "output");
        System.out.printf("Output dir is set to: %s%n", outputPath.getAbsolutePath());

        ArrayList<File> files = new ArrayList<>();
        params.forEach((param) -> Utils.listAllFiles(files, new File(param)));

        files.forEach(inputFile -> AsyncTaskExecutor.execute(() -> new Converter().ncm2Mp3(inputFile.getAbsolutePath(), outputPath.getAbsolutePath())));
    }
}
