package general;
public class Gen{
  public int height = 800;
  public int width = 800;
  public int gravity = 1;
  public static double random(double mn, double mx){
    return Math.random()*(mx-mn)+mn;
  }
}
