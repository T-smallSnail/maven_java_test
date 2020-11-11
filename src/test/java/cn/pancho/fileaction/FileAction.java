package cn.pancho.fileaction;

import cn.pancho.redis.JedisClientDev;
import com.bmcc.vgop.common.utils.file.FileOperationUtils;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;
import redis.clients.jedis.Jedis;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;

/**
 * @author ：pancho
 * @date ：Created in 2019/11/22 15:58
 * @description :
 */
public class FileAction {


    /**
     * 读取本地txt文件
     *
     * @return : void
     * @author : pancho
     * @date : 2019/11/25 10:27
     */
    @Test
    public void readLinetest() throws IOException {

        //文件输入流
        FileInputStream in = new FileInputStream("C:\\Users\\PingTianMa\\Desktop\\test1.txt");

        //FileInputStream in = new FileInputStream("C:\\Users\\PingTianMa\\Desktop\\首页首屏预览功能素材\\首页首屏预览功能素材\\改版icon_14.jpg");

        //读取文件内容
        InputStreamReader reader = null;
        FileOperationUtils consumerUtils = null;
        try {
            //获取item中的上传文件的输入流
            //建立一个输入流对象reader
            reader = new InputStreamReader(in, "GBK");
            // 建立一个BufferedReader对象
            BufferedReader br = new BufferedReader(reader);

            String line = null;
            StringBuffer phoneLine = new StringBuffer();
            Set<String> set = new HashSet<String>();
            int i = 0;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 读取本地excel文件，
     * 本将数据导入redis中
     *
     * @return : void
     * @author : pancho
     * @date : 2019/9/19 17:13
     */
    @Test
    public void addSerialCodeToRedis() throws IOException {

        JedisClientDev JedisClient = new JedisClientDev();
        Jedis jedis = null;
        jedis.select(6);

        FileInputStream in = new FileInputStream("C:\\Users\\PingTianMa\\Desktop\\0元卡券串码.xlsx");//文件输入流
        //POIFSFileSystem fs = new POIFSFileSystem(in);//构建poifsfileSystem对象，根据输入流
        XSSFWorkbook wb = new XSSFWorkbook(in);//xlsx

        //读取公式单元格的值

        int j = 1;
        try {
            jedis = JedisClientDev.getJedis();
            for (; j < 6; j++) {
                XSSFSheet sheet = wb.getSheetAt(j);//读取序号为1的sheet（第二张sheet）

                //取得最后一行的行号
                //int rowNum = sheet.getLastRowNum() + 1;
                //每行的最后一个单元格位置
                //int cellNum = row.getLastCellNum();

                XSSFRow row = null;
                XSSFCell idCell = null;
                XSSFCell codeell = null;
                Double numericCellValue;
                int id;
                String serialCode = null;
                int i = 1;

                do {
                    row = sheet.getRow(i++);//取行（0开始）
                    if (row == null) break;

                    idCell = row.getCell((short) 0);//第1格（0开始）
                    codeell = row.getCell((short) 1);//第1格（0开始）id

                    numericCellValue = idCell.getNumericCellValue();
                    id = numericCellValue.intValue();
                    serialCode = codeell.getStringCellValue();

                    jedis.lpush("usergrowth:serialcode:proid_" + id, serialCode);

                } while (row != null);
            }

        } catch (Exception e) {
            //System.out.println("sheet"+j+"==row"+i);
            e.printStackTrace();
        } finally {
            JedisClientDev.returnJedis(jedis);
        }
    }


    /**
     * 新建文件或者追加文件内容
     * @author : pancho
     * @date : 2020/8/19 19:10
     * @return : void
     */
    @Test
    public void addFile() throws IOException {

        BufferedWriter writer = new BufferedWriter(new FileWriter("C:\\Users\\PingTianMa\\Desktop\\log\\99.txt",true));

        writer.write("999");
        writer.write("\n");
        writer.write("jjj");
        writer.write("\n");

        writer.flush();
        writer.close();


    }

}
