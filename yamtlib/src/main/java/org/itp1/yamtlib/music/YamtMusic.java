package org.itp1.yamtlib.music;

import lombok.Getter;
import org.itp1.yamtlib.errors.YamtException;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.AudioHeader;
import org.jaudiotagger.tag.FieldDataInvalidException;
import org.jaudiotagger.tag.Tag;

import java.io.File;


@Getter
public class YamtMusic {

    private AudioFile audioFile;
    private AudioHeader audioHeader;
    private Tag tags;
    private String format;
    private File file;

    /**
     * Used to create YamtMusic Objects.
     * @param url absolute path to file as String
     * @throws Exception
     */
    public YamtMusic(String url) throws Exception {
        this(makeFile(url));
    }

    /**
     * Used to create YamtMusic Objects.
     * @param f a File Object
     * @throws YamtException.MusicException
     */
    public YamtMusic(File f) throws YamtException.MusicException {
        try {
            if(f != null) {
                this.file = f;
                this.audioFile = makeAudioFile(this.file);
                this.audioHeader = this.audioFile.getAudioHeader();
                this.tags = this.audioFile.getTag();
                this.format = this.audioHeader.getFormat();
            } else {
                throw new YamtException.MusicException("File is null");
            }
        } catch(Exception e) {
            throw new YamtException.MusicException("Construction failed", e);
        }
    }

    /**
     * reads Metadata according to the given key
     * @param wantedKey has to be Enum WantedKey, where jaudioTagger FieldKey is stored
     * @return String - what is written in the file
     * @throws YamtException.MusicException
     */
    public String getTag(WantedKey wantedKey) throws YamtException.MusicException {
        try {
            return this.getTags().getFirst(wantedKey.getFk());
        } catch(Exception e) {
            throw new YamtException.MusicException(e);
        }
    }

    /**
     * sets Metadata in the tags object
     * @param wantedKey has to be Enum WantedKey, where jaudioTagger FieldKey is stored
     * @param newValue  String to be set
     * @throws YamtException.MusicException
     */
    public void setTag(WantedKey wantedKey, String newValue) throws YamtException.MusicException {
        try {
            this.getTags().setField(wantedKey.getFk(), newValue);
        } catch (FieldDataInvalidException e) {
            throw new YamtException.MusicException("Setting tag failed", e);
        }
    }

    /**
     * used to make File from path
     * @param url parameter is the absolute path as String
     * @return null or File
     * @throws YamtException.MusicException
     */
    private static File makeFile(String url) throws YamtException.MusicException {
        try {
            return new File(url);
        } catch(Exception e) {
            throw new YamtException.MusicException(e);
        }
    }

    /**
     * used to make AudioFile from File
     * @param f File Object
     * @return AudioFile
     * @throws YamtException.MusicException
     */
    private AudioFile makeAudioFile(File f) throws YamtException.MusicException {
        try {
            return AudioFileIO.read(f);
        } catch(Exception e) {
            throw new YamtException.MusicException(e);
        }
    }

}
