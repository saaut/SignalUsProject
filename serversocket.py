import socket
import numpy
import base64
import time
import threading
from datetime import datetime
import re

class ServerSocket:
    

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
    
    def changeData(self,strdata):
        cleaned_data = ''
        backslash_found = False
        backslash="\x00"

        for char in strdata:
            if char == backslash and not backslash_found:
                backslash_found = True
                continue
            elif char!='&' and backslash_found:
                continue
            elif char == '&' and backslash_found:
                cleaned_data += char                
                backslash_found = False
                continue
            elif char !='&'and not backslash_found:
                cleaned_data += char
            elif char =='끝':
                socket.close()
                self.socketOpen()
                self.receiveThread = threading.Thread(target=self.receivelandmarks)
                self.receiveThread.start()


        return cleaned_data   

    def receivelandmarks(self):#클라이언트로부터 지속적으로 데이터를 수신한다. 지속적으로 데이터를 보내는 것도 가능?  

        try:         
            while(True):#어떨 때 0 0 0 0 이고, 어떨 때 정상 종료인가?
                #time.sleep(1)
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
                SplitedData=cleanedstrdata.split('&')#출력과 실제 데이터가 다름
                i=0
                count=(int)(len(SplitedData)/4)
                while(count!= 1 and i<count):
                    x.append(SplitedData[1+i])                    
                    y.append(SplitedData[2+i])
                    leftx.append(SplitedData[3+i])
                    lefty.append(SplitedData[4+i])

                    print("rightx"+str(i+1)+" : "+x[i]+"\n")
                    print("righty"+str(i+1)+" : "+y[i]+"\n")
                    print("leftx"+str(i+1)+" : "+leftx[i]+"\n")
                    print("lefty"+str(i+1)+" : "+lefty[i]+"\n")
                    i+=1
                
                if(data):#받은 데이터 값이 정상이면
                    data2 =1 #bytes(strdata[2:5],encoding="utf-8")#그에 대응하는 값을 보낸다.->모델링 테스트 결과가 들어가야함. 문자열은 byte 코드로 바꿔서!
                    # 값하나 보냄(사용자가 입력한 숫자)
                    self.conn.send(data2.to_bytes(4, byteorder='little'))
                    print("보냄")#받아온 데이터 값 출력보다 보내는 게 빠름
                           
        except Exception as e:
            print(e)
            self.socketClose()
            self.socketOpen()
            self.receiveThread = threading.Thread(target=self.receivelandmarks)
            self.receiveThread.start()
    

      


def main():
    server = ServerSocket('172.30.1.62', 3000)

if __name__ == "__main__":
    main()