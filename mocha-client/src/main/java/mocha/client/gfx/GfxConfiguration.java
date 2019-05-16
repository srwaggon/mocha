package mocha.client.gfx;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javafx.scene.Group;

@Configuration
public class GfxConfiguration {

  @Bean
  @Qualifier("root")
  public Group root(
      @Qualifier("menuSelection") Group menuSelection,
      MochaPane mochaPane
  ) {
    Group rootGroup = new Group();
    rootGroup.getChildren().add(menuSelection);
    return rootGroup;
  }

}
