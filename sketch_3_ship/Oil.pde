

class Oil{
    int x ;
    int y ;
    
    void update(){
        
        x = x + 5;
        fill(200);
        ellipse(x,y, 10, 10);
        
    }
    
    int delete(){
        fill(255);
        ellipse(x,y, 10, 10);
        return 1;
    }
}
