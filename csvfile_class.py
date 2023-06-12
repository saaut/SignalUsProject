import pandas as pd
import os

# Set the directory path
folder_path = '/Users/gyuri/Documents/GitHub/SignalUsProject/csvFrom01/'

# Get all CSV files within the directory
csv_files = [os.path.join(folder_path, file) for file in os.listdir(folder_path) if file.endswith('.csv')]

# Loop through each CSV file
for csv_file in csv_files:
    # Read the CSV file
    data = pd.read_csv(csv_file)

    # Define the columns to be divided
    columns_to_divide = ['left_hand_0_x', 'left_hand_0_y', 'left_hand_1_x', 'left_hand_1_y', 'left_hand_2_x',
                         'left_hand_2_y', 'left_hand_3_x', 'left_hand_3_y', 'left_hand_4_x', 'left_hand_4_y',
                         'left_hand_5_x', 'left_hand_5_y', 'left_hand_6_x', 'left_hand_6_y', 'left_hand_7_x',
                         'left_hand_7_y', 'left_hand_8_x', 'left_hand_8_y', 'left_hand_9_x', 'left_hand_9_y',
                         'left_hand_10_x', 'left_hand_10_y', 'left_hand_11_x', 'left_hand_11_y', 'left_hand_12_x',
                         'left_hand_12_y', 'left_hand_13_x', 'left_hand_13_y', 'left_hand_14_x', 'left_hand_14_y',
                         'left_hand_15_x', 'left_hand_15_y', 'left_hand_16_x', 'left_hand_16_y', 'left_hand_17_x',
                         'left_hand_17_y', 'left_hand_18_x', 'left_hand_18_y', 'left_hand_19_x', 'left_hand_19_y',
                         'left_hand_20_x', 'left_hand_20_y', 'right_hand_0_x', 'right_hand_0_y', 'right_hand_1_x',
                         'right_hand_1_y', 'right_hand_2_x', 'right_hand_2_y', 'right_hand_3_x', 'right_hand_3_y',
                         'right_hand_4_x', 'right_hand_4_y', 'right_hand_5_x', 'right_hand_5_y', 'right_hand_6_x',
                         'right_hand_6_y', 'right_hand_7_x', 'right_hand_7_y', 'right_hand_8_x', 'right_hand_8_y',
                         'right_hand_9_x', 'right_hand_9_y', 'right_hand_10_x', 'right_hand_10_y', 'right_hand_11_x',
                         'right_hand_11_y', 'right_hand_12_x', 'right_hand_12_y', 'right_hand_13_x', 'right_hand_13_y',
                         'right_hand_14_x', 'right_hand_14_y', 'right_hand_15_x', 'right_hand_15_y', 'right_hand_16_x',
                         'right_hand_16_y', 'right_hand_17_x', 'right_hand_17_y', 'right_hand_18_x', 'right_hand_18_y',
                         'right_hand_19_x', 'right_hand_19_y', 'right_hand_20_x', 'right_hand_20_y']
    
    # Divide the x values by 1920
    data[columns_to_divide[::2]] = data[columns_to_divide[::2]].div(1920)

    # Divide the y values by 1080
    data[columns_to_divide[1::2]] = data[columns_to_divide[1::2]].div(1080)


    # Get the file name without extension
    file_name = os.path.splitext(os.path.basename(csv_file))[0]

    # Define the new file path for saving
    new_file_path = os.path.join(folder_path, f'{file_name}_updated.csv')

    # Save the updated data to a new CSV file
    data.to_csv(new_file_path, index=False)

    # Print the confirmation message
    print(f"Updated data saved to {new_file_path}")
