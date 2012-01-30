import java.io.IOException;

/**
 * Klasse für Interaktion zwischen Packprogramm und CracX
 * @author Till
 *
 */
public class ProgramHandler {

    private String program; // verwendetes Packprogramm
    private String programpath; // absolutes Verzeichnis des Packprogramms
    private String filepath; // absoluter Pfad des verschlüsselten Archivs
    
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
     * probiert die aktuelle Kombination aus
     * 
     * @param combination aktuelle PW-Kombination
     * @return 7,2:Mißerfolg 0:Erfolg 1:Warnungen -> s. Programmdokumentationen
     */
    int tryPW(String combination) {
        try {
            Process process;
            if (program == "7z") { //$NON-NLS-1$
                process = new ProcessBuilder(
                        programpath + "7z", "x", filepath, "-aoap" + combination).start(); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
                return (process.waitFor());
            } 
            else if (program == "WinRAR") { //$NON-NLS-1$
                process = new ProcessBuilder(
                        programpath + "WinRAR", "x", "-p" + combination, "-inul", "-ibck", "-y", filepath).start(); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$
                return (process.waitFor());
            }

            return 7; // No archive program choosed
        } catch (IOException e) {
            e.printStackTrace();
            return 2; // Command Line Error
        } catch (InterruptedException e) {
            e.printStackTrace();
            return 7; // Other Error
        }
    }
    
}
