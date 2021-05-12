//*it is a sketch*//

import java.util.Iterator;

double a = 0;

int y = 26;

boolean up = true;

boolean cover = true;

StarShip ship = new StarShip();

Ship shit = new Ship();

ArrayList oilList = new ArrayList<Oil>();


void setup() 
{
    size(640,520);
    background(255);

}

void draw()
{
    if (a % 30 < 15){
        ship.moveDown();
    }else{
        ship.moveUp();
    }
    a = a + 0.3;   
    
    shit.mouse();
    Oil oils = ship.leak();
    oilList.add(oils);
    
    Iterator<Oil> iter = oilList.iterator();
    while(iter.hasNext()){
        Oil oil = iter.next();
        oil.update();
        if(oil.x >=600){
            oil.delete();
            iter.remove();
        }
        if(oil.x >= mouseX && oil.x <= mouseX +50 && oil.y >= mouseY && oil.y <= mouseY + 50 ){
            int score = oil.delete();
            shit.addScore(score);
            iter.remove();
        }
    }
    fill(255);
    noStroke();
    rect(5,490, 300, 20);
    fill(0);
    text("you liang " + shit.getScore(),10, 500);
}
class StarShip{
    int x = 0;
    int y = 0;
 
    int lastX = 0;
    int lastY = 0;
    
    void moveUp(){
        fill(255);
        rect(lastX,lastY,50,50);
        
        y = y - 26;
        
        fill(0);
        rect(x,y, 50,50); 
        
        lastX = x;
        lastY = y;
    }
    
    void moveDown(){
        fill(255);
        rect(lastX,lastY,50,50);
        
        
        y = y + 26;
        fill(0);
        rect(x,y, 50,50); 
        
        lastX = x;
        lastY = y;
    } 
    
    Oil leak(){
        Oil oil = new Oil();
        oil.x = x;
        oil.y = y;
        return oil;
    }
}

class Ship{
    int score = 0;
    
    void addScore(int addedScore){
        score = score + addedScore;
    }
    
    int getScore(){
        return score;
    }
    
    void mouse(){
        fill(255);
        rect(pmouseX,pmouseY, 50, 50);
        fill(0);
        rect(mouseX,mouseY, 50, 50);
    }
    
    int shoot(){
        Bullet shoot = new Bullet();
        shoot.x = mouseX;
        shoot.y = mouseY;
        return 10;
    }
}

class Oil{
    int x ;
    int y ;
    
    int lastX;
    int lastY;
    
    void update(){
        fill(255);
        ellipse(lastX,lastY, 10,10);
        
        x = x + 5;
        fill(200);
        ellipse(x,y, 10, 10);
        
        lastX = x;
        lastY = y;
    }
    
    int delete(){
        fill(255);
        ellipse(x,y, 10, 10);
        return 1;
    }
}


class Bullet{
    
    int x ;
    int y ;
    
    int lastX;
    int lastY;
    
    void update(){
        fill(255);
        ellipse(lastX,lastY, 10,10);
        
        x = x - 5;
        fill(200);
        ellipse(x,y, 10, 10);
        
        lastX = x;
        lastY = y;
    }
    
    int delete(){
        fill(255);
        ellipse(x,y, 10, 10);
        return 0;
    }
}
