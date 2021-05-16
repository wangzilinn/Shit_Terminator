//*it is a sketch*//

import java.util.Iterator;

double a = 0;
int b = 0;

int y = 26;

boolean up = true;

boolean cover = true;

EnemyShip ship = new EnemyShip();

Ship shit = new Ship();

ArrayList oilList = new ArrayList<Oil>();

ArrayList bulletList = new ArrayList<Bullet>();

ParticleSystem ps;


void setup() 
{
    size(1500,800);
    ps = new ParticleSystem(new PVector(width/2, 50));
    

}

void draw()
{
    background(255);
    
    if (a % 30 < 15){
        ship.moveDown();
    }else{
        ship.moveUp();
    }
    a = a + 0.3;   
    
    shit.mouse();
    
    if(shit.score <= 0){
        fill(255);
        rect(width/2-30, height/2-30, 230, 100);
        
       fill(0);
       textSize(60);
       text("you lose", width/2-30, height/2-30);
    }
    
    if(b % 3 == 0){
        Oil oils = ship.leak();
        if (oils != null){
            //if ship is destoried, then the oil is null
            oilList.add(oils);
        }
    }
    b++;
    
    
    Iterator<Oil> iter = oilList.iterator();
    while(iter.hasNext()){
        Oil oil = iter.next();
        oil.update();
        
        if(oil.x >= mouseX && oil.x <= mouseX +50 && oil.y >= mouseY && oil.y <= mouseY + 50 ){
            int score = oil.delete();
            shit.addScore(score);
            iter.remove();
        }
        
        else if(oil.x >=600){
            oil.delete();
            iter.remove();
        }
    }
    
    Iterator<Bullet> bage = bulletList.iterator();
    while(bage.hasNext()){
        Bullet bullet = bage.next();
        bullet.update();
        
        if(bullet.x <=50 && bullet.x >= 0 && bullet.y >= ship.y &&bullet.y <= ship.y +50){
           ship.hitted();
           //mark this game
        }  
        
        else if(bullet.x <= 0){
            bullet.delete();
            bage.remove();
        } 
    }
    
    if (ship.dead){
        ps.addParticle(new PVector(ship.x, ship.y));
        ps.run();
        fill(0);
        textSize(60);
        text("you win", 230, 260);
    }
    
    
    fill(0);
    textSize(12);
    text("you liang " + shit.getScore(),10, 500);


}

void keyPressed(){
    int keyNumber = 0;
    keyNumber = key - 80;
    if(keyNumber == 10 || keyNumber == 42){
        Bullet bullet =shit.shoot();
        if (bullet != null){
            // if shit doesn't have enough oil,then the bullet is null
            bulletList.add(bullet);
        }
    }
        
}
