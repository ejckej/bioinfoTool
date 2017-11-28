package cz.uk.mff.keil.bioinfotool;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.List;
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
public class FASTA_Protein {

    private File file;
    private int type;

    public FASTA_Protein() {

    }

    public FASTA_Protein(File file, int type) {
        this.file = file;
        this.type = type;
    }
    
    private void check(){
         if (!file.exists()) {
            System.err.println("File does not exist.");
            System.exit(1);
        }
         
        LinkedHashMap<String, Sequence> map;
        switch (type) {
            case 1:
                //map = getProteinData(file);
                break;
            case 2:
                break;
            case 3:
                break;
            default:
                break;
        }
    }

    private LinkedHashMap<String, ProteinSequence> getProteinData(File file) throws IOException {
        //check();
        
        InputStreamProvider isp = new InputStreamProvider();
        InputStream inStream = isp.getInputStream(file);

        FastaReader<ProteinSequence, AminoAcidCompound> fastaReader = new FastaReader<>(
                inStream,
                new GenericFastaHeaderParser<>(),
                new ProteinSequenceCreator(AminoAcidCompoundSet.getAminoAcidCompoundSet()));

        LinkedHashMap<String, ProteinSequence> b;

        int nrSeq = 0;

        while ((b = fastaReader.process(10)) != null) {
            for (String key : b.keySet()) {
                nrSeq++;
                System.out.println(nrSeq + " : " + key + " " + b.get(key));
            }
        }
        return b;
    }
}
