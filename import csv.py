import csv

def append_to_csv(self,file_path, data):
        with open(file_path, 'a', newline='') as csv_file:
            writer = csv.writer(csv_file)
            writer.writerow(data)
    def delete_row_from_csv(file_path, target_row):
        with open(file_path, 'r') as csv_file:
            reader = csv.reader(csv_file)
            rows = list(reader)        
        with open(file_path, 'w', newline='') as csv_file:
            writer = csv.writer(csv_file)
            for row in rows:
                if row != target_row:
                    writer.writerow(row)
    def update_csv(self,file_path, target_row, column_index, new_value):
        with open(file_path, 'r') as csv_file:
            reader = csv.reader(csv_file)
            rows = list(reader)
            for i, row in enumerate(rows):
                if row == target_row:
                    rows[i][column_index] = new_value
        self.delete_row_from_csv(file_path, target_row)        


        with open(file_path, 'w', newline='') as csv_file:
            writer = csv.writer(csv_file)
            writer.writerows(rows)

            
    def get_most_recent_row(self,file_path):
        with open(file_path, 'r') as csv_file:
            reader = csv.reader(csv_file)
            rows = list(reader)
            most_recent_row = rows[-1] if rows else None
        return most_recent_row
    def changeData(self,strdata):
        cleaned_data = ''
        backslash_found = False
        backslash="\x00"

        for char in strdata:
            if char == backslash and not backslash_found:
                backslash_found = True
                continue
            elif char!=',' and backslash_found:
                continue
            elif char == ',' and backslash_found:
                cleaned_data += char                
                backslash_found = False
                continue
            elif char !=','and not backslash_found:
                cleaned_data += char
            elif char =='끝':
                socket.close()
                self.socketOpen()
                self.receiveThread = threading.Thread(target=self.receivelandmarks)
                self.receiveThread.start()


        return cleaned_data   
    def attachrow(self,dataForAdd):
        target_row=self.get_most_recent_row('point.csv')
                        
        new_row = target_row[:]  # Make a copy of the target row기존 행 가져오기
                        
        new_row.append(dataForAdd)  # Add '090' to the new row 새로운 행에 추가할 데이터 넣기->왜 필요한 과정인지?
                        
        self.append_to_csv('point.csv', new_row) # Update the value in the column following the target row 값을 업데이트. 기존 행
                        
        column_index = len(target_row)  # Assuming the new value should be added as the last column
                        
        new_value = dataForAdd
                        
        self.update_csv('point.csv',target_row,column_index,new_value) 

# Example usage
file_path = 'point.csv'

# Retrieve the target row
target_row = ['Kanadara', '12345']

# Append a new row with additional column value
new_row = target_row[:]  # Make a copy of the target row
new_row.append('090')  # Add '090' to the new row

# Append the new row to the CSV file
append_to_csv(file_path, new_row)

# Update the value in the column following the target row
column_index = len(target_row)  # Assuming the new value should be added as the last column
new_value = '090'

# Update the CSV file with the new value in the specified column
update_csv(file_path, target_row, column_index, new_value)
