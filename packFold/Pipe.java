package packFold;
import general.*;
public class Pipe{
  protected Gen info = new Gen();
  public int spacingX = 200;
  public int spacingY = 150;
  public int speed = 5;
  public int width = 75;
  public int x = info.width;
  public int y = (int) Math.floor(Math.random()*(info.height-this.spacingY));
  public void update(){
    this.x-=this.speed;
  }
}
