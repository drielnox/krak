import java.util.Observable;

import javax.swing.JTextArea;

public class BruteForce extends Observable implements Runnable{
    private String charset; // zu benutzender Zeichensatz
    private String combination; // aktuelle Kombination
    private int maxPWLength; // maximale PW-L�nge
    private int minPWLength; // minimale PW-L�nge
    private boolean running;
    private JTextArea anzeige;
    private ProgramHandler handler; // Interaktion mit dem Zip-Programm

    public BruteForce(JTextArea anzeige) {
        this.anzeige = anzeige;
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
        running = true;
        // Combination-String mit Leerzeichen auff�llen
        for (int i = 0; i < getMinPWLength() - 1; i++) {
            combination += " "; //$NON-NLS-1$
        }
        for (int i = minPWLength - 1; i < maxPWLength; i++) { // bis zur
            // maximalen L�nge
            if (tryCombination(i) == 1) {
                anzeige.setText(anzeige.getText()
                        + Messages.getString("BruteForce.2") //$NON-NLS-1$
                        + combination);
                anzeige.setCaretPosition(anzeige.getText().length());
                setChanged();
                notifyObservers("success");
            }
        }
        setChanged();
        notifyObservers("failure");
    }

    public void setCharset(String charset) {
        this.charset = charset;
    }

    public void setMaxPWLength(int maxPWLength) {
        this.maxPWLength = maxPWLength;
    }

    public void setMinPWLength(int minPWLength) {
        this.minPWLength = minPWLength;
    }

    public void stop() {
        running = false;
    }

    /**
     * geht alle m�glichen Kombinationen einer Wortl�nge durch
     * 
     * @param index
     *            (aktuelle) Wortl�nge
     * @return 1:Erfolg 0:Misserfolg
     */
    int tryCombination(int index) {
        for (int i = 0; (i < charset.length() && running); i++) { // �ber jedes
                                                                  // Zeichen des
                                                                  // Charsets
            int ret = handler.tryPW(combination);

            if (ret == 0 || ret == 1) {
                return 1; // Ausprobieren - bei Erfolg return
            } else { // ansonsten betreffenden Index durch n�chstes Zeichen aus
                     // Charset ersetzen
                String temp = combination;
                combination = combination.substring(0, index)
                        + charset.charAt(i); // Kombination �ndern...
                if (index < temp.length() - 1) {
                    combination = combination + temp.substring(index + 1);
                }

                // Kombination ausgeben und ans Ende scrollen
                anzeige.setText(anzeige.getText() + "\n" + combination); //$NON-NLS-1$
                anzeige.setCaretPosition(anzeige.getText().length());

                if (index > 0) { // zun�chst die niedrigsten Stellen
                    // inkrementieren
                    if (tryCombination(index - 1) == 1)
                        return 1;
                }
            }
        }

        return 0;
    }

    public void setHandler(ProgramHandler programHandler) {
        this.handler = programHandler;
    }

    
}
