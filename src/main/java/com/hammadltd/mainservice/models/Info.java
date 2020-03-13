package com.hammadltd.mainservice.models;

import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.UUID;

@Component
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

    public String getSecretData(String key) {
        try
        {
            String initVector = "encryptionIntVec";
            IvParameterSpec iv = new IvParameterSpec(initVector.getBytes("UTF-8"));
            SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
            String strToEncrypt = this.name + this.description + this.age + this.id;
            return Base64.getEncoder()
                    .encodeToString(cipher.doFinal(strToEncrypt.getBytes("UTF-8")));
        }
        catch (Exception e)
        {
            e.printStackTrace();
            System.out.println("Error while generating secret data: " + e.toString());
        }
        return null;
    }

    public String getName() { return this.name; }

    public int getAge() { return this.age; }

    public String getDescription() { return this.description; }
}
