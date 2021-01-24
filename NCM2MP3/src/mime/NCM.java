package mime;
/**
 * @author charlottexiao
 */
public class NCM {
    //NCM的文件路径
    private String ncmFile;
    //转换后文件路径
    private String outFile;
    //头信息
    private MATA mata;
    //封面信息
    private byte[] image;

    public NCM() {
    }

    public String getNcmFile() {
        return ncmFile;
    }

    public void setNcmFile(String ncmFile) {
        this.ncmFile = ncmFile;
    }

    public String getOutFile() {
        return outFile;
    }

    public void setOutFile(String outFile) {
        this.outFile = outFile;
    }

    public MATA getMata() {
        return mata;
    }

    public void setMata(MATA mata) {
        this.mata = mata;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}
