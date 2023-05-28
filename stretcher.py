from scipy import interpolate
import pandas as pd
import numpy as np
import os
import glob

def stretch_data(data, length):
    old_indices = np.arange(len(data))
    new_indices = np.linspace(0, len(data) - 1, length)
    interpolator = interpolate.interp1d(old_indices, data, axis=0, kind='linear', fill_value='extrapolate')
    return interpolator(new_indices)

path = '01_temp'

# 폴더 내의 모든 csv 파일을 가져옵니다.
csv_files = glob.glob(path + '/*.csv')

# 각 csv 파일을 읽어 시퀀스 리스트와 라벨 리스트를 생성
desired_length = 165  # 원하는 길이로 설정

output_folder_path_stretched = '01_temp_stretched'  # 스트레칭된 csv파일을 출력후 저장할 폴더 생성
if not os.path.exists(output_folder_path_stretched):
    os.makedirs(output_folder_path_stretched)

for csv_file in csv_files:
    data = pd.read_csv(csv_file)
    header = data.columns  # 헤더 정보를 저장합니다.
    values = data.values
    if len(values) < desired_length:
        stretched_values = stretch_data(values, desired_length)
    else:
        stretched_values = values[:desired_length]

    # 스트레칭된 csv 파일 저장
    base_name = os.path.basename(csv_file)
    output_file_path = os.path.join(output_folder_path_stretched, base_name)
    # 스트레칭된 데이터프레임 생성 시, 원본 헤더를 유지합니다.
    pd.DataFrame(stretched_values, columns=header).to_csv(output_file_path, index=False, header=True)
