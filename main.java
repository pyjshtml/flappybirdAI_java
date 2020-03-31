import packFold.*;
import general.*;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
// import io.github.cdimascio.dotenv.Dotenv;
class Main{
  public static final int POP_LIMIT = 10;
  public static Gen info = new Gen();
  public static Pipe[] pipes = {new Pipe()};
  public static Bird[] birds = new Bird[100];
  public static int generation = 1;
  public static int score = 0;
  public static void clearScreen(){
    try{
      new ProcessBuilder("cmd","/c","cls").inheritIO().start().waitFor();
    } catch(Exception e){
      System.out.println(e);
    }
  }
  public static void nextGeneration (Bird[] bestBirds){
    double[] baseWeights = {0,0,0};
    for(int i = 0; i < bestBirds.length; i++){
      for(int j = 0; j < bestBirds[i].weights.length; j++){
        baseWeights[j] += bestBirds[i].weights[j] / bestBirds.length;
      }
    }
    System.out.println("Score: " + String.valueOf(score));
    System.out.print("Generation ");
    System.out.print(generation++);
    System.out.println(":");
    for(double weight : baseWeights){
      System.out.println(weight);
    }
    pipes = new Pipe[]{new Pipe()};
    for(int i = 0; i < POP_LIMIT-bestBirds.length; i++){
      birds = add(birds,new Bird(baseWeights));
    }
    score = 0;
    runGeneration();
    return;
  }
  public static Bird[] remove(Bird[] birdL, int index){
    Bird[] newBirds = new Bird[birdL.length-1];
    for(int i = 0; i < birdL.length; i++){
      if(i == index) continue;
      newBirds[i > index ? i-1 : i] = birdL[i];
    }
    return newBirds;
  }
  public static Bird[] add(Bird[] list, Bird newB){
    Bird[] newBirds = new Bird[list.length+1];
    for(int i = 0; i < list.length; i++){
      newBirds[i] = list[i];
    } newBirds[list.length] = newB;
    return newBirds;
  }
  public static Pipe[] remove(Pipe[] pipeL, int index){
    Pipe[] newPipes = new Pipe[pipeL.length-1];
    for(int i = 0; i < pipeL.length; i++){
      if(i == index) continue;
      newPipes[i > index ? i-1 : i] = pipeL[i];
    }
    return newPipes;
  }
  public static Pipe[] add(Pipe[] list, Pipe newP){
    Pipe[] newPipes = new Pipe[list.length+1];
    for(int i = 0; i < list.length; i++){
      newPipes[i] = list[i];
    } newPipes[list.length] = newP;
    return newPipes;
  }
  public static void runGeneration(){
    for(int i = 0; i < pipes.length; i++){
      pipes[i].update();
    }
    for(int i = 0; i < birds.length; i++){
      //Get closest pipe
      int c = 0;
      double cD = Double.POSITIVE_INFINITY;
      for(int j = 0; j < pipes.length; j++){
        if(pipes[j].x - birds[i].x < cD && pipes[j].x + pipes[j].width > birds[i].x + birds[i].r){
          cD = pipes[j].x - birds[i].x;
          c = j;
        }
      }
      if(pipes[pipes.length-1].x+pipes[pipes.length-1].width <= info.width-pipes[pipes.length-1].spacingX){
        pipes = add(pipes,new Pipe());
      }
      if(pipes[0].x + pipes[0].width < 0){
        pipes = remove(pipes,0);
        score++;
      }
      Pipe close = pipes[c];
      birds[i].update(close);
      //Kill birds
      if( (birds[i].x + birds[i].r >= close.x && (birds[i].y + birds[i].r >= close.y + close.spacingY || birds[i].y - birds[i].r <= close.y)) || birds[i].y < 0 || birds[i].y > info.height){
        birds = remove(birds,i);
      }
    }
    //Loop and update generation
    try{
      TimeUnit.MILLISECONDS.sleep(50);
    } catch (InterruptedException ie){System.out.println("ERROR");}
    if(birds.length <= 2){
      nextGeneration(birds);
      return;
    } else {
      runGeneration();
      return;
    }
  }
  public static void main(String[] args) {
    for(int i = 0; i < birds.length; i++){
      birds[i] = new Bird();
    }
    runGeneration();
  }
}
