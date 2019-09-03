package ru.spaces.artshi2009;

import javax.sound.sampled.*;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;

class PlayerSound {
    void play() {
        String fileName = "Warning.wav";
        URL path = this.getClass().getClassLoader().getResource(fileName);

        try {
            AudioInputStream audio = AudioSystem.getAudioInputStream(Objects.requireNonNull(path));
            Clip clip = AudioSystem.getClip();
            clip.open(audio);
            clip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }

    }
}
