import java.io.File;
import java.io.IOException;

/**
 * Handler for interaction between external compression program and Cracx
 * @author Till
 *
 */
public class ProgramHandler {

    private String program; // compression program
    private String programpath; // absolute path of program
    private String filepath; // absolute path of archive
    
    public ProgramHandler(String program, String programpath, String filepath) {
        setProgram(program);
        setProgrampath(programpath);
        setFilepath(filepath);
    }
    
    public String getFilepath() {
        return filepath;
    }

    public String getProgram() {
        return program;
    }

    public String getProgrampath() {
        return programpath;
    }

    public void setFilepath(String filepath) {
        this.filepath = filepath;
    }

    public void setProgram(String program) {
        this.program = program;
    }

    public void setProgrampath(String programpath) {
        this.programpath = programpath;
    }

    /**
     * opens the compression program, tries to decompress with current combination
     * and closes the program again
     * 
     * @param combination current try
     * @return 7,2:failure 0:success 1:warnings
     */
    int tryPW(String combination) {
        try {
            Process process;
            
            //String outputPath = filepath.substring(0, filepath.lastIndexOf(File.separator));
            
            if (program == "7z") { //$NON-NLS-1$
                process = new ProcessBuilder(
                        programpath + File.separator + "7z", "x", filepath, "-aoa", "-p" + combination/*, "-o" + outputPath*/).start(); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ // old: "-aoap" + combination
                int res = process.waitFor();
                return res;
            } 
            else if (program == "WinRAR") { //$NON-NLS-1$
                process = new ProcessBuilder(
                        programpath + File.separator + "UnRAR", "x", "-p" + combination, "-inul", "-ibck", "-y", filepath).start(); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$
                return (process.waitFor());
            }

            return 7; // No archive program chosen
        } catch (IOException e) {
            e.printStackTrace();
            return 2; // Command Line Error
        } catch (InterruptedException e) {
            e.printStackTrace();
            return 7; // Other Error
        }
    }
    
}
