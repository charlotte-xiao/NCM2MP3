import view.View;

import java.io.File;
import java.util.Vector;

import core.Core;

/**
 * @author charlottexiao
 */
public class main {
    public static void main(String[] args) {
        if (args.length == 0) {
            // no command line args, start GUI
            new View();
        } else {
            // Use a simple CLI format
            // java -jar NCM2MP3 [filename1] [filename2] ... [-o output directory]
            // or use -h or --help for help message

            Vector<String> filenames = new Vector<String>();
            String outputDirStr = "./";

            for (int i = 0; i < args.length; i++) {
                String arg = args[i];
                if (arg.startsWith("-")) {
                    // is a flag
                    if (arg.equals("-h") || arg.equals("--help")) {
                        System.out.println(
                                "Usage: java -jar NCM2MP3.jar [filename1] [filename2] ... [-o output directory]");
                        System.out.println("Output directory defaults to the current working directory");
                        System.exit(0);
                    } else if (arg.equals("-o")) {
                        // next arg should be the output dir
                        if (i + 1 < args.length) {
                            // have next arg
                            outputDirStr = args[i + 1];
                            i += 1;
                        }
                    }
                } else {
                    // should be a file name
                    // check if file exists, and if extension is ncm
                    File f = new File(args[i]);
                    String[] splits = args[i].split("\\.");
                    String extension = splits[splits.length - 1];
                    if (f.isFile() && extension.toLowerCase().equals("ncm")) {
                        // is a valid ncm file, add to list
                        filenames.add(args[i]);
                    }
                }
            }
            // create output dir if not exist
            File outputDir = new File(outputDirStr);
            if (outputDir.isFile()) {
                System.out.println("Output dir is occupied by a file.");
                System.exit(1);
            } else {
                outputDir.mkdirs();
            }

            // now convert
            Core core = new Core();
            System.out.println(String.format("Output dir is set to: %s", outputDir.getAbsolutePath()));
            for (int i = 0; i < filenames.size(); i++) {
                String filename = filenames.elementAt(i);
                File inputFile = new File(filename);
                System.out.println(String.format("Converting %s", filename));

                boolean success = core.ncm2Mp3(inputFile.getAbsolutePath(), outputDir.getAbsolutePath());
                if (success) {
                    System.out.println("Done");
                } else {
                    System.out.println("Failed");
                }
            }
        }
    }
}
