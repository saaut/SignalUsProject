import pandas as pd
import numpy as np
import glob
import csv
import os
import tensorflow as tf
from collections import OrderedDict
from keras.preprocessing.sequence import pad_sequences
from keras.models import Sequential
from keras.layers import Dense, SimpleRNN, LSTM, Flatten, Bidirectional
from keras.layers import Dropout
from keras.optimizers import Adam
import inspect
import pprint

# 폴더 내의 모든 csv 파일을 가져옵니다.
folder_path = '/Users/soyeon/Downloads/csvFrom08/'  #주의: 레이블의 범주가 너무 많으면 정확도가 떨어집니다! 한 레이블 범주당 중복되는 데이터량이 많아야 좋을 거 같아요.
csv_files = glob.glob(folder_path + '*.csv')
csv_files = sorted(csv_files)
# 라벨링 데이터 정의
label_file = '/Users/soyeon/Downloads/answers.csv'
labels_dict = {}

# 'answer.csv' 파일을 읽어 라벨링 데이터 생성
with open(label_file, 'r', encoding='utf-8') as f:
    reader = csv.reader(f)
    next(reader)  # 헤더 행 스킵
    for row in reader:
        file_name = row[0]
        label = row[1]
        labels_dict[file_name] = label      #딕셔너리 생성(동영상명이 key, 정답이 value)

# 각 csv 파일을 읽어 시퀀스 리스트와 라벨 리스트를 생성
sequences = []
labels = []
for csv_file in csv_files:
    file_name = os.path.basename(csv_file).replace('.csv', '')  # 동영상명 추출
    file_name_parts = file_name.split("_")  # "_"로 분할
    key = "_".join(file_name_parts[:3])  # 처음 3개 단어 선택
    data = pd.read_csv(csv_file)    #한 csv파일을 읽음
    sequences.append(data.values)   #sequences에 읽은 csv파일의 모든 내용을 2차원 배열 형태로 저장
    labels.append(labels_dict[key])   #labels에 그 읽은 csv파일에 대한 정답 저장(<예시>sequences[4]의 정답이 사과이면 labels[4] == 사과)

# print(labels)
# print()
# 라벨 리스트를 숫자로 변환
unique_labels = list(OrderedDict.fromkeys(labels))      #먼저 나온 순서대로 집합으로 만들고 리스트로 저장(중복 단어 제거)
# print(unique_labels)
label_mapping = {label: i for i, label in enumerate(unique_labels)}     #정수 인코딩
labels = [label_mapping[label] for label in labels]     #문자열 형식의 정답이 저장된 labels를 정수 형식으로 변환

# 라벨과 라벨의 매핑 정보를 labels_dict에 저장
labels_dict = {label: label_mapping[label] for label in unique_labels}

# CSV 파일에 매핑 정보 저장
label_mapping_file = '/Users/soyeon/Downloads/label_mapping.csv'
with open(label_mapping_file, 'w', encoding='utf-8', newline='') as f:
    writer = csv.writer(f)
    writer.writerow(['index', 'value'])
    for index, value in labels_dict.items():
        writer.writerow([value, index])

# 시퀀스와 라벨을 numpy 배열로 변환
sequences = pad_sequences(sequences, dtype='float32', padding='post')  # 시퀀스 길이를 맞춰주기 위해 패딩 추가(가장 배열 길이가 긴 sequences 요소를 기준으로 배열 길이를 맞춤)
labels = np.array(labels)

# 데이터와 라벨을 동시에 섞기 위해 인덱스 생성
indices = np.arange(len(sequences))
np.random.shuffle(indices)

# 데이터와 라벨을 인덱스에 맞게 섞음
sequences = sequences[indices]
labels = labels[indices]

# 데이터를 훈련, 검증, 테스트 세트로 분할
train_data_ratio = 0.5
validation_data_ratio = 0.5
num_sequences = len(sequences)
num_train = int(num_sequences * train_data_ratio)
num_val = int(num_sequences * validation_data_ratio)

train_data = sequences[:num_train]
train_labels = labels[:num_train]
validation_data = sequences[num_train : num_train + num_val]
validation_labels = labels[num_train : num_train + num_val]

test_data = sequences[:num_train]
test_labels = labels[:num_train]

# 입력 시퀀스와 출력 시퀀스의 길이 및 차원 설정
input_seq_length = train_data.shape[1]  #최대 프레임 수
input_dim = train_data.shape[2]     #하나의 양손 x,y 키포인트에 대한 배열 길이==84
output_dim = len(unique_labels)     #출력 차원은 유일한 라벨의 개수로 설정합니다.(라벨 범주 수)

print(f"라벨 매핑: {label_mapping}")
print(f"라벨 매핑 길이: {len(label_mapping)}")
print(f"유일 라벨 길이: {len(unique_labels)}")
print(f"검증 데이터 shape: {validation_data.shape}")
print(f"검증 데이터 길이: {len(validation_data)}")
print(f"검증 라벨: {validation_labels}")
print(f"검증 라벨 길이: {len(validation_labels)}")
print(f"테스트 라벨: {test_labels}")
print(f"테스트 라벨 길이: {len(test_labels)}")
print(f"input_seq_length: {input_seq_length}, input_dim: {input_dim}, output_dim: {output_dim}")

#CPU, GPU 선택
with tf.device('/CPU:0'):
    # RNN 모델 생성
    model = Sequential()
    model.add(SimpleRNN(units=64, return_sequences=True, input_shape=(input_seq_length, input_dim)))
    model.add(SimpleRNN(units=64, return_sequences=True))
    model.add(SimpleRNN(units=64, return_sequences=True))
    model.add(SimpleRNN(units=64, return_sequences=True))
    model.add(SimpleRNN(units=64, return_sequences=True))
    model.add(SimpleRNN(units=64, return_sequences=True))
    model.add(SimpleRNN(units=64, return_sequences=True))
    model.add(SimpleRNN(units=64, return_sequences=True))
    model.add(SimpleRNN(units=64, return_sequences=True))
    model.add(SimpleRNN(units=64))
    model.add(Dense(units=output_dim, activation='softmax'))

    # Optimizer 설정
    optimizer = Adam(learning_rate=0.0005)      #학습율 조절을 통해 학습의 강도를 조절(너무 작으면 학습이 느리고 너무 크면 수렴되지 않고 발산)

    # 모델 컴파일
    model.compile(optimizer='adam', loss='sparse_categorical_crossentropy', metrics=['accuracy'])

    # 모델 요약 정보 출력
    model.summary()

    #훈련(학습) 단계
    model.fit(train_data, train_labels, epochs=50, batch_size=64, validation_data=(validation_data, validation_labels))

#모델 성능 평가
loss, accuracy = model.evaluate(test_data, test_labels)

#모델 예측값 계산
predictions = model.predict(test_data)
print("\n=================test results=================")
print(f"loss: {loss:.4f}, accuracy: {accuracy:.4f}")

#테스트한 데이터 수, 테스트한 데이터, 테스트한 데이터의 라벨, 예측한 정답을 출력합니다.
#이 코드로 학습을 시켰을 때 학습 정확도는 0.7~0.9 사이로 나오는 편이고, 검증 정확도는 0.5~0.6, 테스트 정확도는 0.7~0.9로 나옵니다.
# print(f"테스트한 데이터 수: {len(predictions)}")
# max_indices = np.argmax(predictions, axis=1)
# for i in range(len(predictions)):
#     print(f"테스트 데이터: {test_data[i]}")
#     print(f"테스트 라벨: {test_labels[i]}")
#     print(f"예측한 정답: {max_indices[i]}")
#     print()

model.save("my model")