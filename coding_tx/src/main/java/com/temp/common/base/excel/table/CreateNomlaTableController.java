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

/**
 * 后台用户-controller
 *
 * @author libo
 */
public class CreateNomlaTableController {
    /**
     * 导出系统用户数据
     *
     * @throws IOException
     */
    public void exportTable() throws IOException, IllegalAccessException {
        SXSSFWorkbook wb = new SXSSFWorkbook(100);//在内存中只保留100行记录,超过100就将之前的存储到磁盘里
//        TableModel build = TableModel.builder().build();
//        String fileName = "教育系统新冠肺炎疫情防控期教职工及学生入校信息采集表样（四个）20200207";
        String fileName = "学生信息汇总简表";
        byte[] content = null;
        try {
            List<NomalTableModel> models = new ArrayList<>();
            getDatas(models);
            //填充projects数据
            ByteArrayOutputStream os = null;
                os = new ByteArrayOutputStream();
                ExcelUtil.createNomalTable(models, wb);

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

    private void getDatas(List<NomalTableModel> models) {
        NomalTableModel title = new NomalTableModel();
        models.add(title);
        Map<String, String[]> dataValue = getDataValue();
        int id = 1;
        for(String str:dataValue.keySet()){
            NomalTableModel nomalTableModel = new NomalTableModel();
            String[] vals = dataValue.get(str);
            nomalTableModel.setId(id+"");
            nomalTableModel.setName(vals[0]);
//            nomalTableModel.setGender(vals[1]);
            nomalTableModel.setIdcard(vals[2]);
//            nomalTableModel.setAddress(vals[3]);
            nomalTableModel.setMobile(vals[4]);
            nomalTableModel.setLeave("四年级");
            nomalTableModel.setStuclass("一班");
            nomalTableModel.setStatus("正常");
            nomalTableModel.setCheck(" ");
            nomalTableModel.setAdvince(" ");
            nomalTableModel.setSchoole(null);
            models.add(nomalTableModel);
            id++;
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


    public static void main(String[] args) throws IOException {
        CreateNomlaTableController tableController = new CreateNomlaTableController();
        try {
            tableController.exportTable();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }


}