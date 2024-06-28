package edu.hitsz.threadprocess;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;

public class MusicThread extends Thread {

    // 音频文件名
    private String filename;
    private AudioFormat audioFormat;
    private byte[] samples;
    private int repeatCount;
    public boolean running = false;
    private boolean paused = false;
    private final Object pauseLock = new Object();
    private InputStream stream;

    public MusicThread(String filename, int repeatCount) {
        // 初始化filename
        this.filename = filename;
        this.repeatCount = repeatCount;
        this.running = true;
        reverseMusic();
        this.stream = new ByteArrayInputStream(this.samples);
    }

    public void reverseMusic() {
        try {
            AudioInputStream stream = AudioSystem.getAudioInputStream(new File(filename));
            audioFormat = stream.getFormat();
            samples = getSamples(stream);
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public byte[] getSamples(AudioInputStream stream) {
        int size = (int) (stream.getFrameLength() * audioFormat.getFrameSize());
        byte[] samples = new byte[size];
        DataInputStream dataInputStream = new DataInputStream(stream);
        try {
            dataInputStream.readFully(samples);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return samples;
    }

    public void play(InputStream source) {
        int size = (int) (audioFormat.getFrameSize() * audioFormat.getSampleRate());
        byte[] buffer = new byte[size];
        SourceDataLine dataLine = null;
        DataLine.Info info = new DataLine.Info(SourceDataLine.class, audioFormat);
        try {
            dataLine = (SourceDataLine) AudioSystem.getLine(info);
            dataLine.open(audioFormat, size);
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }
        dataLine.start();
        int i = 0;
        while (running && (i < repeatCount || repeatCount == 0)) {
            try {
                int numBytesRead = 0;
                while (running && numBytesRead != -1) {
                    synchronized (pauseLock) {
                        if (paused) {
                            dataLine.stop();
                            try {
                                pauseLock.wait();
                            } catch (InterruptedException e) {
                                Thread.currentThread().interrupt();
                            }
                            dataLine.start();
                        }
                    }
                    numBytesRead = source.read(buffer, 0, buffer.length);
                    if (Thread.interrupted()|| !running) {
                        break;
                    }
                    if (numBytesRead != -1) {
                        dataLine.write(buffer, 0, numBytesRead);
                    }
                }
                source.reset();
                i++;
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

        dataLine.drain();
        dataLine.close();
    }

    @Override
    public void run() {
        play(stream);
    }

    public void stopmusic() {
        this.running = false;
        this.interrupt();
        try {
            if (this.stream != null) {
                this.stream.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void pauseMusic() {
        synchronized (pauseLock) {
            paused = true;
        }
    }

    public void resumeMusic() {
        synchronized (pauseLock) {
            paused = false;
            pauseLock.notifyAll();
        }
    }
}
