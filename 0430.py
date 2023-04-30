import tensorflow as tf
from tensorflow import keras
from keras import layers
from keras.models import Sequential
import numpy as np
from sklearn.preprocessing import LabelEncoder
from keras.utils import np_utils

# 학습 데이터
train_data = np.array([
  [0.1, 0.2, 0.5], # A
  [0.6, 0.5, 0.2], # B
  [0.9, 0.8, 0.7]  # C
])

train_labels = np.array([
  "A",
  "B",
  "C"
])

# 전처리
train_data = train_data.reshape((train_data.shape[0], train_data.shape[1], 1))

# 라벨 인코딩
le = LabelEncoder()
train_labels_encoded = le.fit_transform(train_labels)

# 모델 생성
model = Sequential([
  layers.Conv1D(32, 2, activation='relu', input_shape=(train_data.shape[1], 1)),
  layers.MaxPooling1D(2),
  layers.Flatten(),
  layers.Dense(64, activation='relu'),
  layers.Dense(3, activation='softmax')
])

# 모델 컴파일
model.compile(loss='categorical_crossentropy', optimizer='adam', metrics=['accuracy'])

# 모델 학습
history = model.fit(train_data, keras.utils.to_categorical(train_labels_encoded), epochs=100, batch_size=1, verbose=1)

test_data = np.array([[0.9, 0.8, 0.7]])
test_data = test_data.reshape((test_data.shape[0], test_data.shape[1], 1))
pred_prob = model.predict(test_data)
pred_class = np.argmax(pred_prob)
pred_label = le.inverse_transform([pred_class])
print(pred_label)