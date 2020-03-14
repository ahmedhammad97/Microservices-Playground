package com.hammadltd.mainservice.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.hammadltd.mainservice.interfaces.IFileHandler;
import com.hammadltd.mainservice.models.Info;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileHandler implements IFileHandler {
    public String writeToFile(Info info) {
        try {
            Path tempDirPath = Files.createTempDirectory("tempdir");
            String infoFileName = "temp-" + UUID.randomUUID().toString() + ".txt";
            Path infoFilePath = tempDirPath.resolve(infoFileName);

            File infoFile = new File(infoFilePath.toString());
            infoFile.createNewFile();

            ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
            String json = ow.writeValueAsString(info);

            FileWriter myWriter = new FileWriter(infoFilePath.toString());
            myWriter.write(json);
            myWriter.close();

            return infoFilePath.toString();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void deleteFilePath(String path) {
        Path parentDir = Paths.get(path).getParent();
        System.out.println("Deleting info directory: " + parentDir);
        try {
            Files.delete(Paths.get(path));
            Files.delete(parentDir);
            System.out.println("Deleted Successfully");
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
