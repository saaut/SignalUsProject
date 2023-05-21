import pandas as pd
import numpy as np
from sklearn.model_selection import train_test_split
from keras.utils import np_utils
from keras.models import Sequential
from keras.layers import Dense, Dropout
import glob

file_path ="C:\\Users\\jooso\\OneDrive\\바탕 화면\\sampledata\\*.csv"

# 빈 리스트 생성하기
all_data = []

# 모든 파일을 반복문으로 불러오기
for filename in glob.glob(file_path):
    # CSV 파일 불러오기
    df = pd.read_csv(filename, header=None)
    # 데이터 추출하기
    data = np.array(df.values.T)
    # 각 열별로 묶은 데이터를 하나의 배열에 넣기(첫번째 행은 제외)
    data_merged = np.concatenate([col[1:] for col in data])
    # all_data 리스트에 추가하기 (빈 배열은 추가하지 않음)
    if data_merged.size>0:
        all_data.append(data_merged)

train_labels=np.array([
    "사과",
    "바나나",
    "딸기"
])

# 데이터와 라벨 나누기
# 데이터
X = np.array(all_data)
# 라벨
y = np.array([np.where(train_labels==label)[0][0] for label in train_labels])

from sklearn.preprocessing import MinMaxScaler

# 데이터 정규화
scaler = MinMaxScaler()
X_scaled = scaler.fit_transform(X)

for i in range(len(all_data)):
    # 새로운 모델 생성
    model = Sequential()
    # 입력층 추가
    model.add(Dense(64, input_dim=X[i].shape[0], activation='relu'))
    # 은닉층 추가
    model.add(Dense(32, activation='relu'))
    # 출력층 추가
    model.add(Dense(3, activation='softmax'))
    # 모델 컴파일
    model.compile(loss='categorical_crossentropy', optimizer='adam', metrics=['accuracy'])
    # 라벨 원-핫 인코딩
    y_train = np_utils.to_categorical(y[i], 3)
    # 입력 데이터 3차원 배열로 변환
    X_train = np.expand_dims(X_scaled[i], axis=0)
    # 라벨 데이터 2차원 배열로 변환
    y_train = np.expand_dims(y_train, axis=0)
    # 모델 학습
    model.fit(X_train, y_train, epochs=50, batch_size=32, verbose=1)
    # 학습된 모델 평가
    scores = model.evaluate(X_train, y_train, verbose=1)
    print(f"Model {i+1} - Test loss:", scores[0])
    print(f"Model {i+1} - Test accuracy:", scores[1])

# 모델 테스트
test_data = pd.read_csv('C:\\Users\\jooso\\OneDrive\\바탕 화면\\folder\\my keypoints\\NIA_SL_WORD1501_REAL01_D.csv')

# 데이터 전처리
test_data = np.array(test_data.values.T)
test_data_merged = np.concatenate([col[:] for col in test_data])
test_data_scaled = scaler.transform(test_data_merged.reshape(1, -1))

# 모델 예측
test_data_scaled = test_data_scaled.reshape(1, -1)  # 2차원 배열로 변환
prediction = model.predict(test_data_scaled)
predicted_label = train_labels[np.argmax(prediction)]
print("Predicted label:", predicted_label)