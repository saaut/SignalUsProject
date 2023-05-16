import pandas as pd
import numpy as np
import glob
import csv
import os
import tensorflow as tf
from tensorflow.keras.preprocessing.sequence import pad_sequences
from tensorflow.keras.models import Sequential
from tensorflow.keras.layers import Dense, SimpleRNN

# 폴더 내의 모든 csv 파일을 가져옵니다.
folder_path = 'csvFromJSON3/'
csv_files = glob.glob(folder_path + '*.csv')

# 라벨링 데이터 정의
label_file = 'answers.csv'
labels_dict = {}

# 'answer.csv' 파일을 읽어 라벨링 데이터 생성
with open(label_file, 'r', encoding='utf-8') as f:
    reader = csv.reader(f)
    next(reader)  # 헤더 행 스킵
    for row in reader:
        file_name = row[0]
        label = row[1]
        labels_dict[file_name] = label

# 각 csv 파일을 읽어 시퀀스 리스트와 라벨 리스트를 생성
sequences = []
labels = []
for csv_file in csv_files:
    file_name = os.path.basename(csv_file).replace('.csv', '')  # 파일 이름 추출
    data = pd.read_csv(csv_file)
    sequences.append(data.values)
    labels.append(labels_dict[file_name])

# 라벨 리스트를 숫자로 변환
unique_labels = list(set(labels))
label_mapping = {label: i for i, label in enumerate(unique_labels)}
labels = [label_mapping[label] for label in labels]

# 시퀀스와 라벨을 numpy 배열로 변환
sequences = pad_sequences(sequences, dtype='float32', padding='post')  # 시퀀스 길이를 맞춰주기 위해 패딩 추가
labels = np.array(labels)

# 데이터를 훈련, 검증, 테스트 세트로 분할
train_data_ratio = 0.7
validation_data_ratio = 0.15
num_sequences = len(sequences)
num_train = int(num_sequences * train_data_ratio)
num_val = int(num_sequences * validation_data_ratio)

train_data = sequences[:num_train]
train_labels = labels[:num_train]
validation_data = sequences[num_train : num_train + num_val]
validation_labels = labels[num_train : num_train + num_val]
test_data = sequences[num_train + num_val:]
test_labels = labels[num_train + num_val:]

# 모델 생성 및 학습
model = Sequential()
model.add(SimpleRNN(50, input_shape=(None, train_data.shape[2]), return_sequences=False))
model.add(Dense(len(label_mapping), activation='softmax'))

model.compile(loss='sparse_categorical_crossentropy', optimizer='adam', metrics=['accuracy'])
history = model.fit(train_data, train_labels, epochs=10, validation_data=(validation_data, validation_labels))

# 모델 평가
test_loss, test_acc = model.evaluate(test_data, test_labels)
print('Test Accuracy: {}'.format(test_acc))

print(label_mapping)
print(labels)