package com.nitinrkz.autosubgen;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import edu.cmu.sphinx.api.Configuration;
import edu.cmu.sphinx.api.SpeechResult;
import edu.cmu.sphinx.api.StreamSpeechRecognizer;
import java.io.PrintWriter;
import java.net.URL;

public class Transcribe {       
   public static URL acousticm;
   public static URL dict;
   public static URL lmp;
   public static URL audioUrl;
   public static URL G2P;
    Transcribe(URL ac,URL d,URL lmp,URL a,URL g2)throws Exception
    {
       acousticm=ac;
       dict=d;
       Transcribe.lmp=lmp;
       audioUrl=a;
       G2P=g2;
    }
    Transcribe()throws Exception
    {
         acousticm=new URL("file://C:/Users/Nitinrkz/Documents/NetBeansProjects/AutoSubGen/en-us");
         dict=new URL("file://C:/Users/Nitinrkz/Documents/NetBeansProjects/AutoSubGen/en-us/cmudict-5prealpha.dict");
         lmp=new URL("file://C:/Users/Nitinrkz/Documents/NetBeansProjects/AutoSubGen/en-us/en_us.lm.bin");
         audioUrl=new URL("file://C:/Users/Nitinrkz/Documents/NetBeansProjects/AutoSubGen/Audio/audio.wav");
         G2P=new URL("file://C:/Users/Nitinrkz/Documents/NetBeansProjects/AutoSubGen/en-us/model.fst.ser");
      
    }            
    
    public static String transcript;
    public static int transcribing() throws Exception {
        PrintWriter pw = new PrintWriter(new File("c:/temp.txt"));
        try{                             
        Configuration configuration = new Configuration();
        
        configuration
               .setAcousticModelPath(acousticm.getPath());
        configuration
                .setDictionaryPath(dict.getPath());
        configuration
                .setLanguageModelPath("resource:/edu/cmu/sphinx/models/en-us/en-us.lm.bin");

        StreamSpeechRecognizer recognizer = new StreamSpeechRecognizer(
                configuration);
        InputStream stream;
        stream = new FileInputStream(new File(audioUrl.getPath()));

        recognizer.startRecognition(stream);
        SpeechResult result;
        while (true)
        {
            result = recognizer.getResult();
            if(result==null)
                break;
            //System.out.format("Hypothesis: %s\n",result.getWords());
            transcript =result.getNbest(1).toString();
            transcript=transcript.replace("null"," ");
            String text=transcript.toLowerCase();
            text=text.replace("<s>"," ");
            text=text.replace("</s>"," ");
            text=text.replace("["," ");
            text=text.replace("]"," ");
            
            System.out.println(text);
            pw.println(text.trim());
            pw.flush();
        }
          pw.close();
        
        recognizer.stopRecognition();
    }
        catch(Exception e)
    {
    System.out.println("broke"+ e);
    }
    
        
          
        return 1;
     
    }            
         
}