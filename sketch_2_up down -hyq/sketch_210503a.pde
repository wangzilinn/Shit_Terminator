//*it is a sketch*//

int a = 100;
int y = 26;
boolean up = true;

boolean cover = true;


void setup() 
{
    size(640,520);
    background(100);
}

void draw()
{
    
    if(up){
        if (cover){
            fill(255);
            rect(a,y, 26, 50);
            fill(0);
            rect(a,y-26,26,50);
        }
        y = y + 1;
        if( y % 26 == 0){
            cover=true;
        }else{
            cover=false;
        }
    }else{
        if (cover){
            fill(255);
            rect(a+50,y-26,26,50);
            fill(0);
            rect(a+50,y,26,50);
        }
        y=y-1;
        if(y%26 == 0){
            cover=true;
        }else{
            cover=false;
        }
    }
    
    if (y==520){
        up=false;
    }
    if (y == 0){
        up=true;
    }
        
}

class starShip{
    int x = 0;
    int y = 0;
    
    int lastX = 0;
    int lastY = 0;
    
    void moveUp(){
        fill(0);
        rect(lastX,lastY,26,50);
        
        fill(255);
        rect(x,y - 26, 26,50); 
        
        lastX = x;
        lastY = y;
    }
    void moveDown(){
        fill(0);
        rect(lastX,lastY,26,50);
        
        fill(255);
        rect(x,y + 26, 26,50); 
        
        lastX = x;
        lastY = y;
    }
}
