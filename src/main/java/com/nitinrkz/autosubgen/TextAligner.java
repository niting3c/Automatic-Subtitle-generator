package com.nitinrkz.autosubgen;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import edu.cmu.sphinx.alignment.LongTextAligner;
import edu.cmu.sphinx.api.SpeechAligner;
import edu.cmu.sphinx.result.WordResult;
import edu.cmu.sphinx.util.TimeFrame;
import java.io.File;
import java.io.PrintWriter;
import java.util.*;


public class  TextAligner{
   public static URL acousticm;
   public static URL dict;
   public static URL lmp;
   public static URL audioUrl;
   public static URL G2P;
   public static File f;
   public static PrintWriter pw;
   
   TextAligner(URL ac,URL d,URL lmp,String a,URL g2,String v)throws Exception
    {
        acousticm=ac;
        dict=d;
        TextAligner.lmp=lmp;
        audioUrl=new File(a).toURI().toURL();
        G2P=g2;
        f=new File(v);
        pw=new PrintWriter(f);
    }
    TextAligner()throws Exception
    {
         acousticm=new URL("file://C:/Users/Nitinrkz/Documents/NetBeansProjects/AutoSubGen/en-us");
         dict=new URL("file://C:/Users/Nitinrkz/Documents/NetBeansProjects/AutoSubGen/en-us/cmudict-5prealpha.dict");
         lmp=new URL("file://C:/Users/Nitinrkz/Documents/NetBeansProjects/AutoSubGen/en-us/en_us.lm.bin");
         audioUrl=new File("C:/Users/Nitinrkz/Documents/NetBeansProjects/AutoSubGen/Audio/audio.wav").toURI().toURL();
         G2P=new URL("file://C:/Users/Nitinrkz/Documents/NetBeansProjects/AutoSubGen/en-us/model.fst.ser");
         f=new File("C:/Users/Nitinrkz/Documents/NetBeansProjects/AutoSubGen/Audio/audio.txt");
         pw=new PrintWriter(f);
    
    }
    
    public static void Allign(List<String> transcript,String a)throws Exception{
     List<WordResult> results = null;
     try{
        TimeFrame t;
        int number=1;
        
      
        SpeechAligner aligner = new SpeechAligner(acousticm.getPath(), dict.getPath(), G2P.getPath());
        audioUrl=new File("C:/Users/Nitinrkz/Documents/NetBeansProjects/AutoSubGen/Audio/audio.wav").toURI().toURL();
         
        //Word result: word, time frame, double score, double confidence
        try{//Word result: string, double confidence
         results = aligner.align(audioUrl, transcript);
        }
        catch(Exception e)
        {System.out.println(e);
        e.printStackTrace();
        }List<String> stringResults = new ArrayList<>();
        
        //stringResults list will have a list of words in transcript text.
        results.stream().forEach((wr) -> {
            stringResults.add(wr.getWord().getSpelling());
        });
        
        //creates a long text aligner object with tuple size 1
        LongTextAligner textAligner =
                new LongTextAligner(stringResults, 1);
        
        //words list will have a list of Word objects for the transcript
        List<String> sentences = aligner.getTokenizer().expand(a);
        List<String> words = aligner.sentenceToWords(sentences);
       System.out.println("starting");
        int[] aid = textAligner.align(words);
        int lastId = -1;
         String text;
               
        for (int i = 0; i < aid.length; ++i) {
        //iterates over the aid array
            
            if (aid[i] == -1) {
                System.out.format("- %s\n", words.get(i));
                
            } else {
                
                if (aid[i] - lastId > 1) {
                    for (WordResult result : results.subList(lastId + 1,
                            aid[i])) {
                        if(result.getTimeFrame() != null){
                            t = result.getTimeFrame();
                        }else{
                            t = new TimeFrame(0,0);
                        }
                        text = result.getWord().getSpelling();
                        findsub(number++,text , t.getStart(), t.getEnd());
                     
                    }
                } 
                
                if(results.get(aid[i]).getTimeFrame() != null){
                    t = results.get(aid[i]).getTimeFrame();
                }else{
                    t = new TimeFrame(0,0);
                }
                text = results.get(aid[i]).getWord().getSpelling();
                findsub(number++,text , t.getStart(), t.getEnd());
                     lastId = aid[i];
            }
        }

        if (lastId >= 0 && results.size() - lastId > 1) {
            for (WordResult result : results.subList(lastId + 1,results.size())) {
                if(result.getTimeFrame() != null){
                    t = result.getTimeFrame();
                }else{
                    t = new TimeFrame(0,0);
                }
                text = result.getWord().getSpelling();
                findsub(number++,text , t.getStart(), t.getEnd());
                     }
        }
        }
        catch(Exception e)
        {
        System.out.println(e);
        e.printStackTrace();
        }
       
    }
    
    
   
           
     
     
 public static void findsub(int n,String text,long s,long e) throws Exception
   {
                pw.println(n);
                pw.println(
                                test.format(s) +
                                " --> " +
                                test.format(e));
                pw.println(text);
                
                // Add an empty line at the end
                pw.println();
                pw.flush();
     }
    
 public static void starter()throws Exception{
     System.out.println("Start Alligning");
     File f=new File("c:/temp.txt");
     Scanner sc=new Scanner(f);
     String transcript =" ";
     List<String> tr=new ArrayList<>();
     int a=0;
     while(sc.hasNext())
     {
         tr.add(sc.nextLine());
         transcript+=tr.get(a++)+ " ";
     }
     System.out.println(transcript);     
     TextAligner t=new TextAligner();
     t.Allign(tr,transcript);
     pw.flush();
     pw.close();
     }
   
   }

