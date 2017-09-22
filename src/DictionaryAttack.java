import java.io.IOException;
import java.io.LineNumberReader;
import java.util.Observable;

import javax.swing.JTextArea;

/**
 * functionality for attack based on a dictionary (txt file with multiple strings)
 * @author Till Riemer
 *
 */
public class DictionaryAttack extends Observable implements Runnable{
    
    private BruteForce br;
    private boolean running;
    private LineNumberReader dictionary;
    private ProgramHandler handler;
    private JTextArea anzeige;
    private int returnCheck;

    public DictionaryAttack(BruteForce br, JTextArea anzeige){
        this.br = br;
        this.anzeige = anzeige;
    }
    
    public void Initialize(ProgramHandler handler, LineNumberReader dictionary, int returnCheck)
    {
    	this.handler = handler;
    	this.dictionary = dictionary;
    	this.returnCheck = returnCheck;
    }

    public boolean isRunning() {
        return running;
    }

    public void run() {
        setRunning(true);
        try {
        	// iterate over all dictionary lines while not stopped
            for(String line = dictionary.readLine(); line != null && running; line = dictionary.readLine())
            { 
                int ret = handler.tryPW(line);
                
                anzeige.setText(line); //$NON-NLS-1$
                
                if ((handler.getProgram() != 2 && (ret == 0 || ret == 1))  // archive program returns success or warning
                		|| (handler.getProgram() == 2 && ret == returnCheck)) // custom return check fulfilled
                {
                	anzeige.setText(String.format("%s%s", Messages.getString("DictionaryAttack.1"), line)); //$NON-NLS-1$
                    setChanged();
                    notifyObservers("success");
                    setRunning(false);
                }
                anzeige.setCaretPosition(anzeige.getText().length());
            }
            dictionary.close();
        } catch (IOException e) 
        {
            e.printStackTrace();
        }
        // if nothing found yet, do a bruteforce
        if(running)
        {
            br.run();
        }
        setChanged();
        notifyObservers("failure");
        setRunning(false);
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    public void stop(){
        setRunning(false);
        br.stop();
    }    
}
