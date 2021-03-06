package gameobject;

import state.GameWorldState;
import effect.Animation;
import effect.AnimationHandler;
import effect.CacheDataLoader;
import static gameobject.ParticularObject.LEFT_DIR;
import static gameobject.ParticularObject.NOBEHURT;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class SmallRedGun extends ParticularObject{

    
    private long startTimeToShoot;

    public SmallRedGun(float x, float y, GameWorldState gameWorld) {
        super(x, y, 127, 89,"smallredgun", 0, 100, gameWorld);
        startTimeToShoot = 0;
        setTimeForNoBehurt(300000000);
    }

    @Override
    public void attack() {
    
        Bullet bullet = new YellowFlowerBullet(getPosX(), getPosY(), getGameWorld());
        bullet.setSpeedX(-3);
        bullet.setSpeedY(3);
        bullet.setTeamType(getTeamType());
        getGameWorld().bulletManager.addObject(bullet);
        
        bullet = new YellowFlowerBullet(getPosX(), getPosY(), getGameWorld());
        bullet.setSpeedX(3);
        bullet.setSpeedY(3);
        bullet.setTeamType(getTeamType());
         getGameWorld().bulletManager.addObject(bullet);
    }

    
    public void Update(){
        super.Update();
        if(System.nanoTime() - startTimeToShoot > 1000*10000000*2.0){
            attack();
            System.out.println("Red Eye attack");
            startTimeToShoot = System.nanoTime();
        }
    }
    
    @Override
    public Rectangle getBoundForCollisionWithEnemy() {
        Rectangle rect = getBoundForCollisionWithMap();
        rect.x += 20;
        rect.width -= 40;
        
        return rect;
    }

    @Override
    public void draw(Graphics2D g2) {
        if(!isObjectOutOfCameraView()){
            if(getState() == NOBEHURT && (System.nanoTime()/10000000)%2!=1){
                // plash...
            }else{
            	g2.setColor(Color.BLACK);
            	g2.fillRect((int)(getPosX() - getGameWorld().camera.getPosX()-51), 
                        (int)(getPosY() - getGameWorld().camera.getPosY()-61),102,10+2);
            	g2.setColor(Color.RED);
            	g2.fillRect((int)(getPosX() - getGameWorld().camera.getPosX()-50), 
                        (int)(getPosY() - getGameWorld().camera.getPosY()-60),(int)getBlood(),10);
                if(getDirection() == LEFT_DIR){
                    animationH.backAnim.Update(System.nanoTime());
                    animationH.backAnim.draw((int) (getPosX() - getGameWorld().camera.getPosX()), 
                            (int)(getPosY() - getGameWorld().camera.getPosY()), g2);
                }else{
                    animationH.forwardAnim.Update(System.nanoTime());
                    animationH.forwardAnim.draw((int) (getPosX() - getGameWorld().camera.getPosX()), 
                            (int)(getPosY() - getGameWorld().camera.getPosY()), g2);
                }
            }
        }
    }
    
}
