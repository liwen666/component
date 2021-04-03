package com.temp.common.complier.file;

import java.io.File;

/**
 * <p>
 * 描述
 * </p>
 *
 * @author LW
 * @since 2021/4/1  18:02
 */
public class TestFile {
    public static void main(String[] args) {
        File f  = new File("D:\\workspace\\test_workspace\\coding_tx\\src\\main\\java\\com\\temp\\common\\complier\\file\\jkfdja");
        if(!f.exists()){
            f.mkdir();
        }
    }
}
