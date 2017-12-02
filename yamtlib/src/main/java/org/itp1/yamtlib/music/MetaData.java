package org.itp1.yamtlib.music;

import lombok.Getter;
import lombok.Setter;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.AudioHeader;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.Tag;
import org.junit.Assert;

import java.io.File;
import java.util.ArrayList;

@Setter
@Getter
public class MetaData {

    private static ArrayList<MetaData> allInstances = new ArrayList<MetaData>();

    private AudioFile audioFile;
    private AudioHeader audioHeader;
    private Tag tags;
    private String format;
    private File file;

    public MetaData(String url) {
        this.file = makeFile(url);
        if(this.file != null) this.audioFile = makeAudioFile(this.file);
        if(this.audioFile != null) this.audioHeader = this.audioFile.getAudioHeader();
        if(this.audioFile != null) this.tags = this.audioFile.getTag();
        if(this.audioHeader != null) this.format = this.audioHeader.getFormat();
        allInstances.add(this);
    }

    public MetaData(File f) {
        this.file = f;
        if(this.file != null) this.audioFile = makeAudioFile(this.file);
        if(this.audioFile != null) this.audioHeader = this.audioFile.getAudioHeader();
        if(this.audioFile != null) this.tags = this.audioFile.getTag();
        if(this.audioHeader != null) this.format = this.audioHeader.getFormat();
        allInstances.add(this);
    }

    // creates fileObject return null if it doesnt exist or on failure
    private File makeFile(String url) {
        try {
            File f = new File(url);
            if(f.exists()) return f;
        } catch(Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //makes AudioFile returns null if it doesnt exist or on failure
    private AudioFile makeAudioFile(File f) {
        try {
            return AudioFileIO.read(f);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
