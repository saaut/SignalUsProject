import pandas as pd
import numpy as np
import glob

import tensorflow as tf
from tensorflow.keras.preprocessing.sequence import pad_sequences
from tensorflow.keras.models import Sequential
from tensorflow.keras.layers import Dense, SimpleRNN

# 폴더 내의 모든 csv 파일을 가져옵니다.
folder_path = 'csvFromJSON/'
csv_files = glob.glob(folder_path + '*.csv')

# 라벨링 데이터 정의
labels_dict = {'딸기': 0, '바나나': 1, '사과': 2}

# 각 csv 파일을 읽어 시퀀스 리스트와 라벨 리스트를 생성
sequences = []
labels = []
for i, csv_file in enumerate(csv_files):
    data = pd.read_csv(csv_file)
    sequences.append(data.values)
    if i < len(csv_files) / 3:
        labels.append(labels_dict['딸기'])
    elif i < 2 * len(csv_files) / 3:
        labels.append(labels_dict['바나나'])
    else:
        labels.append(labels_dict['사과'])

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
model.add(Dense(len(labels_dict), activation='softmax'))

model.compile(loss='sparse_categorical_crossentropy', optimizer='adam', metrics=['accuracy'])
history = model.fit(train_data, train_labels, epochs=10, validation_data=(validation_data, validation_labels))

# 모델 평가
test_loss, test_acc = model.evaluate(test_data, test_labels)
print('Test Accuracy: {}'.format(test_acc))
print(sequences.shape)
print(labels.shape)
print(sequences[67])
print(labels[67])