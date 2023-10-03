package com.techelevator;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Logger {

    File file;

    public Logger(File file){
        this.file = file;
    }

    public String dateAndTime(){
        LocalDateTime timestamp = LocalDateTime.now();

        DateTimeFormatter targetFormat = DateTimeFormatter.ofPattern("MM/dd/yyyy hh:mm a");
        return timestamp.format(targetFormat);
    }

    public void write(String message){

        String dateAndTime = dateAndTime();

        try{
            PrintWriter writer = null;

            if (file.exists()){

                writer = new PrintWriter(new FileOutputStream(file, true));
            } else{
                writer = new PrintWriter(file);
            }

            writer.append(dateAndTime + " " + message + "\n");
            writer.flush();
            writer.close();

        }catch (FileNotFoundException e){
            System.out.println("File can't be accessed");
        }
    }

    public void addCreateSalesReport(String name,String message){
        String dateAndTime = dateAndTime();

        try{
            PrintWriter saleWriter;
            if(file.exists()){
                saleWriter = new PrintWriter(new FileOutputStream(file, true));
            }else{
                saleWriter = new PrintWriter(file);
            }
            String newLine = name + "|" + message + "\n";
            saleWriter.append(newLine);
            saleWriter.flush();
            saleWriter.close();
        }catch(FileNotFoundException e){
            System.out.println("File can't be accessed");
        }

    }
}
