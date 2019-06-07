//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.temp.common.common.util;

import com.sun.crypto.provider.SunJCE;
import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.AccessController;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.Security;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;
import sun.security.action.GetPropertyAction;

public class Encrypt {
    private String Algorithm = "Blowfish";
    private SecretKey deskey;
    private Cipher cipher;

    public Encrypt() {
        this.init();
    }

    private void init() {
        Security.addProvider(new SunJCE());
        this.deskey = new SecretKeySpec("hzg-soft".getBytes(), this.Algorithm);

        try {
            this.cipher = Cipher.getInstance(this.Algorithm);
        } catch (NoSuchAlgorithmException var2) {
            throw new RuntimeException("没有此加密算法，加密器初始化失败", var2);
        } catch (NoSuchPaddingException var3) {
            throw new RuntimeException("加密器初始化失败", var3);
        }
    }

    private byte[] createEncryptor(byte[] datasource) {
        byte[] encryptorData = (byte[])null;

        try {
            this.cipher.init(1, this.deskey);
            encryptorData = this.cipher.doFinal(datasource);
            return encryptorData;
        } catch (InvalidKeyException var4) {
            throw new RuntimeException("非法的加密密匙，加密失败", var4);
        } catch (BadPaddingException var5) {
            throw new RuntimeException("非法的加密数据，加密失败", var5);
        } catch (IllegalBlockSizeException var6) {
            throw new RuntimeException("加密字符串字节数不对，加密失败", var6);
        }
    }

    public String createEncryptor(String datasource) {
        try {
            byte[] encryptorData = this.createEncryptor(datasource.getBytes());
            String enc = (String)AccessController.doPrivileged(new GetPropertyAction("file.encoding"));
            return URLEncoder.encode((new BASE64Encoder()).encode(encryptorData), enc);
        } catch (Exception var4) {
            var4.printStackTrace();
            throw new RuntimeException("加密失败");
        }
    }

    private byte[] createDecryptor(byte[] datasource) {
        byte[] decryptorData = (byte[])null;

        try {
            this.cipher.init(2, this.deskey);
            decryptorData = this.cipher.doFinal(datasource);
            return decryptorData;
        } catch (InvalidKeyException var4) {
            throw new RuntimeException("非法的解密密匙，解密失败", var4);
        } catch (BadPaddingException var5) {
            throw new RuntimeException("非法的解密数据，解密失败", var5);
        } catch (IllegalBlockSizeException var6) {
            throw new RuntimeException("解密字符串字节数不对，解密失败", var6);
        }
    }

    public String createDecryptor(String datasource) {
        datasource = this.urlEncoder(datasource);
        byte[] decryptorData = new byte[datasource.length()];

        try {
            decryptorData = (new BASE64Decoder()).decodeBuffer(datasource);
        } catch (IOException var4) {
            throw new RuntimeException("字符串Base64解码失败", var4);
        }

        return new String(this.createDecryptor(decryptorData));
    }

    private String urlEncoder(String datasource) {
        if (datasource.indexOf(37) < 0) {
            return datasource;
        } else {
            try {
                String enc = (String)AccessController.doPrivileged(new GetPropertyAction("file.encoding"));
                return this.urlEncoder(URLDecoder.decode(datasource, enc));
            } catch (Exception var3) {
                var3.printStackTrace();
                throw new RuntimeException("解密失败！");
            }
        }
    }
}
