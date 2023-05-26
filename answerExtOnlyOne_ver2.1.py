import csv
import glob
import json
import os
import re
from collections import Counter

# An empty list to store the extracted values in
names = []

# Path to the directory where the JSON file is located
directory_path = 'C:\\Users\\KuShiro\\Documents\\수어 관련 데이터\\학습용 데이터\\수어 영상\\1.Training\\morpheme\\01\\'

# Find all JSON files in a directory that contain "_D_" in their name
file_paths = glob.glob(directory_path + '\\*_D_*.json')

# Define a regular expression pattern to extract the 4-digit number after "WORD"
pattern = r'WORD(\d{4})'

# Iterate through the files, extract the name value and add it to the list
for file_path in file_paths:
    file_name = os.path.basename(file_path)
    # Extract the number from the file name using the regular expression pattern
    number = int(re.search(pattern, file_name).group(1))
    with open(file_path, 'r', encoding='utf-8') as f:
        data = json.load(f)
        i = data['data'][0]['attributes'][0]['name']
        file_name_parts = file_name.split("_")  # "_"로 분할
        truncated_name = "_".join(file_name_parts[:3])  # 처음 3개 단어 선택
        names.append((number, truncated_name, i))

# Sort the names list based on the extracted number
names = sorted(names, key=lambda x: x[0])

# A dictionary to keep track of name occurrences
name_occurrences = Counter(name[2] for name in names)

# A dictionary to keep track of the current count of each name
current_counts = {}

# Output the extracted name values with their corresponding file names
with open('answers.csv', 'w', newline='', encoding='utf-8') as f:
    writer = csv.writer(f)
    writer.writerow(['File Name', 'Name'])
    for name in names:
        # If the name has occurred more than once, append the count
        if name_occurrences[name[2]] > 1:
            if name[2] in current_counts:
                current_counts[name[2]] += 1
            else:
                current_counts[name[2]] = 1
            name_with_count = name[2] + str(current_counts[name[2]])
        else:
            name_with_count = name[2]
        
        writer.writerow([name[1], name_with_count])
