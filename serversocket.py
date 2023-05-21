import socket
import numpy
import base64
import time
import threading
from datetime import datetime

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

    def receivelandmarks(self):#클라이언트로부터 지속적으로 데이터를 수신한다. 지속적으로 데이터를 보내는 것도 가능?       
        try:
            while():
                data = self.conn.recv(1024)
                print(data.decode("utf-8"), len(data))#받은 데이터 값을 출력한다.
                
                if(data):#받은 데이터 값이 정상이면
                    data2 = 1 #그에 대응하는 값을 보낸다.
                    # 값하나 보냄(사용자가 입력한 숫자)
                    self.conn.send(data2.to_bytes(4, byteorder='little'))
                    print("보냄")

                if(data == 99):
                    break
                      
        except Exception as e:
            print(e)
            self.socketClose()
            self.socketOpen()
            self.receiveThread = threading.Thread(target=self.receivelandmarks)
            self.receiveThread.start()

      


def main():
    server = ServerSocket('172.30.1.51', 3000)

if __name__ == "__main__":
    main()