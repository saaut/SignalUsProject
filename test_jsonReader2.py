import json
import csv
import glob
import re
import os

header = []
for hand in ['left', 'right']:
    for point_idx in range(21):
        header.append(f'{hand}_hand_{point_idx}_x')
        header.append(f'{hand}_hand_{point_idx}_y')

folder_path = '/Users/kushiro/Downloads/New_sample/라벨링데이터/REAL/WORD/01_real_word_keypoint'
vid_paths = glob.glob(folder_path + '*\\')
file_list = []

for path in vid_paths:
    file_list.extend(glob.glob(path + '*.json'))

# print(file_list[0])

def extract_number(filename):
    return int(re.findall(r'\d+', filename)[0])

file_list_sorted = sorted(file_list, key=extract_number)

vid_names = []

for file in file_list_sorted:
    vid_name = os.path.basename(os.path.normpath(file))
    vid_names.append(vid_name)

# print(vid_names[0])

# with open('csvFromJson.csv', 'w', newline='') as file:
#     writer = csv.writer(file)
#     writer.writerow(header)

#     for file_name in file_list_sorted:
#         with open(file_name, 'r') as f:
#             json_data = json.load(f)
#         left = json_data['people']['hand_left_keypoints_2d']
#         right = json_data['people']['hand_right_keypoints_2d']
        
#         for index, value in enumerate(left):    #1 구분자 제거
#             if value == 1:
#                 del left[index]
#         for index, value in enumerate(right):
#             if value == 1:
#                 del right[index]

#         writer.writerow(left + right)  # Move this line inside the 'with' block