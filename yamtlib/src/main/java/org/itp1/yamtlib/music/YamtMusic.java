package org.itp1.yamtlib.music;

import lombok.Getter;
import lombok.Setter;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.AudioHeader;
import org.jaudiotagger.tag.FieldDataInvalidException;
import org.jaudiotagger.tag.Tag;

import java.io.File;
import java.util.ArrayList;


@Getter
public class YamtMusic {

    private AudioFile audioFile;
    private AudioHeader audioHeader;
    private Tag tags;
    private String format;
    private File file;

    public YamtMusic(String url) {
        this(makeFile(url));
    }

    public YamtMusic(File f) {
        this.file = f;
        if(this.file != null) this.audioFile = makeAudioFile(this.file);
        if(this.audioFile != null) this.audioHeader = this.audioFile.getAudioHeader();
        if(this.audioFile != null) this.tags = this.audioFile.getTag();
        if(this.audioHeader != null) this.format = this.audioHeader.getFormat();
    }

    public String getTag(WantedKeys wantedKey) {
        return this.getTags().getFirst(wantedKey.getFk());
    }

    public void setTag(WantedKeys wantedKey, String newValue) {
        try {
            this.getTags().setField(wantedKey.getFk(), newValue);
        } catch (FieldDataInvalidException e) {
            e.printStackTrace();
        }
    }

    // creates fileObject return null if it doesnt exist or on failure
    private static File makeFile(String url) {
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
