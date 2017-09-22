import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Handler for interaction between external compression program and Cracx
 * @author Till
 *
 */
public class ProgramHandler {

    private int program; // compression program
    private String programpath; // absolute path of program
    private String filepath; // absolute path of archive
    
    public ProgramHandler(int program, String programpath, String filepath) {
        setProgram(program);
        setProgrampath(programpath);
        setFilepath(filepath);
    }
    
    public String getFilepath() {
        return filepath;
    }

    public int getProgram() {
        return program;
    }

    public String getProgrampath() {
        return programpath;
    }

    public void setFilepath(String filepath) {
        this.filepath = filepath;
    }

    public void setProgram(int program) {
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
            
            if (program == 0) { // 7z
                process = new ProcessBuilder(
                        programpath + File.separator + "7z", "x", filepath, "-aoa", "-p" + combination).start(); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ // old: "-aoap" + combination
                int res = process.waitFor();
                return res;
            } 
            else if (program == 1) { // WinRAR
                process = new ProcessBuilder(
                        programpath + File.separator + "UnRAR", "x", "-p" + combination, "-inul", "-ibck", "-y", filepath).start(); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$
                return (process.waitFor());
            }
            else if(program == 2) // custom
            {
            	String[] cmdCommand = { "cmd" };
            	String errorLevelCmd = "echo %errorlevel%";
            	
            	// create new process that runs cmd, executes two commands,
            	// and catch the output
            	process = Runtime.getRuntime().exec(cmdCommand);     
            	ByteArrayOutputStream programOut = new ByteArrayOutputStream();
        	    new Thread(new SyncPipe(process.getErrorStream(), System.err)).start();
        	    new Thread(new SyncPipe(process.getInputStream(), programOut)).start();
        	    
        	    PrintWriter stdin = new PrintWriter(process.getOutputStream());
        	    stdin.println(programpath.replace("[pw]", combination));        	    
        	    stdin.println(errorLevelCmd);
        	    stdin.close();
        	    
        	    process.waitFor();
        	    
        	    // strip out the %errorlevel% output
        	    String output = programOut.toString();     
        	    output = output.substring(output.lastIndexOf(errorLevelCmd + System.lineSeparator()) + errorLevelCmd.length() + 2);
        	    output = output.substring(0, output.indexOf(System.lineSeparator()));        	    
        	    
        	    Integer ret = 0;
        	    try
        	    {
        	    	ret = new Integer(output);
        	    }
        	    catch(NumberFormatException e) 
        	    {
        	    	// TODO print error and stop
        	    }
        	    
        	    return ret;
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
