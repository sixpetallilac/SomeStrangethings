package Work;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class WordContain {

    public void Determine(){
        try (RandomAccessFile file = new RandomAccessFile(new File("g:/iotest/a.txt"),"r")){
            //input:
            Scanner sc = new Scanner(System.in);
            System.out.println("input:");
            String input = sc.nextLine();
            //line&content recording
            int record = 1;
            String read;

            while ((read = file.readLine())!= null){
                if (read.contains(input)){
                    System.out.println("line:"+record+"  content---"+read);
                }
                record ++;
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {

        WordContain w = new WordContain();
        w.Determine();

    }
}
