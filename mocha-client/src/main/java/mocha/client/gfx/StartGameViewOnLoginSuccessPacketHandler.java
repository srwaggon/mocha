package mocha.client.gfx;

import com.google.common.eventbus.Subscribe;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javafx.application.Platform;
import javafx.scene.Group;
import mocha.net.LoginSuccessPacket;
import mocha.net.packet.PacketHandler;

@Component
public class StartGameViewOnLoginSuccessPacketHandler implements PacketHandler<LoginSuccessPacket> {

  private Group root;
  private MochaPane mochaPane;
  private RenderLoop renderLoop;

  public StartGameViewOnLoginSuccessPacketHandler(
      @Qualifier("root") Group root,
      MochaPane mochaPane,
      RenderLoop renderLoop
  ) {
    this.root = root;
    this.mochaPane = mochaPane;
    this.renderLoop = renderLoop;
  }

  @Subscribe
  @Override
  public void handle(LoginSuccessPacket loginSuccessPacket) {
    Platform.runLater(() -> {
      root.getChildren().clear();
      root.getChildren().add(mochaPane);

      renderLoop.start();
    });
  }
}
