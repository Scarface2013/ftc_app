package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

/**
 * Created by Sean O on 11/23/2016.
 */
@Autonomous(name="Red Wait 6", group="Red")
public class RedWait6 extends AutonomousBase {
    boolean init;
    @Override
    public void gameState() {
        if(!init){
            init = true;
            map.setRobot(6,11.25);
        }
        super.gameState();
        switch(gameState){
            case 0: //Start
                if(tDiff == 0){tDiff = getRuntime();}
                if(getRuntime() > 15 && !gyro.isCalibrating()) {
                    gameState = 1;
                }
                break;
            case 1: //moves to shooter post
                map.setGoal(6.3, 9);
                if(linedUp()){
                    moveState = MoveState.FORWARD;
                }else{
                    moveState = MoveState.TURN_TOWARDS_GOAL;
                }
                if(map.distanceToGoal()<=.1){
                    moveState = MoveState.STOP;
                    gameState = 2;
                }
                break;
            case 2: // turns ..
                desiredAngle = 140;
                if(linedUpAngle()){
                    moveState = MoveState.STOP;
                    gameState = 3;
                    sTime = getRuntime();
                    telemetry.addData("sTime", sTime);
                }else{
                    moveState = MoveState.TURN_TOWARDS_ANGLE;
                }
                break;
            case 3: // ... and shoots
                 moveState = MoveState.SHOOT_WHEEL;
                 if(getRuntime() - sTime >= 1){
                     moveState = MoveState.SHOOT_CONVEYOR;
                 }
                 if(getRuntime() - sTime >= 3) {
                     moveState = MoveState.SHOOT_STOP;
                     gameState = 4;
                 }
                break;
            case 4: //MOVE TO KNOCK OFF BALL
                map.setGoal(5,6);
                if(linedUp()){
                    moveState = MoveState.FORWARD;
                }else{
                    moveState = MoveState.TURN_TOWARDS_GOAL;
                }
                if(map.distanceToGoal()<=.1){
                    moveState = MoveState.STOP;
                }
                break;
            case 777:
                moveState = MoveState.STOP;
                break;
        } 
    }
}
