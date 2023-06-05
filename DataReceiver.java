import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.net.Socket;
import java.util.Arrays;

public class DataReceiver {
    private static final int PORT = 5555;  // 서버와 동일한 포트 번호를 사용
    private static Boolean isConnect = false;
    private static String csvFilePath = "C:/Androidstudio/SignalUsProject-seongjun_network/point.csv";

    public static void main(String[] args) {
        try {
            // 지역 호스트에 연결
            Socket clientSocket = new Socket("172.30.1.51", PORT);
            DataInputStream dis = new DataInputStream(clientSocket.getInputStream());
            isConnect = true;

            while (isConnect) {
                Thread.sleep(200);
                // 배열의 길이를 받아옵니다
                int length = dis.readInt();
                float[] coords = new float[length];

                // 배열 값을 받아옵니다
                for (int i = 0; i < length; i++) {
                    coords[i] = dis.readFloat();
                }

                // 받아온 값이 종료를 알리는 값이면 연결을 종료합니다.
                if (coords[0] == 99) { // 0.999999로 가정
                    dis.close();
                    clientSocket.close();
                    System.out.println("연결이 종료되었습니다.");
                    return;
                }

                float[] leftHandX = new float[21]; // 왼손 X 좌표 배열
                float[] leftHandY = new float[21]; // 왼손 Y 좌표 배열
                float[] rightHandX = new float[21]; // 오른손 X 좌표 배열
                float[] rightHandY = new float[21]; // 오른손 Y 좌표 배열

                // 주어진 1차원 배열에서 왼손과 오른손의 X, Y 좌표 값을 추출하여 각각의 배열에 저장
                System.arraycopy(coords, 0, rightHandX, 0, 21);
                System.arraycopy(coords, 21, rightHandY, 0, 21);
                System.arraycopy(coords, 42, leftHandX, 0, 21);
                System.arraycopy(coords, 63, leftHandY, 0, 21);

                // 변환된 값들을 저장할 변수들
                float[] result = new float[84];
                int index = 0;

                // 왼손 X, Y 좌표 순서대로 저장
                for (int i = 0; i < 21; i++) {
                    result[index++] = leftHandX[i];
                    result[index++] = leftHandY[i];
                }

                // 오른손 X, Y 좌표 순서대로 저장
                for (int i = 0; i < 21; i++) {
                    result[index++] = rightHandX[i];
                    result[index++] = rightHandY[i];
                }

                // 받아온 값을 csv 파일에 추가합니다.
                writeCSV(csvFilePath, result);

                // 수신한 배열 길이와, 좌표 배열를 출력합니다
                System.out.println("수신한 배열 길이: " + length);
                System.out.println("수신한 좌표 배열 값: " + Arrays.toString(result));
            }
        } catch (Exception e) {
            System.out.println("Error while receiving data: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void writeCSV(String filePath, float[] data) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true)); // 파일을 추가 모드로 엽니다.
            StringBuilder row = new StringBuilder();
            
            for (int i = 0; i < data.length; i++) {
                row.append(data[i]);
                if (i != data.length - 1) {
                    row.append(",");
                }
            }
            row.append("\n");
            writer.write(row.toString());
            writer.close();
            System.out.println("데이터가 성공적으로 CSV 파일에 추가되었습니다.");
        } catch (IOException e) {
            System.out.println("CSV 파일 저장 중 오류가 발생했습니다: " + e.getMessage());
            e.printStackTrace();
        }
    }
}