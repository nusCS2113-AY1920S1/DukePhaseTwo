package storage;
import exception.DukeException;
import task.*;
import java.io.*;
import java.util.ArrayList;
/**
 * This Storage class is utilised to do both the reading and writing to persistent storage using the two primary methods
 * saveFile and loadFile.
 *
 * @author Sai Ganesh Suresh
 * @version v2.0
 */
public class Storage {

    private File file;

    /**
     * This Storage constructor is used to function is used to assign the different parameters required by the Storage
     * methods.
     *
     * @param filePath This parameter holds the string which contains the location of the persistent storage.
     * @param file This parameter holds the file to write to.
     */
    public Storage(File file) {
            this.file = file;
            this.file.getParentFile().mkdirs();
    }

    /**
     * This saveFile method is used repeatedly throughout the other classes to save updates made to the TaskList to the
     * persistent storage to ensure the user does not loose data due to sudden termination of the program.
     *
     * @param listOfTasks This parameter holds the updated TaskList of the user and used to save the updated TaskList.
     * @throws DukeException This exception is thrown if there is not file at the given location to save to.
     */
    public void saveFile(ArrayList<Task> listOfTasks) throws DukeException {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(listOfTasks);
            objectOutputStream.close(); //always close
            fileOutputStream.close(); //always close
        }
        catch(IOException e)
        {
            throw new DukeException(DukeException.UNABLE_TO_WRITE_FILE());
        }
    }

    /**
     * This saveFile method is used repeatedly throughout the other classes to save updates made to the TaskList to the
     * persistent storage to ensure the user does not loose data due to sudden termination of the program.
     *
     * @param file This parameter is passed as to be able to write to the file.
     * @throws DukeException This exception is thrown for any unexpected issues such as no file in location, unable to
     * read the file or a class in not found.
     */
    public ArrayList<Task> loadFile(File file) throws DukeException{
        ArrayList<Task> listOfTasks = new ArrayList<>();
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            listOfTasks = (ArrayList<Task>) objectInputStream.readObject();
            fileInputStream.close();
            objectInputStream.close();
            return listOfTasks;
        }
        catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
            throw new DukeException(DukeException.FILE_DOES_NOT_EXIST());

        }
        catch (IOException e) {
            throw new DukeException(DukeException.UNABLE_TO_READ_FILE());
        }
       catch (ClassNotFoundException e) {
            throw new DukeException(DukeException.CLASS_DOES_NOT_EXIST());
       }
        catch (Exception e)
        {
            throw new DukeException(DukeException.CLASS_DOES_NOT_EXIST());
        }
    }
}
