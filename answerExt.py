import csv
import glob
import json
import os

# An empty list to store the extracted values in
names = []

# Path to the directory where the JSON file is located
directory_path = '/Users/kushiro/Downloads/morpheme/01'

# Find all JSON files in a directory
file_paths = glob.glob(directory_path + '/*.json')
file_names = os.listdir(directory_path)
sorted_file_names = sorted(file_names)

# Iterate through the files, extract the name value and add it to the list
for file_name in sorted_file_names:
    file_path = os.path.join(directory_path, file_name)
    with open(file_path, 'r') as f:
        data = json.load(f)
        i = data['data'][0]['attributes'][0]['name']
        names.append((file_name, i))

# Output the extracted name values with their corresponding file names
with open('answers.csv', 'w', newline='') as f:
    writer = csv.writer(f)
    writer.writerow(['File Name', 'Name'])
    for name in names:
        writer.writerow([name[0], name[1]])
