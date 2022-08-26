package com.epam.mjc.nio;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.HashMap;
import java.util.Map;

import static java.lang.Integer.parseInt;


public class FileReader {




    public Profile getDataFromFile(File file) {
        StringBuilder data = new StringBuilder();
        Map<String,String> map = new HashMap<>();

        try (RandomAccessFile aFile = new RandomAccessFile(file, "r")) {
        FileChannel inChannel = aFile.getChannel();
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        char ch;
        while (inChannel.read(buffer)>0) {
            buffer.flip();
            for (int i = 0; i < buffer.limit(); i++) {
                ch = (char) buffer.get();
                data.append(ch);
            }
            buffer.clear();
            }
            String[] keyPairs = data.toString().split("\n");
            for (String pair : keyPairs) {
                String[] keyValue = pair.split(": ");
                map.put(keyValue[0], keyValue[1].trim());
            }


        } catch (IOException e) {
            e.printStackTrace();
        }

        return new Profile(map.get("Name"),
                parseInt(map.get("Age")),
                map.get("Email"),
                Long.parseLong(map.get("Phone")));
    }
}
