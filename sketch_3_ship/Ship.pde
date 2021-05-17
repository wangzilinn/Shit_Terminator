

class Ship{
    int score = 0;
    double d;
    int r;
    boolean dead = false;
    int deadx, deady;
    
    void addScore(int addedScore){
        if(dead){
            return ;
        }
        score = score + addedScore;
    }
    
    int getScore(){
        return score;
    }
    
    void mouse(){
        if(dead){
            fill(0);
            rect(deadx,deady, 50, 50);
            return ;
        }
        fill(0);
        rect(mouseX,mouseY, 50, 50);
        d = Math.sqrt((pmouseX - mouseX)*(pmouseX - mouseX)+(pmouseY - mouseY)*(pmouseY - mouseY)); 
        r = (int)Math.floor(d/25);
        score = score - r;
        if(score < 0 && !dead){
            dead = true;
            deadx = mouseX;
            deady = mouseY;
        }
    }
    
    Bullet shoot(){
        if(score < 10){
            return null;
        }
        //if we don't have enough oil, then return null directily
        Bullet bullet = new Bullet();
        bullet.x = mouseX + 25;
        bullet.y = mouseY + 25;
        score = score - 10;
        return bullet;
    }
    
    void lose(){
        if(score <= 0){
            
        }
    }
            
            
}
