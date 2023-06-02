import java.io.DataInputStream;
import java.net.Socket;
import java.util.Arrays;

public class DataReceiver {
    private static final int PORT = 5555;  // 서버와 동일한 포트 번호를 사용

    public static void main(String[] args) {
        try {
            // 지역 호스트에 연결
            Socket clientSocket = new Socket("192.168.45.55", PORT);
            DataInputStream dis = new DataInputStream(clientSocket.getInputStream());
            Thread.sleep(200);
            // 배열의 길이를 받아옵니다
            int length = dis.readInt();
            float[] coords = new float[length];

            // 배열 값을 받아옵니다
            for (int i = 0; i < length; i++) {
                coords[i] = dis.readFloat();
            }

            // 수신한 배열 길이와, 좌표 배열를 출력합니다
            System.out.println("수신한 배열 길이: " + String.valueOf(length));
            System.out.println("수신한 좌표 배열 값: " + Arrays.toString(coords));

            // // 연결을 닫습니다
            // dis.close();
            // clientSocket.close();
        } catch (Exception e) {
            System.out.println("Error while receiving data: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
