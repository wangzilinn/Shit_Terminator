

class Bullet{
    
    int x ;
    int y ;
    
    void update(){

        x = x - 5;
        fill(0);
        ellipse(x,y, 10, 10);
        
    }
    
    int delete(){
        fill(255);
        ellipse(x,y, 10, 10);
        return 0;
    }
}
