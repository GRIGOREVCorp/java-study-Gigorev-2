package neoflexprojecttask2;

import org.apache.log4j.Logger;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;
import java.util.Iterator;

public class NeoFlexprojectTask2 {
    private static final Logger logger = Logger.getLogger(NeoFlexprojectTask2.class); // имена констант всегда с больших букв - LOGGER

    static Hashtable<String,Integer> countWords;

    public static void main(String[] args) {
        try {        
            writeFile(searchWord("String private public", true));
        } catch (FileNotFoundException ex1) { logger.info(ex1); }
          catch (IOException ex2) { logger.warn(ex2); }
          catch (Exception ex3) { logger.error(ex3); }
    }
    
    public static Hashtable<String,Integer> searchWord(String word, boolean ignorCase) {
        countWords = new Hashtable<String,Integer>();
        try {
            String[] wordsFromTheText = transformationInMassiv(readFile());        
            String[] wordsFromTheWord = transformationInMassiv(word);
            int count;
            for (String d : wordsFromTheWord) {
                count=0;
                for (String f : wordsFromTheText) {
                    if (((d.compareToIgnoreCase(f))==0)&&(ignorCase==true))
                        count++;
                    else if((d.compareTo(f))==0) 
                            count++;
                }
                countWords.put(d,count);
            }
            return countWords;
        } catch (FileNotFoundException ex1) { logger.info(ex1); }
          catch (IOException ex2) { logger.warn(ex2); }
          catch (Exception ex3) { logger.error(ex3); }
        return null;
    }
    
    public static String[] transformationInMassiv(String text) {
        text = text.replaceAll(" ", "_");
        text = text.replaceAll("\\W", "_");
        while (true) {            
            if (text.indexOf("__")!=-1) {
                text = text.replaceAll("__", "_");
                continue;
            } break;
        }
        return text.split("_");
    }
    
    public static String readFile() throws Exception{//если метод может кидать Exception, то остальные исключения можно не перечислять
        try(InputStream f1 = new FileInputStream("input.txt"); //очень хорошо что используешь try-with-resources
            BufferedInputStream bis = new BufferedInputStream(f1)) {// bis не нужен, см. bis.close
            byte b[] = new byte[f1.available()];// попробуй переписать используя Stream API
            f1.read(b);
            bis.close();// close не нужен, try-with-resources сам все сделает
            f1.close();// аналогично выше
            return new String(b, "utf8");
        }// ловить исключения лучше в том методе где они могут выкидываться (исключением может быть общий ExceptionHandler для всего приложения, но он будет позже, когда доберемся до спринга)
    }
    
    public static void writeFile(Hashtable<String,Integer> result) throws Exception{
        Date dateNow = new Date();//советую присмотреться к "новому" API - LocalDateTime, в данном примере его использование не приципиально, но может он больше
        SimpleDateFormat formatForDateNow = new SimpleDateFormat("hh.mm.ss");
        String nameFile = "ouput_" + formatForDateNow.format(dateNow) +  ".txt";
        try (FileWriter f0 = new FileWriter(nameFile, true)) {
            Iterator itr = countWords.keySet().iterator();
            while(itr.hasNext()) {
                String word = (String)itr.next();
                f0.append("Количество встречающихся слов '" + word + "' "
                        + "в тексте равно " + (int) countWords.get(word) + "\n");
            } // исключения лучше хэндлить в этом методе. FileNotFoundException выкидывается только на чтение, здесь его не будет
        }
    }
}
