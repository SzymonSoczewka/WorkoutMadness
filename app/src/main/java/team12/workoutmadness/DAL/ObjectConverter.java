package team12.workoutmadness.DAL;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import team12.workoutmadness.BE.Day;

public class ObjectConverter {
    public static byte[] toByte(ArrayList<Day> modeldata) {
        try {

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(modeldata);
            byte[] employeeAsBytes = baos.toByteArray();
            ByteArrayInputStream bais = new ByteArrayInputStream(employeeAsBytes);
            return employeeAsBytes;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
    public static ArrayList<Day> fromByte(byte[] data) {
        try {


            ByteArrayInputStream baip = new ByteArrayInputStream(data);
            ObjectInputStream ois = new ObjectInputStream(baip);
            return (ArrayList<Day>) ois.readObject();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
