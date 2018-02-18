package mocha.sfx;

import java.net.URL;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class Sound {

  private MediaPlayer mediaPlayer;

  Sound(String fileName) {
    URL resource = getClass().getClassLoader().getResource("sfx/" + fileName);
    assert resource != null;
    Media media = new Media(resource.toString());
    this.mediaPlayer = new MediaPlayer(media);
  }

  public void play() {
    mediaPlayer.setOnEndOfMedia(mediaPlayer::stop);
    mediaPlayer.play();
  }
}
