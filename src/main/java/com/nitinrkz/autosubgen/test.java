/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nitinrkz.autosubgen;

/**
 *
 * @author Nitinrkz
 */
public class test {
static public String format(long val) {
    StringBuilder                       buf=new StringBuilder(20);
    String                              sgn="";

    if(val<0) { sgn="-"; val=Math.abs(val); }

    append(buf,sgn,0,( val/3600000             ));
    append(buf,":",2,((val%3600000)/60000      ));
    append(buf,":",2,((val         %60000)/1000));
    append(buf,",",3,( val                %1000));
    return buf.toString();
    }

/** Append a right-aligned and zero-padded numeric value to a `StringBuilder`. */
static private void append(StringBuilder tgt, String pfx, int dgt, long val) {
    tgt.append(pfx);
    if(dgt>1) {
        int pad=(dgt-1);
        for(long xa=val; xa>9 && pad>0; xa/=10) { pad--;           }
        for(int  xa=0;   xa<pad;        xa++  ) { tgt.append('0'); }
        }
    tgt.append(val);
    }
public static void main(String[] args)throws Exception
{
System.out.println("checking ");
 long a=13444;
 System.out.println(format(a));
}
}
