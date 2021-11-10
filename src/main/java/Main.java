import java.io.*;

public class Main {
    public static void main(String[] args) throws IOException {
        String str;
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringAnalysator sa = new StringAnalysator();
        System.out.println("Для выхода из программы введите пустое выражение.");
        for (; ; ) {
            System.out.println("Введите выражение: ");
            str = br.readLine();
            if (str.equals(""))
                break;
            try {
                System.out.println("Результат: " + sa.analyse(str));
                System.out.println();
            } catch (Exception exc) {
                System.out.println(exc);
            }
        }
    }
}