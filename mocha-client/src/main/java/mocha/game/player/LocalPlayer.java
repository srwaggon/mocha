package mocha.game.player;

public class LocalPlayer implements Player {

  private int id;

  public LocalPlayer(int id) {
    this.id = id;
  }

  @Override
  public Integer getId() {
    return id;
  }

  @Override
  public void setId(Integer id) {
    this.id = id;
  }
}
