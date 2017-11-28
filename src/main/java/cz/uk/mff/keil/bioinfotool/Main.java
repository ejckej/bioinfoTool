package cz.uk.mff.keil.bioinfotool;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.biojava.nbio.core.sequence.ProteinSequence;
import org.biojava.nbio.core.sequence.compound.AminoAcidCompound;
import org.biojava.nbio.core.sequence.compound.AminoAcidCompoundSet;
import org.biojava.nbio.core.sequence.io.FastaReader;
import org.biojava.nbio.core.sequence.io.GenericFastaHeaderParser;
import org.biojava.nbio.core.sequence.io.ProteinSequenceCreator;
import org.biojava.nbio.core.sequence.template.Sequence;
import org.biojava.nbio.core.util.InputStreamProvider;

/**
 *
 * @author Jan Keil
 */
public class Main {

    //private static Sequence seq1, seq2;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) { 
        writeFileToConsole("./help.txt");
        EditDistance.ed();
    }

    private static void writeFileToConsole(String path){
        try {
            BufferedReader in = new BufferedReader(new FileReader(path));
            String s;
            while((s = in.readLine()) != null){
                System.out.println(s);
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static Sequence[] getData(File file) throws IOException {

        if (!file.exists()) {
            System.err.println("File does not exist.");
            System.exit(1);
        }

        InputStreamProvider isp = new InputStreamProvider();
        InputStream inStream = isp.getInputStream(file);

        FastaReader<ProteinSequence, AminoAcidCompound> fastaReader = new FastaReader<>(
                inStream,
                new GenericFastaHeaderParser<>(),
                new ProteinSequenceCreator(AminoAcidCompoundSet.getAminoAcidCompoundSet()));

        LinkedHashMap<String, ProteinSequence> b;
        Sequence[] secs = new Sequence[2];
        
        int nrSeq = 0;

        while ((b = fastaReader.process(4)) != null) {
            for (String key : b.keySet()) {
                nrSeq++;
                System.out.println(nrSeq + " : " + key + " " + b.get(key));
                secs[nrSeq-1] = b.get(key);
            }
        }
        if (nrSeq != 2) {
            System.err.println("Wrong sequence count...");
            System.exit(2);
        }
        return secs;
    }
}
