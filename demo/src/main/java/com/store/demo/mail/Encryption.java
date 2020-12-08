package com.store.demo.mail;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Encryption {
    private static String keys = "";

    public Encryption() {

    }

    public Encryption(String key) {
        keys = key;
    }

    public String encrypt(String messageString, int key) {
        String codedString = "";
        char[] charArray = messageString.toCharArray();

        for (int i = 0; i < messageString.length(); i++) {
            //System.out.println("before="+charArray[i]);
            charArray[i] += key;
            //System.out.println("after="+charArray[i]);
            String temp = codedString;
            //System.out.println(temp);
            codedString = temp + charArray[i];
        }
        //codedString=charArray.toString();
        return codedString;
    }

    public String decrypt(String codedString) throws IOException {
        String messageString = "";
        File file = new File("/home/rashwin/Project/Rashwin_Nonda_SOF/CaseStudy/demo/src/main/java/com/store/demo/mail/key.txt");
        FileInputStream fileInputStream = new FileInputStream(file);
        keys = new String(fileInputStream.readAllBytes());
        //System.out.println(keys);
        char[] keyArray = keys.toCharArray();
        int key = 0;
        for (char temp : keyArray) {
            key += temp;
        }
        key %= 100;

        char[] charArray = codedString.toCharArray();
        for (int i = 0; i < codedString.length(); i++) {
            charArray[i] -= key;
            String temp = messageString;
            messageString = temp + charArray[i];
        }

        return messageString;
    }


}

