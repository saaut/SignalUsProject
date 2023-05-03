import json
import csv

with open('C:/Users/KuShiro/Documents/수어 관련 데이터/New_sample/라벨링데이터/REAL/WORD/01_real_word_keypoint/NIA_SL_WORD1501_REAL01_F/NIA_SL_WORD1501_REAL01_F_000000000000_keypoints.json') as f:
    json_data = json.load(f)

left = json_data['people']['hand_left_keypoints_2d']
right = json_data['people']['hand_right_keypoints_2d']
print(left)
print()
print(right)



with open('hand_keypoints.csv', 'w', newline='') as csvfile:
    csv_writer = csv.writer(csvfile)

    # Write header (optional)
    csv_writer.writerow(['A (Left Hand Keypoints)', 'B (Right Hand Keypoints)'])

    # Write the hand keypoints data
    for left_point, right_point in zip(left, right):
        csv_writer.writerow([left_point, right_point])
