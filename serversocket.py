import socket
import threading
import csv
import time

class ServerSocket:
    filename = 'point.csv'
    def __init__(self, ip, port):#생성자의 TCP IP 주소와 포트 초기화
        self.TCP_IP = ip
        self.TCP_PORT = port
        self.socketOpen()
        self.receiveThread = threading.Thread(target=self.receivelandmarks)
        self.receiveThread.start()

    def socketClose(self):
        self.sock.close()
        print(u'Server socket [ TCP_IP: ' + self.TCP_IP + ', TCP_PORT: ' + str(self.TCP_PORT) + ' ] is close')

    def socketOpen(self):
        self.sock = socket.socket(socket.AF_INET, socket.SOCK_STREAM)#소켓 생성
        self.sock.bind((self.TCP_IP, self.TCP_PORT))#지정된 ip,포트에 바인딩함
        self.sock.listen(1)#연결 수신 시작
        print(u'Server socket [ TCP_IP: ' + self.TCP_IP + ', TCP_PORT: ' + str(self.TCP_PORT) + ' ] is open')
        self.conn, self.addr = self.sock.accept()
        print(u'Server socket [ TCP_IP: ' + self.TCP_IP + ', TCP_PORT: ' + str(self.TCP_PORT) + ' ] is connected with client')
    
    
    def append_to_csv(self,file_path, data):
        with open(file_path, 'a', newline='') as csv_file:
            writer = csv.writer(csv_file)
            writer.writerow(data)
            
    def delete_row_from_csv(self,file_path, target_row):
        rows = []

        with open(file_path, 'r') as csv_file:
            reader = csv.reader(csv_file)
            rows = [row for row in reader if row != target_row]

        with open(file_path, 'w', newline='') as csv_file:
            writer = csv.writer(csv_file)
            writer.writerows(rows)
                    
    def update_csv(self,file_path, target_row, column_index, new_value):
        with open(file_path, 'r') as csv_file:
            reader = csv.reader(csv_file)
            rows = list(reader)
            for i, row in enumerate(rows):
                if row == target_row:
                    rows[i][column_index-1] = new_value

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
    def attachrow(self,dataForAdd,):
        target_row=self.get_most_recent_row('point.csv')
                        
        new_row = target_row[:]  # Make a copy of the target row기존 행 가져오기
        
            #기존 행 길이 체크.                       
        column_index = len(target_row)  # Assuming the new value should be added as the last column
        if(column_index>=84):#기존 행이 84이거나 더 클 경우 새로운 행에 작성
            with open('point.csv', 'a', newline='') as file:
                writer = csv.writer(file)
                writer.writerow([dataForAdd])
                return
            
        new_row.append(dataForAdd)
                        
        self.append_to_csv('point.csv', new_row) # Update the value in the column following the target row 값을 업데이트. 기존 행
        

        new_value = dataForAdd
        self.delete_row_from_csv('point.csv', target_row)                
        #self.update_csv('point.csv',target_row,column_index,new_value)     
             
        
    def receivelandmarks(self):#클라이언트로부터 지속적으로 데이터를 수신한다.  
        isFileOpened=0

        try:         
            while(True):
                time.sleep(0.2)
                x=[]
                y=[]
                leftx=[]
                lefty=[]
                data=''
                strdata=''
                SplitedData=''
                data = self.conn.recv(1024)
                if(data==0):
                    break
                strdata=data.decode("utf-8")
                cleanedstrdata=self.changeData(strdata)
                                               
                print(cleanedstrdata)
                SplitedData=cleanedstrdata.split(',')#출력과 실제 데이터가 다름
                
                count=(int)(len(SplitedData)/4)+1
                with open('point.csv', 'a', newline='') as file:
                    writer = csv.writer(file)
                    
                    if file.tell() == 0:
                        # Write column headers
                        writer.writerow(['rightX0', 'rightY0', 'rightX1', 'rightY1', 'rightX2', 'rightY2', 'rightX3', 'rightY3', 'rightX4', 'rightY4', 'rightX5', 'rightY5', 'rightX6 ', 'rightY6', 'rightX7', 'rightY7', 'rightX8', 'rightY8', 'rightX9', 'rightY9', 'rightX10', 'rightY10', 'rightX11', 'rightY11', 'rightX12', 'rightY12', 'rightX13', 'rightY13', 'rightX14', 'rightY14', 'rightX15', 'rightY15', 'rightX16', 'rightY16', 'rightX17', 'rightY17', 'rightX18', 'rightY18 ', 'rightX19', 'rightY19', 'rightX20', 'rightY20','leftX0', 'leftY0', 'leftX1', 'leftY1', 'leftX2', 'leftY2', 'leftX3', 'leftY3', 'leftX4', 'leftY4', 'leftX5', 'leftY5', 'leftX6 ', 'leftY6', 'leftX7', 'leftY7', 'leftX8', 'leftY8', 'leftX9', 'leftY9', 'leftX10', 'leftY10', 'leftX11', 'leftY11', 'leftX12', 'leftY12', 'leftX13', 'leftY13', 'leftX14', 'leftY14', 'leftX15', 'leftY15', 'leftX16', 'leftY16', 'leftX17', 'leftY17', 'leftX18', 'leftY18 ', 'leftX19', 'leftY19', 'leftX20', 'leftY20'])
                    
                    if(isFileOpened==0):#서버 연결하고 파일을 처음 열 때는 마지막 row에 빈 공간이 있는 것을 무시한다.  
                            writer.writerow(SplitedData[1:])
                            isFileOpened=1

                    elif(isFileOpened==1):#앞선 row에 빈 칸이 없음을 가정한다.
                    
                        if(count<21):#받아온 데이터가 83개 이하로 끊겼을 경우
                            DataForCheck=SplitedData[0] 
                            if(DataForCheck==''or DataForCheck[:2]!='0.'):#받은 데이터의 맨 앞이 비어 있거나, 0.n으로 시작하지 않으면 그 값은 버린다.
                                for i in SplitedData[1:]:
                                    self.attachrow(i)
                                print("Splited data has been written successfully.")
                            else:
                                for i in SplitedData[1:]:
                                    self.attachrow(i)
                                print("Splited data has been written successfully.")
                            
                        else:#받아온 데이터가 84개로 정상적일 경우
                            target_row=self.get_most_recent_row('point.csv')
                            #기존 행 길이 체크. 84개인가?                                     
                            column_index = len(target_row)
                            DataForCheck=SplitedData[0] 
                            if(column_index>84):
                                print("작성 오류")
                                if(DataForCheck==''or DataForCheck[:2]!='0.'):
                                    writer.writerow(SplitedData[1:])
                                else:
                                    writer.writerow(SplitedData[:])
                            elif(column_index==84):#사실상 이런 경우(안 끊기는 경우)가 없다.
                                if(DataForCheck==''or DataForCheck[:2]!='0.'):
                                    writer.writerow(SplitedData[1:])
                                else:
                                    writer.writerow(SplitedData[:])
                            elif(column_index<84):
                                if(DataForCheck==''or DataForCheck[:2]!='0.'):
                                    for i in SplitedData[1:]:
                                        self.attachrow(i)
                                else:
                                    for i in SplitedData[:]:
                                        self.attachrow(i)
                            print("CSV file has been written successfully.")
                    
                        

  #적절하지 않은 때 기존 길이 무시하는 경우 생김                  
       
                if(data):#받은 데이터 값이 정상이면
                    data2 ="보냄" #bytes(strdata[2:5],encoding="utf-8")#그에 대응하는 값을 보낸다.->모델링 테스트 결과가 들어가야함. 문자열은 byte 코드로 바꿔서!
                    strdata=data2.encode('utf-8')
                    int_data=int.from_bytes(strdata[2:5],byteorder='little')
                    byte_data= int_data.to_bytes(4,byteorder='little')
                    self.conn.send(byte_data)
                    #self.conn.send(data2.to_bytes(4, byteorder='little'))
                    print("보냄")#받아온 데이터 값 출력보다 보내는 게 빠름
                    
                    
        except Exception as e:
            print(e)
            self.socketClose()
            self.socketOpen()
            self.receiveThread = threading.Thread(target=self.receivelandmarks)
            self.receiveThread.start()
    

      


def main():
    server = ServerSocket("172.30.1.39", 3000)

if __name__ == "__main__":
    main()