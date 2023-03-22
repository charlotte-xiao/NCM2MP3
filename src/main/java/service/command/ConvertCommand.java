package service.command;

import executor.AsyncTaskExecutor;
import service.Converter;
import service.command.common.BaseCommand;
import utils.Utils;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * @author charlottexiao
 */
public class ConvertCommand extends BaseCommand {

    @Override
    public void handle(List<String> params) {
        //File outputPath = new File("." + File.separator + "output");
        File outputPath = new File("output");
        if(!outputPath.mkdir()){
            if(outputPath.isDirectory()){
                //文件夹已存在
                System.out.println("output文件夹已存在，将复用");
            }
            else{
                //存在output同名非文件夹
                Random random = new Random();
                outputPath = new File("output"+ random.nextInt());
            }
        }
        System.out.printf("Output dir is set to: %s%n", outputPath.getAbsolutePath());

        ArrayList<File> files = new ArrayList<>();
        params.forEach((param) -> Utils.listAllFiles(files, new File(param)));

        //中途有修改过outputPath的对象引用，要copy一份final才能传入lambda
        File finalOutputPath = outputPath;
        files.forEach(inputFile -> AsyncTaskExecutor.execute(() -> new Converter().ncm2Mp3(inputFile.getAbsolutePath(), finalOutputPath.getAbsolutePath())));
    }
}
