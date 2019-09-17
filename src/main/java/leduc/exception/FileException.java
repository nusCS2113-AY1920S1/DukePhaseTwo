package leduc.exception;

import leduc.Ui;

public class FileException extends DukeException {
    /**
     * Constructor of leduc.exception.DukeException

     */
    public FileException(){
        super();
    }

    @Override
    public String print() {
        return "File doesn't exist or cannot be created or cannot be opened";
    }
}
