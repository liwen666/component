package com.temp.common.base.excel.table;

import com.temp.common.base.excel.SysUser;
import com.temp.common.base.excel.base.ExcelUtil;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import java.io.*;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.alibaba.druid.util.JdbcSqlStatUtils.getData;

/**
 * 后台用户-controller
 *
 * @author libo
 */
public class TableController {
    /**
     * 导出系统用户数据
     *
     * @throws IOException
     */
    public void exportTable() throws IOException, IllegalAccessException {
        SXSSFWorkbook wb = new SXSSFWorkbook(100);//在内存中只保留100行记录,超过100就将之前的存储到磁盘里
        List<SysUser> userList = new ArrayList<SysUser>();
//        TableModel build = TableModel.builder().build();
        String fileName = "教育系统新冠肺炎疫情防控期教职工及学生入校信息采集表样（四个）20200207";
        byte[] content = null;
        try {
            List<TableModel> models = new ArrayList<>();
            getDatas(models);
            //填充projects数据
            ByteArrayOutputStream os = null;
            for (TableModel tableModel:models) {
                List<Map<String, Object>> list = createModel(tableModel);
                os = new ByteArrayOutputStream();
                ExcelUtil.createNewSheet(list, wb);
            }

            wb.write(os);
            content = os.toByteArray();
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        InputStream is = new ByteArrayInputStream(content);
        File f = new File("D:\\idea2018workspace\\component_new\\coding_tx\\src\\main\\java\\com\\temp\\common\\base\\excel\\table\\" + fileName + ".xlsx");
        if (f.exists()) {
            f.createNewFile();
        }
        FileOutputStream fo = new FileOutputStream(f);
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        try {
            bis = new BufferedInputStream(is);
            bos = new BufferedOutputStream(fo);
            byte[] buff = new byte[2048];
            int bytesRead;
            // Simple read/write loop.
            while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
                bos.write(buff, 0, bytesRead);
            }
        } catch (final IOException e) {
            throw e;
        } finally {

            if (bis != null)
                bis.close();

            if (bos != null)
                bos.close();
            if (is != null)
                is.close();
            if (fo != null)
                fo.close();
        }
    }

    private void getDatas(List<TableModel> models) {
        TableModel tableModel = new TableModel();
        models.add(tableModel);
        Map<String, String[]> dataValue = getDataValue();
        for(String str:dataValue.keySet()){
            String[] vals = dataValue.get(str);
            TableModel model= new TableModel();
            String[] line4 = model.getLine4();
            line4[0]=vals[0];
            line4[1]=vals[1];
            line4[2]=vals[2];
            line4[3]=vals[3];
            line4[4]=vals[4];
            String[] line10 = model.getLine10();
            line10[1]=vals[0];
            try {
                line10[3]=line10[3]+"  "+vals[7];
            } catch (Exception e) {
            }

            String[] line6 = model.getLine6();
            try {
                line6[2]=vals[5];
            } catch (Exception e) {
            }

            String[] line7 = model.getLine7();
            try {
                line7[2]=vals[6];
            } catch (Exception e) {
            }

            String[] line8 = model.getLine8();
            try {
                line8[2]=vals[8];
            } catch (Exception e) {
            }

            models.add(model);
        }

    }

    public Map<String,String[]> getDataValue(){
        Map<String,String[]>data = new HashMap<>();
        data.put("黄青源",new String[]{"黄青源","男","411526200911130119","王楼村","15737693161"});
        data.put("黄语欣",new String[]{"黄语欣","女","411526200910030044","潢川县，双柳树镇，初中部后面","17337601565","1月19号经过武汉转乘，自我隔离近20天"});
        data.put("潘子棋",new String[]{"潘子棋","男","411526201003310194","双柳树镇,付营小队","15738462532"});
        data.put("杨世博",new String[]{"杨世博","男","411526201006170457","双柳树镇，曙光青石板","18037478697"});
        data.put("杨佳新",new String[]{"杨佳新","女","411526200911170225","双柳树镇，曙光村，南楼组","13295993478"});
        data.put("曹籽彦",new String[]{"曹籽彦","女","411526200910080084","双柳树镇，张营村","18568277689"});
        data.put("张华乐",new String[]{"张华乐","男","411526201006240259","江集镇，姚楼村，张营组","13673469303"});
        data.put("林正松",new String[]{"林正松","男","411526200910270291","江集镇，姚楼村，更楼组","13673487275"});
        data.put("杨智键",new String[]{"杨智键","男","41152620101027015x","江集镇，姚楼村，马楼组","13303977765"});
        data.put("黄丹丹",new String[]{"黄丹丹","女","411526201001020062","双柳树镇，张营村","17737039585"});
        data.put("熊晨雪",new String[]{"熊晨雪","女","411526201003060260","双柳树镇，敬老院大门西侧","17633782316"});
        data.put("刘音乐",new String[]{"刘音乐","女","411526200910270291","双柳树镇，郑岗村岳游坊组","17395867256"});
        data.put("宋梦瑶",new String[]{"宋梦瑶","女","411526200908270380","双柳树镇，曙光村，东大湾组","18337615257","居家","爷爷，奶奶，爸爸，妈妈，弟弟，妹妹","张军玲"});
        data.put("胡紊瑞",new String[]{"胡紊瑞","男","411526201007150511","双柳树镇，长兴路","18625060051","一月23日，到达江苏省徐州市，现一直居住在此地，无外出","爷爷，奶奶，妈妈,妹妹","舒贤"});
        data.put("胡仁杰",new String[]{"胡仁杰","男","411526201004020236","双柳树镇，曙光村，足岗组","15690837712","居家","爷爷，奶奶，妈妈,妹妹，爸爸","郎倩","妹妹在25号感冒发烧，诊所输液4天病愈，其他亲属一切正常"});
        return data;
    }

    private List<Map<String, Object>> createModel(TableModel build) throws IllegalAccessException {
        List<Map<String, Object>> listmap = new ArrayList<Map<String, Object>>();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("sheetName", build.getLine4()[0]);
        listmap.add(map);
        Field[] declaredFields = build.getClass().getDeclaredFields();
        for (Field lineValue : declaredFields) {
            if ("log".equals(lineValue.getName())) {
                continue;
            }
            lineValue.setAccessible(true);
            String[] row = (String[]) lineValue.get(build);
            Map<String, Object> mapValue = new HashMap<String, Object>();
            mapValue.put(lineValue.getName(), row);
            listmap.add(mapValue);
        }
        return listmap;
    }

    public static void main(String[] args) throws IOException {
        TableController tableController = new TableController();
        try {
            tableController.exportTable();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }


}