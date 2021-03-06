package core;

import mime.NCM;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.flac.metadatablock.MetadataBlockDataPicture;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.Tag;
import org.jaudiotagger.tag.images.Artwork;
import org.jaudiotagger.tag.images.ArtworkFactory;
import utils.Utils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;

/**
 * @author charlottexiao
 */
public class Combine {

    private NCM ncm;
    public Combine(NCM ncm){
        this.ncm=ncm;
    }

    /**
     * 功能:将NCM中各个信息整合到一起,转换成对应音乐格式
     * @return 合成失败与否
     */
    public boolean combineFile(){
        try {
            AudioFile audioFile = AudioFileIO.read(new File(ncm.getOutFile()));
            Tag tag = audioFile.getTag();
            tag.setField(FieldKey.ALBUM, ncm.getMata().album);
            tag.setField(FieldKey.TITLE, ncm.getMata().musicName);
            tag.setField(FieldKey.ARTIST, ncm.getMata().artist[0]);
            BufferedImage image = ImageIO.read(new ByteArrayInputStream(ncm.getImage()));
            if(image!=null){
                MetadataBlockDataPicture coverArt = new MetadataBlockDataPicture(ncm.getImage(), 0, Utils.albumImageMimeType(ncm.getImage()), "", image.getWidth(), image.getHeight(), image.getColorModel().hasAlpha() ? 32 : 24, 0);
                Artwork artwork = ArtworkFactory.createArtworkFromMetadataBlockDataPicture(coverArt);
                tag.setField(tag.createField(artwork));
            }
            AudioFileIO.write(audioFile);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
