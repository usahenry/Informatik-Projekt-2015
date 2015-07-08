package de.duererInfoProject.fruitNinja;

import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.InputEvent;

import edu.ufl.digitalworlds.j4k.J4KSDK;
import edu.ufl.digitalworlds.j4k.Skeleton;

//Controls the Kinect
public class Kinect extends J4KSDK {
	
	private Robot robot;
	private boolean mouseButtonPressed;
	private int mainHand, secondHand;
	private Controller controller;
	
	public Kinect(Controller controller) {
		super();
		mouseButtonPressed = false;
		this.controller = controller;
		
		updateHand();
		
		//Create robot to control mouse
		try {
			robot = new Robot();
		} catch (AWTException e) {
			controller.errorMessage(e, "Error while starting Robot to move mouse by Kinect!");
		}
	}
	
	@Override
	public void onColorFrameEvent(byte[] data) {
	}

	@Override
	public void onDepthFrameEvent(short[] depth_frame, byte[] player_index, float[] XYZ, float[] UV) {
	}

	@Override //Called every time the Kinect receives a new Skeleton frame
	public void onSkeletonFrameEvent(boolean[] skeleton_tracked, float[] joint_position, float[] joint_orientation, byte[] joint_status) {
		Skeleton skeleton = null;
		for (int i = 0; i < skeleton_tracked.length; i++) {
			if (skeleton_tracked[i]) skeleton = Skeleton.getSkeleton(i, skeleton_tracked, joint_position, joint_orientation, joint_status, this);
		}
		
		if (skeleton != null) moveMousePointer(skeleton);
	}
	
	//Moves mouse to the hand positon and clicks if the second hand is over the head
	private void moveMousePointer(Skeleton skeleton) {
		float handX = skeleton.get3DJointX(mainHand);
		float handY = skeleton.get3DJointY(mainHand);
		float otherHandY = skeleton.get3DJointY(secondHand);
		float headY = skeleton.get3DJointY(Skeleton.HEAD);
		
		//Transform coordinates and get Screen Size
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		handX = (handX * 2) + 0.5f;
		handY = (- 2 * handY) + 0.5f;
		
		//Set mouse position and click
		robot.mouseMove((int) (handX * screenSize.width), (int) (handY * screenSize.height));
		if (otherHandY >= headY && !mouseButtonPressed) {
			robot.mousePress(InputEvent.BUTTON1_MASK);
			mouseButtonPressed = true;
		} else if (otherHandY < headY && mouseButtonPressed) {
			robot.mouseRelease(InputEvent.BUTTON1_MASK);
			mouseButtonPressed = false;
		}
	}
	
	//Get main hand from preferences
	public void updateHand() {
		mainHand = Skeleton.HAND_LEFT;
		secondHand = Skeleton.HAND_RIGHT;
		if (controller.getPreferences().getBoolean("rightHand", true)) {
			mainHand = Skeleton.HAND_RIGHT;
			secondHand = Skeleton.HAND_LEFT;
		}
	}
}
