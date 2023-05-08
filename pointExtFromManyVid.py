import os
import mediapipe as mp
import cv2
import csv

mp_drawing = mp.solutions.drawing_utils
mp_holistic = mp.solutions.holistic

header = []
for hand in ['left', 'right']:
    for point_idx in range(21):
        header.append(f'{hand}_hand_{point_idx}_x')
        header.append(f'{hand}_hand_{point_idx}_y')

# Path to video files
video_path = "/Users/kushiro/Downloads/New_sample/원천데이터/REAL/WORD/01"

# Initialize Mediapipe holistic model
with mp_holistic.Holistic(static_image_mode=False, min_detection_confidence=0.5, min_tracking_confidence=0.5) as holistic:

    for video_file in os.listdir(video_path):
        if not video_file.endswith(".mp4"):
            continue

        # Create CSV file for current video
        filename = os.path.splitext(video_file)[0] + ".csv"
        csv_path = os.path.join("my keypoints", filename)
        with open(csv_path, mode='w', newline='') as file:
            writer = csv.writer(file)
            writer.writerow(header)

        # Open video file
        video_file_path = os.path.join(video_path, video_file)
        cap = cv2.VideoCapture(video_file_path)

        while cap.isOpened():
            # Initialize variables to store hand landmarks
            left_hand_points = []
            right_hand_points = []
            
            ret, frame = cap.read()
            if ret == 1:
                image = cv2.cvtColor(frame, cv2.COLOR_BGR2RGB)
            elif ret == 0:
                break

            # Run Mediapipe holistic model on current frame
            results = holistic.process(image)
            
            # Recolor image back to BGR for rendering
            image = cv2.cvtColor(image, cv2.COLOR_RGB2BGR)
            
            # Draw landmarks on image
            mp_drawing.draw_landmarks(image, results.face_landmarks, mp_holistic.FACEMESH_TESSELATION, 
                                     mp_drawing.DrawingSpec(color=(80,110,10), thickness=1, circle_radius=1),
                                     mp_drawing.DrawingSpec(color=(80,256,121), thickness=1, circle_radius=1)
                                     )
            mp_drawing.draw_landmarks(image, results.right_hand_landmarks, mp_holistic.HAND_CONNECTIONS, 
                                     mp_drawing.DrawingSpec(color=(80,22,10), thickness=2, circle_radius=4),
                                     mp_drawing.DrawingSpec(color=(80,44,121), thickness=2, circle_radius=2)
                                     )
            mp_drawing.draw_landmarks(image, results.left_hand_landmarks, mp_holistic.HAND_CONNECTIONS, 
                                     mp_drawing.DrawingSpec(color=(121,22,76), thickness=2, circle_radius=4),
                                     mp_drawing.DrawingSpec(color=(121,44,250), thickness=2, circle_radius=2)
                                     )
            mp_drawing.draw_landmarks(image, results.pose_landmarks, mp_holistic.POSE_CONNECTIONS, 
                                     mp_drawing.DrawingSpec(color=(245,117,66), thickness=2, circle_radius=4),
                                     mp_drawing.DrawingSpec(color=(245,66,230), thickness=2, circle_radius=2)
                                     )

                    # Extract and store hand landmarks in variables
            if results.left_hand_landmarks is not None:
                for idx, landmark in enumerate(results.left_hand_landmarks.landmark):
                    left_hand_points.append(landmark.x)
                    left_hand_points.append(landmark.y)
            else:
                left_hand_points += [0] * 42

            if results.right_hand_landmarks is not None:
                for idx, landmark in enumerate(results.right_hand_landmarks.landmark):
                    right_hand_points.append(landmark.x)
                    right_hand_points.append(landmark.y)
            else:
                right_hand_points += [0] * 42

            # Write hand landmarks to CSV file
            with open(csv_path, mode='a', newline='') as file:
                writer = csv.writer(file)
                writer.writerow(left_hand_points + right_hand_points)
        
            # Release video capture object and destroy any open windows
            cv2.imshow('Raw Webcam Feed', image)
            if cv2.waitKey(1) & 0xFF == ord('q'):
                break
cap.release()
cv2.destroyAllWindows()
