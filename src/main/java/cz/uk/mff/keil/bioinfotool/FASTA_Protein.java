package cz.uk.mff.keil.bioinfotool;

import java.io.File;
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
public class FASTA_Protein implements FASTA_reader{

    private final File file;
    private final LinkedHashMap<String, ProteinSequence> b;

//    public FASTA_Protein() {
//    }

    public FASTA_Protein(File file) {
        this.file = file;
        check();
        b = getProteinData(file);
    }
    
    private void check(){
         if (!file.exists()) {
            System.err.println("File does not exist.");
            System.exit(1);
        }
         
        //LinkedHashMap<String, Sequence> map;
    }

    private LinkedHashMap<String, ProteinSequence> getProteinData(File file) {
        
        InputStreamProvider isp = new InputStreamProvider();
        InputStream inStream;
        LinkedHashMap<String, ProteinSequence> a = null;
        
        try {
        inStream = isp.getInputStream(file);
        
        FastaReader<ProteinSequence, AminoAcidCompound> fastaReader = new FastaReader<>(
                inStream,
                new GenericFastaHeaderParser<>(),
                new ProteinSequenceCreator(AminoAcidCompoundSet.getAminoAcidCompoundSet()));

        int nrSeq = 0;

        while ((a = fastaReader.process()) != null) { //TODO: limit 10?
            for (String key : a.keySet()) {
                nrSeq++;
                System.out.println(nrSeq + " : " + key + " " + a.get(key));
            }
        }
        } catch (IOException ex) {
            Logger.getLogger(FASTA_Protein.class.getName()).log(Level.SEVERE, null, ex);
        }
        return a;
    }

    @Override
    public int getLength(String index) {
        return b.get(index).getLength();
    }

    @Override
    public String getDescription(String index) {
        return b.get(index).getDescription();
    }

    @Override
    public String getSequence(String index) {
        return b.get(index).getSequenceAsString();
    }

    @Override
    public Sequence getSubsequence(String index, int Start, int End) {
        for (String key : b.keySet()) {
            System.out.println(key + " " + b.get(key));
            return b.get(key).getSubSequence(Start, End);
        }
        return null;
    }

    @Override
    public int getType() {
        return 1;
    }
}
