import numpy as np
import matplotlib.pyplot as plt
import cv2
import time


class TaskQual():
    def __init__(self):
        self.image = None
        self.lower = np.array([12, 50, 52])
        self.upper = np.array([35, 208, 212])
    def adjustment(self,x_diff,y_diff):
        """Return the Power of the LR and depth movement"""
        LR = 0
        depth = -5
        LRPower = 30
        DPower = 30
        x_ratio = (x_diff / 200)
        print(x_diff,y_diff)
        if y_diff > 10:
            """y_diff > 0 mean the object is below the center of the image"""
            print("Go Down")
            depth = -DPower
        elif y_diff < -10:
            print("Go Up")
            # depth = DPower
        else:
            print("Depth Stay")
        if x_diff > 10:
            """x_diff > 0 mean the object is at the Right side of the image"""
            print("Go Right")
            LR = LRPower * x_ratio
        elif x_diff < -10:
            print("Go Left")
            LR = LRPower * x_ratio
        else:
            print("LR Stay")
        return LR,depth

    def step(self, image, startTime = None):
        #in cm, expected the camera must capture the orange object/gate wihin this distance, suppose 1cm per second
        focalLength = 30 
        completed = False
        LR, UD, FB, turn = 0, 0, 0, 0
        #self.image, _, x_diff,y_diff = self.findObjects(image,self.lower,self.upper)
        #move forward until reach the focal length, no detection before something expected to be found in the distance
        elapsed_time = time.time() - startTime
        if elapsed_time <= 2:
            FB = 20
            return completed, LR, UD, FB, turn, startTime
        else:
            FB = 0
            self.image, _, x_diff,y_diff = self.pointGate(image,self.lower,self.upper)
            LR,UD,FB,turn = 0,0,5,0
            if x_diff is not None and y_diff is not None:
                LR,UD = self.adjustment(x_diff,y_diff)
            #print(centers)
            return completed, LR, FB, UD, turn, startTime
            
            #LR,UD,FB,turn = 0,0,5,0
        
    def pointGate(self, img, lower, upper):
        # Convert the image to HSV color space
        hsv = cv2.cvtColor(img, cv2.COLOR_BGR2HSV)
        # Create a mask for the specified color range
        mask = cv2.inRange(hsv, lower, upper)
        # Apply threshold
        _, edges = cv2.threshold(mask, 150, 255, cv2.THRESH_BINARY)
        # Find contours in the edge image
        contours, hierarchy = cv2.findContours(edges, cv2.RETR_EXTERNAL, cv2.CHAIN_APPROX_SIMPLE)
        centers = []
        # Sort contours by area and keep the largest two
        contours = sorted(contours, key=cv2.contourArea, reverse=True)[:2]
        # indicate the distance between gate centre and camera center
        x_diff = 0
        y_diff = 0
        if len(contours) > 0:
            for contour in contours:
                try:
                    # Calculate the center of each contour
                    center = cv2.moments(contour)["m10"] / cv2.moments(contour)["m00"]
                    center = (int(center), int(cv2.moments(contour)["m01"] / cv2.moments(contour)["m00"]))
                    centers.append(center)
                    # Apply the mask to the original image
                    #frame = cv2.bitwise_and(img, img, mask=mask)
                    # Set line thickness and color
                    line_thickness = 2
                    color = (0, 0, 255)  # Red color
                    # Draw a circle at the center of each object
                    cv2.circle(img, center, 10, color, line_thickness)
                except ZeroDivisionError:
                    frame = None
                    return frame, mask, x_diff, y_diff
                    
            
            center1 = centers[0]
            center2 = centers[1]
            center_difference = (((center1[0] + center2[0]) // 2), ((center1[1] + center2[1]) // 2))
            
            color = (0, 0, 255)  # Change (0, 0, 255) for different color
            colorRed = (0, 255, 0)  # Change (0, 0, 255) for different color
            cv2.circle(img, center_difference, 10, color, line_thickness)
            # Calculate half the image width and height for cross dimensions
            frame = cv2.bitwise_and(img, img, mask=mask)
            img_height, img_width = frame.shape[:2]
            half_width, half_height = img_width // 2, img_height // 2
            # Draw horizontal line through the center
            cv2.line(frame, (center_difference[0] - half_width, center_difference[1]), (center_difference[0] + half_width, center_difference[1]), color, line_thickness)
            # Draw vertical line through the center
            cv2.line(frame, (center_difference[0], center_difference[1] - half_height), (center_difference[0], center_difference[1] + half_height), color, line_thickness)
            cv2.line(frame, (center_difference[0], center_difference[1]), (half_width, half_height), colorRed, line_thickness)
            
            # calculate the different between the center of the image and the center of the object
            x_diff = center_difference[0] - half_width
            y_diff = center_difference[1] - half_height
            
            # return values for adjustment()
            return frame, mask, x_diff, y_diff
        
        return frame, mask, x_diff, y_diff
        
        
    

if __name__ == "__main__":
    # use image to Test the TaskA class
    task = TaskQual()
    Vdopath = "PutYourImageInputHere"
    cap = cv2.VideoCapture(Vdopath)
    startTime = time.time()
    while True:
        ret, frame = cap.read()
        if ret:
            try:
                cv2.imshow("image2",frame)
            except:
                continue
            completed, LR, UD, FB, turn, startTime = task.step(frame, startTime)
            print(LR, UD, startTime)
            try:
                cv2.imshow("image",task.image)
            except:
                continue
            cv2.waitKey(1)