package com.poputar.krc2prc.tool;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.zip.DataFormatException;
import java.util.zip.Inflater;

/**
 * <b> </b>
 * <p>
 * 功能:
 * </p>
 * 
 * @作者 stone
 * @创建时间 2016年2月27日 下午6:12:05
 * @修改内容
 * @修改时间
 */
public class DecodeKrcTool {
	private static final char key[] = { '@', 'G', 'a', 'w', '^', '2', 't', 'G', 'Q', '6', '1', '-', '\316', '\322','n', 'i' };
	
	/* 
     * 参数：文件名 函数作用：解密转换 
     */  
    public static String convert(String fileName) throws Exception {  
        RandomAccessFile raf = new RandomAccessFile(fileName, "r");  
        byte[] content = new byte[(int) (raf.length() - 4)];  
        raf.skipBytes(4);  
        raf.read(content);  
        raf.close();  
  
        for (int i = 0, length = content.length; i < length; i++) {  
            int j = i % 16; // 循环异或解密  
            content[i] ^= key[j];  
        }  
  
        String lrc = null;  
  
        lrc = new String(decompress(content), "utf-8"); // 解压为 utf8  
        
        return lrc;
    }  
  
    /* 
     * 解压 
     */  
    private static byte[] decompress(byte[] data) throws Exception {  
        byte[] output = new byte[0];  
        Inflater decompresser = new Inflater();  
        decompresser.reset();  
        decompresser.setInput(data);  
        ByteArrayOutputStream o = new ByteArrayOutputStream(data.length);  
        byte[] buf = new byte[1024];  
        while (!decompresser.finished()) {  
            int i = decompresser.inflate(buf);  
            o.write(buf, 0, i);  
        }  
        output = o.toByteArray();  
        o.close();  
        decompresser.end();  
        return output;  
    }  
	
}
