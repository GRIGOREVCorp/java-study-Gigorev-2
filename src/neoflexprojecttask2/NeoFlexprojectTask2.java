package neoflexprojecttask2;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.*;
import java.util.logging.Level;
import java.util.stream.Collectors;
import javafx.util.converter.LocalDateTimeStringConverter;
import org.apache.log4j.Logger;
import sun.util.resources.LocaleData;

public class NeoFlexprojectTask2 {
    private static final Logger LOGGER = Logger.getLogger(NeoFlexprojectTask2.class);

    static Hashtable<String,Integer> countWords;

    public static void main(String[] args) {
            writeFile(searchWord("String private public", true));
    }
    
    private static Hashtable<String,Integer> searchWord(String word, boolean ignorCase) {
        countWords = new Hashtable<String,Integer>();
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
    }
    
    private static String[] transformationInMassiv(String text) {
        return text.replaceAll("[^A-Za-z]", " ").split(" ");
    }
    
    private static List<String> readingFileInCollection() {
        try {
            List<String> text = Files.lines(Paths.get("input.txt"))
                    .map(line -> line.replaceAll("[^A-Za-z]+", " ").split(" "))
                    .flatMap(Arrays::stream)
                    .collect(Collectors.toList());                    
            return text;
        } catch (FileNotFoundException notFound) { LOGGER.info(notFound); }
          catch (IOException ex) { LOGGER.warn(ex); }
          catch (Exception e) { LOGGER.error(e); }
        return null;
    }
    
    private static void writeFile(Hashtable<String,Integer> result) {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("hh.mm.ss");
        String nameFile = "ouput_"+LocalTime.now().format(format)+".txt";
        try (FileWriter f0 = new FileWriter(nameFile, true)) {
            for (String word : countWords.keySet()) {
                f0.append("Количество встречающихся слов '" + word + "' "
                        + "в тексте равно " + countWords.get(word) + "\n");
            }
        } catch (IOException ex) { LOGGER.warn(ex); }
          catch (Exception e) { LOGGER.error(e); }
    }
}
