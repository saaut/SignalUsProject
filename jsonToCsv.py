import json
import csv
import glob
import re
import os

header = []     # 빈 헤더 선언
for hand in ['left', 'right']:  # 헤더 저장
    for point_idx in range(21):
        header.append(f'{hand}_hand_{point_idx}_x')
        header.append(f'{hand}_hand_{point_idx}_y')

folder_path = 'C:\\Users\\KuShiro\\Documents\\수어 관련 데이터\\New_sample\\라벨링데이터\\REAL\\WORD\\01_real_word_keypoint\\'
vid_paths = glob.glob(folder_path + '*\\')  # 각 동영상 이름은 각 폴더 이름으로 되어있는 규칙임, 그 안에 프레임당 키포인트가 한 JSON파일을 이루고 있는 구조
file_list = []      # 빈 가장 하위 파일 경로(JSON파일) 선언

for path in vid_paths:  # 모든 JSON파일 리스트화
    file_list.extend(glob.glob(path + '*.json'))

def extract_number(filename):
    return int(re.findall(r'\d+', filename)[0])

file_list_sorted = sorted(file_list, key=extract_number)    # 정렬

folder_mapping = {}

for file in file_list_sorted:   # 딕셔너리 키를 상위 폴더(동영상명)으로 하고 각 키에 대한 리스트 값을 폴더 안 JSON파일로 채우기
    folder_name = os.path.basename(os.path.dirname(file))
    if folder_name not in folder_mapping:
        folder_mapping[folder_name] = []
    folder_mapping[folder_name].append(file)

output_folder_path = 'csvFromJSON2'     # csv파일을 출력후 저장할 폴더 생성
if not os.path.exists(output_folder_path):
    os.makedirs(output_folder_path)

for folder, json_files in folder_mapping.items():   #키-값 쌍을 순회
    output_file_path = os.path.join(output_folder_path, folder + '.csv')    #동영상명으로 csv파일 만들기
    with open(output_file_path, 'w', newline='') as file:
        writer = csv.writer(file)
        writer.writerow(header)     #첫 행에 헤더 쓰기

        for json_file in json_files:    #폴더 안의 모든 JSON파일을 순회
            with open(json_file, 'r') as f:
                json_data = json.load(f)
            left = json_data['people']['hand_left_keypoints_2d']
            right = json_data['people']['hand_right_keypoints_2d']

            for index, value in enumerate(left):    #1 구분자 제거
                if value == 1:
                    del left[index]
            for index, value in enumerate(right):
                if value == 1:
                    del right[index]


            writer.writerow(left + right)   #한 JSON파일에 뽑은 양손 키포인트를 한 행에 쓰기
