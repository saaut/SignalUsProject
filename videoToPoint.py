# -*- coding: utf-8 -*-

import mediapipe as mp
import cv2
import csv
import pandas as pd

mp_drawing = mp.solutions.drawing_utils
mp_holistic = mp.solutions.holistic

filename = 'csvFromVideo.csv'



# csv 파일 header 정의
# header = []
# for hand in ['left', 'right']:
#     for finger in ['wrist', 'thumb', 'index', 'middle', 'ring', 'pinky']:
#         for coordinate in ['x', 'y', 'z']:
#             header.append(f'{hand}_{finger}_{coordinate}')

header = []
for hand in ['left', 'right']:
    for point_idx in range(21):
        header.append(f'{hand}_hand_{point_idx}_x')
        header.append(f'{hand}_hand_{point_idx}_y')

# Mediapipe holistic model 초기화
with mp_holistic.Holistic(static_image_mode=False, min_detection_confidence=0.5, min_tracking_confidence=0.5) as holistic:

    cap = cv2.VideoCapture('C:/Users/KuShiro/Documents/수어 관련 데이터/New_sample/원천데이터/REAL/WORD/01/NIA_SL_WORD1501_REAL01_F.mp4')

        # 파일에 header 쓰기
    with open(filename, mode='w', newline='') as file:
        writer = csv.writer(file)
        writer.writerow(header)

    while cap.isOpened():
        # csv 파일에 저장할 변수들 초기화
        left_hand_points = []
        right_hand_points = []
        
        ret, frame = cap.read()
        # frame = cv2.flip(frame, 1)
        # Recolor Feed
        if ret == 1:
            image = cv2.cvtColor(frame, cv2.COLOR_BGR2RGB)
        elif ret == 0:
            break
        # Make Detections
        results = holistic.process(image)
        # print(results.face_landmarks)
        
        # face_landmarks, pose_landmarks, left_hand_landmarks, right_hand_landmarks
        
        # Recolor image back to BGR for rendering
        image = cv2.cvtColor(image, cv2.COLOR_RGB2BGR)
        
        # 1. Draw face landmarks
        mp_drawing.draw_landmarks(image, results.face_landmarks, mp_holistic.FACEMESH_TESSELATION, 
                                 mp_drawing.DrawingSpec(color=(80,110,10), thickness=1, circle_radius=1),
                                 mp_drawing.DrawingSpec(color=(80,256,121), thickness=1, circle_radius=1)
                                 )
        
        # 2. Right hand
        mp_drawing.draw_landmarks(image, results.right_hand_landmarks, mp_holistic.HAND_CONNECTIONS, 
                                 mp_drawing.DrawingSpec(color=(80,22,10), thickness=2, circle_radius=4),
                                 mp_drawing.DrawingSpec(color=(80,44,121), thickness=2, circle_radius=2)
                                 )

        # 3. Left Hand
        mp_drawing.draw_landmarks(image, results.left_hand_landmarks, mp_holistic.HAND_CONNECTIONS, 
                                 mp_drawing.DrawingSpec(color=(121,22,76), thickness=2, circle_radius=4),
                                 mp_drawing.DrawingSpec(color=(121,44,250), thickness=2, circle_radius=2)
                                 )

        # 4. Pose Detections
        mp_drawing.draw_landmarks(image, results.pose_landmarks, mp_holistic.POSE_CONNECTIONS, 
                                 mp_drawing.DrawingSpec(color=(245,117,66), thickness=2, circle_radius=4),
                                 mp_drawing.DrawingSpec(color=(245,66,230), thickness=2, circle_radius=2)
                                 )
        

        # 엄지 손가락 좌표 값 추출 및 변수에 저장
        if results.left_hand_landmarks is not None:
            for idx, landmark in enumerate(results.left_hand_landmarks.landmark):
                x = landmark.x * frame.shape[1]
                y = landmark.y * frame.shape[0]
                left_hand_points.append(x)
                left_hand_points.append(y)

        if results.right_hand_landmarks is not None:
            for idx, landmark in enumerate(results.right_hand_landmarks.landmark):
                x = landmark.x * frame.shape[1]
                y = landmark.y * frame.shape[0]
                right_hand_points.append(x)
                right_hand_points.append(y)

        # 변수를 csv 파일에 쓰기
        with open(filename, mode='a', newline='') as file:
            writer = csv.writer(file)
            writer.writerow(left_hand_points + right_hand_points)

        cv2.imshow('Raw Webcam Feed', image)
        if cv2.waitKey(1) & 0xFF == ord('q'):
            break

cap.release()
cv2.destroyAllWindows()