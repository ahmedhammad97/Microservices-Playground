package com.hammadltd.mainservice;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.UUID;

public class Info {
    private String name;
    private int age;
    private String description;
    private String id;

    public Info() { }

    public Info(String name, int age, String description) {
        this.name = name;
        this.age = age;
        this.description = description;
        this.setUniqueId();
    }

    public void setUniqueId() {
        this.id = UUID.randomUUID().toString();
    }

    public String getId() {
        return this.id;
    }

    public String getSecretData() {
        try
        {
            String key = "McQeThWmZq4t7w!z";
            String initVector = "encryptionIntVec";
            IvParameterSpec iv = new IvParameterSpec(initVector.getBytes("UTF-8"));
            SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
            String strToEncrypt = this.name + this.description + this.age + this.id;
            return Base64.getEncoder().encodeToString(cipher.doFinal(strToEncrypt.getBytes("UTF-8")));
        }
        catch (Exception e)
        {
            System.out.println("Error while encrypting: " + e.toString());
        }
        return null;
    }

    public String getName() { return this.name; }
}
