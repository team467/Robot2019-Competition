package org.usfirst.frc.team467.robot.vision;

import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

import edu.wpi.cscore.UsbCamera;

public class VisionProcessing {
	
	public Mat source;

	public double cubeCenterPointY;
	public double cubeCenterPointX;
	public double DistToCube;
	public double cubeWidth;
	
	private DetectPowerCubePipeline pipeline;
	private static final Logger LOGGER = Logger.getLogger(VisionProcessing.class);
	private static VisionProcessing instance;

	private double windowHeight;
	private double windowWidth;
	private double cameraAngleToCube;
	private double cameraPx = 160;
	
	public static final double CUBE_WIDTH = 13.0;
	public static final double CUBE_HEIGHT = 11.0;
	private static final double FOCAL_LENGTH = 634;

	private UsbCamera camera = null;

	private double average;
	private MovingAverage averageAngle;


	
		
	private VisionProcessing() {
		pipeline = new DetectPowerCubePipeline();
		averageAngle = new MovingAverage(25); 
	}

	public static VisionProcessing getInstance() {
		if (instance == null) {
			instance = new VisionProcessing();
		}
		return instance;
	}

	public double findCube(Mat source) {
		double cameraDistanceX;
		
		cameraAngleToCube = Double.NaN;

		if (!source.empty()) {
			pipeline.process(source);
			windowHeight = source.size().height;
			windowWidth = source.size().width;
			ArrayList<Rect> boundingBoxes = new ArrayList<Rect>();
			for (MatOfPoint points : pipeline.convexHullsOutput()) {
				Rect box = Imgproc.boundingRect(points);
				boundingBoxes.add(box);
				//LOGGER.info("ADDED Bounding BOX X:" + box.x + " Y: " + box.y + " H: " + box.height + " W: " + box.width);	
				cubeCenterPointY = (box.height / 2) + box.y;
				cubeCenterPointX = (box.width / 2) + box.x;
				Imgproc.rectangle(source, new Point(box.x, box.y), new Point(box.x + box.width, box.y + box.height),
						new Scalar(0, 255, 0, 0), 5);
				
				cameraDistanceX = cubeCenterPointX - (windowWidth / 2);				
				cameraAngleToCube = 0.00294524375 * cameraDistanceX;
							
				if(!Double.isNaN(cameraAngleToCube)) {
					average = averageAngle.average(cameraAngleToCube);
				}				
				
				break;
			}
		}
			return Math.toDegrees(average);
		}
	

	/**
	 * This method is a generated getter for the output of a Find_Contours.
	 * 
	 * @return ArrayList<MatOfPoint> output from Find_Contours.
	 */
	public void findContoursOutput() {
		System.out.println("COUNT: " + pipeline.filterContoursOutput().size());
		for (MatOfPoint points : pipeline.convexHullsOutput()) {
			System.out.println("Test " + points);
		}
	}
	public double avgAngle() {
		return Math.toDegrees(average);
	}
	public double cameraAngle() {
		return Math.toDegrees(cameraAngleToCube);
	}


}