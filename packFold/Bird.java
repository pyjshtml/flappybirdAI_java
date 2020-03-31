package packFold;
import general.*;
public class Bird{
  //Initialize and construct
  protected static Gen info = new Gen();
  public final int x = 100;
  public final  int r = 18;
  public double y = info.height / 2;
  public double[] weights = new double[3];
  public int velocity = 0;
  public Bird(){
    for(int i = 0; i < weights.length; i++){
      this.weights[i] = info.random(-1,1);
    }
  }
  public Bird(double[] baseWeights){
    for(int i = 0; i < weights.length; i++){
      this.weights[i] = baseWeights[i]+info.random(-0.15,0.15);
    }
  }
  //Funtionality
  public void up(){
    this.velocity -= 14;
  }
  public double guess(double[] inputs){
    double sum = 0;
    for(int i = 0; i < inputs.length; i++){
      sum += inputs[i]*this.weights[i];
    }
    return sum > 0 ? sum : 0;
  }
  public void update(Pipe pipe){
      if(this.velocity < -14){
        this.velocity = -14;
      } else if (this.velocity > 14){
        this.velocity = 14;
      }
      this.velocity += info.gravity;
      this.y += this.velocity;
      //Insert code here if you want upper and lower boundaries
      double[] input = {this.y, pipe.y-this.y, this.y-(pipe.y+pipe.spacingY)};
      if(guess(input)>1){
        up();
      }
  }
}
