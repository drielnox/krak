import java.util.Observable;

import javax.swing.JTextArea;

/**
 * functionality for attack based on trying out all possible combinations on a given charset
 * and word length
 * @author Till Riemer
 *
 */

public class BruteForce extends Observable implements Runnable{
    private String charset; 	// combination of used charsets
    private String combination; // current try
    private int maxPWLength;
    private int minPWLength;
    private int returnCheck = 1;
    private boolean running;
    private JTextArea logView;
    private ProgramHandler handler;
    

    public BruteForce(JTextArea logView) {
        this.logView = logView;
        combination = ""; //$NON-NLS-1$
    }

    public String getCharset() {
        return charset;
    }

    public int getMaxPWLength() {
        return maxPWLength;
    }

    public int getMinPWLength() {
        return minPWLength;
    }

    public void run() {
    	combination = "";
        running = true;
        
        for (int i = 0; i < minPWLength - 1; i++) { // initialize with space chars
            combination += " "; //$NON-NLS-1$
        }
        for (int i = minPWLength - 1; i < maxPWLength; i++) { // for every string length
            if (tryCombination(i) == 1) 
            {
                logView.setText(Messages.getString("BruteForce.2") //$NON-NLS-1$
                        + combination);
                setChanged();
                notifyObservers("success");
                return;
            }
        }
        setChanged();
        notifyObservers("failure");
    }
    
    public void stop() {
        running = false;
    }
    
    public void Initialize(String charset, int minPWLength, int maxPWLength, int commandCheck, ProgramHandler handler)
    {
    	this.charset = charset;
    	this.minPWLength = minPWLength;
    	this.maxPWLength = maxPWLength;
    	this.handler = handler;
    	this.returnCheck = commandCheck;
    }
    
    public void setHandler(ProgramHandler programHandler) {
        this.handler = programHandler;
    }        

    /**
     * try every combination on charset with given word length
     * 
     * @param index: current length & index last char
     * @return 1:success 0:failure
     */
    int tryCombination(int index) {
        for (int i = 0; (i < charset.length() && running); i++) { // for char in charset
            int ret = handler.tryPW(combination); 
            
            if (handler.getProgram() != 2 && (ret == 0 || ret == 1)) { // archive program returns success or warning
                return 1;
            }
            else if(handler.getProgram() == 2 && ret == returnCheck) { // custom return check fulfilled
            	return 1;
            }
            else { 
            	// replace char at index with next char from charset
                String temp = combination;
                combination = combination.substring(0, index)
                        + charset.charAt(i);
                if (index < temp.length() - 1) {
                    combination = combination + temp.substring(index + 1);
                }

                // plot combination
                logView.setText(combination); //$NON-NLS-1$

                if (index > 0) { // increment from lowest indexes first
                    if (tryCombination(index - 1) == returnCheck)
                        return 1;
                }
            }
        }

        return 0;
    }
}
