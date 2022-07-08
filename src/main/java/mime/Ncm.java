package mime;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author charlottexiao
 */
@Data
@NoArgsConstructor
public class Ncm {

    /**
     * NCM的文件路径
     */
    private String ncmFile;

    /**
     * 转换后文件路径
     */
    private String outFile;

    /**
     * 头信息
     */
    private Mata mata;

    /**
     * 封面信息
     */
    private byte[] image;


}
