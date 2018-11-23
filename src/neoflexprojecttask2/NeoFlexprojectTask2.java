package neoflexprojecttask2;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;
import org.apache.log4j.Logger;

public class NeoFlexprojectTask2 {
    private static final Logger LOGGER = Logger.getLogger(NeoFlexprojectTask2.class);

    static Hashtable<String,Integer> countWords;

    public static void main(String[] args) {
        try {        
            writeFile(searchWord("String private public", true));
        } catch (FileNotFoundException ex1) { LOGGER.info(ex1); }
          catch (IOException ex2) { LOGGER.warn(ex2); }
          catch (Exception ex3) { LOGGER.error(ex3); }
    }
    
    private static Hashtable<String,Integer> searchWord(String word, boolean ignorCase) {
        countWords = new Hashtable<String,Integer>();
        try {
            /*String[] wordsFromTheText = transformationInMassiv(readFile());        
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
            }*/
            List<String> text = readingFileInCollection();
            String[] wordsFromTheWord = transformationInMassiv(word);
            int count;
            for (String d : wordsFromTheWord) {
                count=0;
                for (String f : text) {
                    if (((d.compareToIgnoreCase(f))==0)&&(ignorCase==true))
                        count++;
                    else if((d.compareTo(f))==0) 
                            count++;
                }
                countWords.put(d,count);
            }
            return countWords;
        } catch (FileNotFoundException ex1) { LOGGER.info(ex1); }
          catch (IOException ex2) { LOGGER.warn(ex2); }
          catch (Exception ex3) { LOGGER.error(ex3); }
        return null;
    }
    
    private static String[] transformationInMassiv(String text) {
        return text.replaceAll("[^A-Za-z]", " ").split(" ");
    }
    
    /*private static String readFile() throws Exception{        
        try(InputStream input = new FileInputStream("input.txt")) {
            byte b[] = new byte[input.available()];
            input.read(b);
            return new String(b, "utf8");
        }
    }*/
    
    private static List<String> readingFileInCollection() throws IOException {
        List<String> text = Files.lines(Paths.get("input.txt"))
            .map(line -> line.replaceAll("[^A-Za-z]", " ").split(" ")).flatMap(Arrays::stream)
            .collect(Collectors.toList());
        return text;
    }
    
    private static void writeFile(Hashtable<String,Integer> result) throws Exception{
        Date dateNow = new Date();
        SimpleDateFormat formatForDateNow = new SimpleDateFormat("hh.mm.ss");
        String nameFile = "ouput_" + formatForDateNow.format(dateNow) +  ".txt";
        try (FileWriter f0 = new FileWriter(nameFile, true)) {
            Iterator itr = countWords.keySet().iterator();
            while(itr.hasNext()) {
                String word = (String)itr.next();
                f0.append("Количество встречающихся слов '" + word + "' "
                        + "в тексте равно " + (int) countWords.get(word) + "\n");
            }
        }
    }
}
