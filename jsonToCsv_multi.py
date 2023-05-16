import json
import csv
import glob
import re
import os
from multiprocessing import Pool

header = []  # 빈 헤더 선언
for hand in ['left', 'right']:  # 헤더 저장
    for point_idx in range(21):
        header.append(f'{hand}_hand_{point_idx}_x')
        header.append(f'{hand}_hand_{point_idx}_y')

folder_path = 'C:\\Users\\KuShiro\\Documents\\수어 관련 데이터\\New_sample\\라벨링데이터\\REAL\\WORD\\01_real_word_keypoint\\'
vid_paths = glob.glob(folder_path + '*\\')  # 각 동영상 이름은 각 폴더 이름으로 되어있는 규칙임, 그 안에 프레임당 키포인트가 한 JSON파일을 이루고 있는 구조
file_list = []  # 빈 가장 하위 파일 경로(JSON파일) 선언

for path in vid_paths:  # 모든 JSON파일 리스트화
    file_list.extend(glob.glob(path + '*.json'))

def extract_number(filename):
    return int(re.findall(r'\d+', filename)[0])

file_list_sorted = sorted(file_list, key=extract_number)  # 정렬

folder_mapping = {}

for file in file_list_sorted:  # 딕셔너리 키를 상위 폴더(동영상명)으로 하고 각 키에 대한 리스트 값을 폴더 안 JSON파일로 채우기
    folder_name = os.path.basename(os.path.dirname(file))
    if folder_name not in folder_mapping:
        folder_mapping[folder_name] = []
    folder_mapping[folder_name].append(file)

output_folder_path = 'csvFromJSON4'  # csv파일을 출력후 저장할 폴더 생성
if not os.path.exists(output_folder_path):
    os.makedirs(output_folder_path)

def process_json_file(json_file):
    with open(json_file, 'r') as f:
        json_data = json.load(f)
    left = json_data['people']['hand_left_keypoints_2d']
    right = json_data['people']['hand_right_keypoints_2d']

    for index, value in enumerate(left):  # 1 구분자 제거
        if value == 1:
            del left[index]
    for index, value in enumerate(right):
        if value == 1:
            del right[index]

    return left + right

def write_csv(data):
    folder, json_files = data
    output_file_path = os.path.join(output_folder_path, folder + '.csv')  # 동영상명으로 csv파일 만들기

    # 이미 존재하는 CSV 파일인 경우 파일을 이어서 엽니다.
    if os.path.exists(output_file_path):
        mode = 'a'  # 이어쓰기 모드
    else:
        mode = 'w'  # 새로 쓰기 모드
    
    with open(output_file_path, mode, newline='') as file:
        writer = csv.writer(file)

        # CSV 파일이 비어있는 경우 헤더를 쓰기
        if mode == 'w':
            writer.writerow(header)
        
        # JSON 파일을 읽어서 CSV 파일에 작성
        for json_file in json_files:
            keypoints = process_json_file(json_file)
            writer.writerow(keypoints)

def extract_completed_folders():
    existing_csv_files = glob.glob(output_folder_path + '/*.csv')
    completed_folders = set()

    for csv_file in existing_csv_files:
        folder_name = os.path.splitext(os.path.basename(csv_file))[0]
        completed_folders.add(folder_name)
    
    return completed_folders

if __name__ == '__main__':
    pool = Pool()  # 프로세스 풀 생성

    completed_folders = extract_completed_folders()  # 이전 작업이 완료된 폴더 추적

    remaining_folders = {folder for folder in folder_mapping.keys() if folder not in completed_folders}  # 작업할 폴더 필터링

    tasks = [(folder, folder_mapping[folder]) for folder in remaining_folders]  # 작업할 폴더와 해당 폴더의 JSON 파일 리스트 생성

    pool.map(write_csv, tasks)  # 멀티 프로세싱으로 작업 실행

    pool.close()
    pool.join()
