import java.io.IOException;
import java.io.LineNumberReader;
import java.util.Observable;

import javax.swing.JTextArea;

/**
 * Funktionalität für "Dictionary-Attacke" basierend auf einem Wörterbuch
 * @author Till
 *
 */
public class DictionaryAttack extends Observable implements Runnable{
    
    private BruteForce br;
    private boolean running;
    private LineNumberReader dictionary;
    private ProgramHandler handler;
    private JTextArea anzeige;

    public DictionaryAttack(BruteForce br, JTextArea anzeige){
        setBr(br);
        setAnzeige(anzeige);
    }

    public JTextArea getAnzeige() {
        return anzeige;
    }
    
    public BruteForce getBr() {
        return br;
    }

    public LineNumberReader getDictionary() {
        return dictionary;
    }

    public ProgramHandler getProgram() {
        return handler;
    }

    public boolean isRunning() {
        return running;
    }

    public void run() {
        setRunning(true);
        try {
            for(String line = dictionary.readLine(); line != null && running; line = dictionary.readLine()){ // jede Zeile bis Dateiende oder stopp
                int ret = handler.tryPW(line);
                anzeige.setText(anzeige.getText() + "\n" + line); //$NON-NLS-1$
                if(ret == 1 || ret == 0){ // bei Erfolg
                    anzeige.setText(anzeige.getText() + Messages.getString("DictionaryAttack.1") + line); //$NON-NLS-1$
                    setChanged();
                    notifyObservers("success");
                    setRunning(false);
                }
                anzeige.setCaretPosition(anzeige.getText().length());
            }
            getDictionary().close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(running){ // wenn nach Dictionary noch nicht stop gedrückt oder Erfolg gehabt, BruteForce
            br.run();
        }
        setChanged();
        notifyObservers("failure");
        setRunning(false);
    }

    public void setAnzeige(JTextArea anzeige) {
        this.anzeige = anzeige;
    }

    public void setBr(BruteForce br) {
        this.br = br;
    }

    public void setDictionary(LineNumberReader dictionary) {
        this.dictionary = dictionary;
    }

    public void setProgram(ProgramHandler program) {
        this.handler = program;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    public void stop(){
        setRunning(false);
        br.stop();
    }

    public void setHandler(ProgramHandler programHandler) {
        this.handler = programHandler;
    }

}
