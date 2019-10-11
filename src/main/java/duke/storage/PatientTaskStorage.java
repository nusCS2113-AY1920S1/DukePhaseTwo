package duke.storage;

import duke.core.DukeException;
import duke.relation.EventPatientTask;
import duke.relation.PatientTask;
import duke.relation.StandardPatientTask;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class PatientTaskStorage {

    /**
     * A string that represents a relative file path from the project folder.
     */
    private String filePath;

    /**
     * Constructs a Storage object with a specific file path.
     *
     * @param filePath A string that represents the path of the file to read or
     *                 write.
     */
    public PatientTaskStorage(String filePath) {
        this.filePath = filePath;
    }

    public ArrayList<PatientTask> load() throws DukeException {
        ArrayList<PatientTask> patientTaskList = new ArrayList<PatientTask>();
        try {
            Reader in = new FileReader(filePath);
            Iterable<CSVRecord> records = CSVFormat.EXCEL.withFirstRecordAsHeader().parse(in);
            for (CSVRecord record : records) {
                Integer pid = Integer.parseInt(record.get("PID"));
                Integer tid = Integer.parseInt(record.get("TID"));
                boolean isDone = Boolean.parseBoolean(record.get("DONE"));
                boolean isRecursive = Boolean.parseBoolean(record.get("RECURRENCE"));
                String deadline = record.get("DEADLINE");
                String startTime = record.get("STARTTIME");
                String endTime = record.get("ENDTIME");
                String taskType = record.get("TASKTYPE");
                if (taskType.equals("S")){
                    patientTaskList.add(new StandardPatientTask(pid,tid,isDone,isRecursive,deadline,taskType));
                }
                else if (taskType.equals("E")){
                    patientTaskList.add(new EventPatientTask(pid,tid,isDone,isRecursive,startTime,endTime,taskType));
                }
            }
            return patientTaskList;
        } catch (IOException e) {
            throw new DukeException(e.getMessage());
        }
    }

    public void save(ArrayList<PatientTask> patientTask) throws DukeException {
        try {
            BufferedWriter writer = Files.newBufferedWriter(Paths.get(filePath));
            CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT
                    .withHeader("PID", "TID", "DONE", "RECURRENCE", "DEADLINE", "STARTTIME", "ENDTIME", "TASKTYPE" ));
            for (PatientTask patient : patientTask) {
                int pid = patient.getPatientId();
                int tid = patient.getTaskID();
                boolean isDone = patient.isDone();
                boolean isRecurr = patient.isRecurrsive();
                String deadline = null;
                String startTime = null;
                String endTime = null;
                String type = patient.getTaskType();
                if (patient instanceof  StandardPatientTask)
                {
                    deadline = ((StandardPatientTask) patient).getDeadline();
                }
                else if (patient instanceof EventPatientTask)
                {
                    startTime = ((EventPatientTask) patient).getStartTimeRaw();
                    endTime = ((EventPatientTask) patient).getEndTimeRaw();
                }
                csvPrinter.printRecord(pid, tid, String.valueOf(isDone), String.valueOf(isRecurr), deadline, startTime,endTime,type);
            }
            csvPrinter.flush();
        } catch (IOException e) {
            throw new DukeException(e.getMessage());
        }
    }
}
