package cz.uk.mff.keil.bioinfotool;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.biojava.nbio.alignment.Alignments;
import org.biojava.nbio.alignment.Alignments.PairwiseSequenceAlignerType;
import org.biojava.nbio.alignment.SimpleGapPenalty;
import org.biojava.nbio.alignment.template.GapPenalty;
import org.biojava.nbio.alignment.template.PairwiseSequenceAligner;
import org.biojava.nbio.core.alignment.matrices.SubstitutionMatrixHelper;
import org.biojava.nbio.core.alignment.template.SequencePair;
import org.biojava.nbio.core.alignment.template.SubstitutionMatrix;
import org.biojava.nbio.core.sequence.ProteinSequence;
import org.biojava.nbio.core.sequence.compound.AminoAcidCompound;
import org.biojava.nbio.core.sequence.compound.AminoAcidCompoundSet;
import org.biojava.nbio.core.sequence.io.FastaReader;
import org.biojava.nbio.core.sequence.io.FastaReaderHelper;
import org.biojava.nbio.core.sequence.io.GenericFastaHeaderParser;
import org.biojava.nbio.core.sequence.io.ProteinSequenceCreator;
import org.biojava.nbio.core.sequence.template.Sequence;
import org.biojava.nbio.core.util.InputStreamProvider;

/**
 *
 * @author Jan Keil
 */
public class Main {

    private static Sequence seq1, seq2;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {

            File file = new File("intpt.fasta");
            Sequence[] map = getData(file);
            seq1 = map[0];
            seq2 = map[1];
            
            String uniprotID1 = "P69905";
            String uniprotID2 = "P68871";

            ProteinSequence s1 = getSequenceForId(uniprotID1);
            ProteinSequence s2 = getSequenceForId(uniprotID2);
            
            SubstitutionMatrix<AminoAcidCompound> matrix = SubstitutionMatrixHelper.getBlosum65();

            GapPenalty penalty = new SimpleGapPenalty();

            int gop = 8;
            int extend = 1;
            penalty.setOpenPenalty(gop);
            penalty.setExtensionPenalty(extend);

            PairwiseSequenceAligner<ProteinSequence, AminoAcidCompound> editDistance
                    = Alignments.getPairwiseAligner(s1, s2, PairwiseSequenceAlignerType.GLOBAL, penalty, matrix);

            SequencePair<ProteinSequence, AminoAcidCompound> pair = editDistance.getPair();

            System.out.println(editDistance.getDistance());
            System.out.println(pair.toString(60));

            //System.out.println(s1.getLength());
            //NeedlemanWunsch nw = new NeedlemanWunsch();
            //System.out.println(nw.getDistance());
        } catch (Exception ex) {
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

    public static ProteinSequence getSequenceForId(String uniProtId) throws Exception {
        URL uniprotFasta = new URL(String.format("http://www.uniprot.org/uniprot/%s.fasta", uniProtId));
        ProteinSequence seq = FastaReaderHelper.readFastaProteinSequence(uniprotFasta.openStream()).get(uniProtId);
        //System.out.printf("id : %s %s%s%s", uniProtId, seq, System.getProperty("line.separator"), seq.getOriginalHeader());
        //System.out.println();
        return seq;
    }
}
