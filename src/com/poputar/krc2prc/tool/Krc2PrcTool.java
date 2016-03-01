package com.poputar.krc2prc.tool;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.gson.Gson;
import com.poputar.krc2prc.model.LyricsLine;
import com.poputar.krc2prc.model.Word;
  
public class Krc2PrcTool {  
	
    public static void main(String[] args) {  
    	Krc2PrcTool tool = new Krc2PrcTool();
    	try {
			tool.parseKrc2PrcFile("");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }  
    
    public void parseKrc2PrcFile(String filename) throws Exception {
    	String json = parseKrc2Prc(filename);
    	saveJsonFile(json, filename.replace(".krc", ".prc"));
    }
    
    public String parseKrc2Prc(String filename) throws Exception {
    	String res = "";
    	List<LyricsLine> lyricsLines = new ArrayList<LyricsLine>();
    	String decodeKrcStr;
		try {
			decodeKrcStr = DecodeKrcTool.convert(filename);
			
			String[] krcStrLines = decodeKrcStr.split("\n");
	        
	        Pattern p = Pattern.compile("\\[-??\\d+?,-??\\d+?\\](<-??\\d+?,-??\\d+?,-??\\d+?>[^<]+?)+?");
	        Matcher m = null;
	        for (String l : krcStrLines) {
	        	m = p.matcher(l); 
	        	if (m.matches()) {
	        		//System.out.println("line:" + l);
	        		LyricsLine line = parseKrcLine(l);
	        		if (line != null) {
	        			lyricsLines.add(line);
	        		}
	        	}
	        }
	        
	        if (lyricsLines.size() > 0) {
	        	Gson gson = new Gson();
	        	res = gson.toJson(lyricsLines);
	        }
	        
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(String.format("文件[%s]转换失败!", filename));
		}
    	
		return res;
    }
    
    private LyricsLine parseKrcLine(String lineStr) {
    	LyricsLine line = null;
    	if (lineStr != null && !"".equals(lineStr)) {
    		line = new LyricsLine();
    		
    		String linetimeStr = lineStr.substring(1, lineStr.indexOf("]"));
    		String[] linetime = linetimeStr.split(",");
    		
    		line.setStime(Double.parseDouble(linetime[0]));
    		line.setDuration(Double.parseDouble(linetime[1]));
    		
    		line.setWords(parseKrcLineWords(lineStr.substring(lineStr.indexOf("]")+1)));
    	}
    	
    	return line;
    }
    
    private List<Word> parseKrcLineWords(String lineWordsStr) {
    	List<Word> words = null;
    	if (lineWordsStr != null && !"".equals(lineWordsStr)) {
    		words = new ArrayList<Word>();
    		
    		String[] wordsStr = lineWordsStr.split("<");
    		if (wordsStr != null && wordsStr.length > 0) {
    			for (String ws : wordsStr) {
    				Word w = parseKrcWord(ws);
        			if (w != null) {
        				words.add(w);
        			}
    			}
    		}
    	}
    	
    	return words;
    }
    
    private Word parseKrcWord(String wordStr) {
    	Word word = null;
    	
    	if (wordStr != null && !"".equals(wordStr)) {
    		String[] wordtimes = wordStr.substring(0,wordStr.indexOf(">")).split(",");
        	
        	if (wordtimes != null && wordtimes.length >= 2) {
        		word = new Word();
        		word.setStime(Double.parseDouble(wordtimes[0]));
        		word.setDuration(Double.parseDouble(wordtimes[1]));
        		word.setTxt(wordStr.substring(wordStr.indexOf(">")+1));
        	}
    	}
    	
    	return word;
    }
    
    private void saveJsonFile(String json, String filepath) {
		FileOutputStream fout = null;
		try {
			fout = new FileOutputStream(new File(filepath));
			fout.write(json.getBytes("UTF-8"));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (fout != null) {
				try {
					fout.close();
					fout = null;
				} catch (IOException e) {
					fout = null;
				}
			}
		}
	}
    
}  