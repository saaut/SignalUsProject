import csv
import matplotlib
matplotlib.use('TkAgg')
import matplotlib.pyplot as plt
from matplotlib.animation import FuncAnimation

csv_file = 'csvFromJSON/NIA_SL_WORD1501_REAL01_F.csv'  # 파일명을 변경해 주세요.

with open(csv_file, 'r') as f:
    reader = csv.reader(f)
    header = next(reader)  # 헤더를 읽고 스킵
    data = [row for row in reader]

left_hand_x = [list(map(float, row[:42:2])) for row in data]
left_hand_y = [list(map(float, row[1:42:2])) for row in data]
right_hand_x = [list(map(float, row[42::2])) for row in data]
right_hand_y = [list(map(float, row[43::2])) for row in data]

num_frames = len(data)

fig, ax = plt.subplots(figsize=(10, 5))

# x축과 y축의 범위를 설정합니다.
ax.set_xlim(0, 1920)
ax.set_ylim(1080, 0)  # y축의 방향을 바꾸기 위해 1080, 0 순서로 설정

left_scatter = ax.scatter([], [], c='blue', label='Left Hand', s=1)
right_scatter = ax.scatter([], [], c='red', label='Right Hand', s=1)
ax.legend()

def update(frame):
    left_scatter.set_offsets(list(zip(left_hand_x[frame], left_hand_y[frame])))
    right_scatter.set_offsets(list(zip(right_hand_x[frame], right_hand_y[frame])))
    ax.set_title(f'Frame {frame+1}')
    return left_scatter, right_scatter,

ani = FuncAnimation(fig, update, frames=num_frames, interval=33, blit=True)     #interval: 한 프레임 넘기는 속도(ms)

plt.show()
