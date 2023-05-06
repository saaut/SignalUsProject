import csv
import glob
import json
import os
import re

# An empty list to store the extracted values in
names = []

# Path to the directory where the JSON file is located
directory_path = '/Users/kushiro/Downloads/morpheme/01'

# Find all JSON files in a directory that contain "_D_" in their name
file_paths = glob.glob(directory_path + '/*_D_*.json')

# Define a regular expression pattern to extract the 4-digit number after "WORD"
pattern = r'WORD(\d{4})'

# Iterate through the files, extract the name value and add it to the list
for file_path in file_paths:
    file_name = os.path.basename(file_path)
    # Extract the number from the file name using the regular expression pattern
    number = int(re.search(pattern, file_name).group(1))
    with open(file_path, 'r') as f:
        data = json.load(f)
        i = data['data'][0]['attributes'][0]['name']
        names.append((number, file_name, i))

# Sort the names list based on the extracted number
names = sorted(names, key=lambda x: x[0])

# Output the extracted name values with their corresponding file names
with open('answers_onlyD.csv', 'w', newline='') as f:
    writer = csv.writer(f)
    writer.writerow(['File Name', 'Name'])
    for name in names:
        writer.writerow([name[1], name[2]])
