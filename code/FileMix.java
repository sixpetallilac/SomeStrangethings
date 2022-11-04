package Work;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.RandomAccessFile;
import java.time.format.DateTimeFormatterBuilder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;

public class FileMix {
    public static void main(String[] args) {

        try(RandomAccessFile file_a = new RandomAccessFile(new File("g:/iotest/a.txt"),"rw");
            RandomAccessFile file_b = new RandomAccessFile(new File("g:/iotest/b.txt"),"r")){
            //ab分别写入数组
            byte[] arr1 = new byte[(int) file_a.length()];
            for (int i = 0;i<file_a.length();i++){
                file_a.read(arr1);
            }
            System.out.println(Arrays.toString(arr1));

            byte[] arr2 = new byte[(int) file_b.length()];
            for (int i = 0;i<file_b.length();i++){
                file_b.read(arr2);
            }
            System.out.println(Arrays.toString(arr2));

            //ab合并到新数组准备写入,以asc13对应做分割
            // +8避免光标在下一行造成数组越界
            byte[] arr3 = new byte[(int) (file_a.length()+file_b.length()+8)];
            //record记录对应文件数组下标
            int record1 = 0;
            int record2 = 0;
            //boolean交替循环记录
            boolean rec = false;
            for (int i = 0;i < arr3.length;i++){
                if (rec == false && record1 < arr1.length && arr1[record1] != 10 ){
                    arr3[i] = arr1[record1];
                    record1 ++;
                }else if (rec == false && record1 < arr1.length && arr1[record1] == 10){
                    arr3[i] = 10;
                    record1 ++;
                    rec = true;
                    continue;
                }
                if (record1 == arr1.length){
                    arr3[++i] = 13;
                    arr3[++i] = 10;
                    record1 ++;
                    rec = true;
                    continue;
                }

                if (rec == true && record2 < arr2.length && arr2[record2] !=10){
                    arr3[i] = arr2[record2];
                    record2 ++;
                }else if (rec == true && record2 < arr2.length && arr2[record2] == 10){
                    arr3[i] = 10;
                    record2 ++;
                    rec = false;
                    continue;
                }
                if (record2 == arr2.length){
                    arr3[++i] = 13;
                    arr3[++i] = 10;
                    record2 ++;
                    rec = false;
                    continue;
                }
            }
            System.out.println(Arrays.toString(arr3));
            //write
            file_a.seek(0);
            for (int i = 0;i<arr3.length;i++){
                file_a.write(arr3[i]);
            }
            System.out.println("done");

        }
        catch (Exception e){
            e.printStackTrace();
        }

    }
}
