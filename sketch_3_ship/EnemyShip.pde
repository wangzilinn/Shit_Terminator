

class EnemyShip{
    int x = 0;
    int y = 0;
    
    int blood = 50;
    boolean dead = false;
    
    void moveUp(){
        if(dead){
            return;
        }
        y = y - 10;
        
        fill(blood);
        rect(x,y, 50,50); 
        

    }
    
    void moveDown(){
        if(dead){
            return;
        }
        
        y = y + 10;
        fill(blood);
        rect(x,y, 50,50); 
        
    } 
    
    Oil leak(){
        if(dead){
            return null;
        }
        Oil oil = new Oil();
        oil.x = x;
        oil.y = y;
        return oil;
    }
    
    int explode(){
        fill(255);
        rect(x,y, 50, 50);
        //explosion effect 
        dead = true;
        return 50;
    }
    
    int hitted(){
        blood = blood - 10;
        if(blood == 0){
            int explodeScore = explode();
            return explodeScore;
        }
        return 10;
    }
}
