package cz.uk.mff.keil.bioinfotool;

import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.biojava.nbio.alignment.Alignments;
import org.biojava.nbio.alignment.SimpleGapPenalty;
import org.biojava.nbio.alignment.template.GapPenalty;
import org.biojava.nbio.alignment.template.PairwiseSequenceAligner;
import org.biojava.nbio.core.alignment.matrices.SubstitutionMatrixHelper;
import org.biojava.nbio.core.alignment.template.SequencePair;
import org.biojava.nbio.core.alignment.template.SubstitutionMatrix;
import org.biojava.nbio.core.sequence.ProteinSequence;
import org.biojava.nbio.core.sequence.compound.AminoAcidCompound;
import org.biojava.nbio.core.sequence.io.FastaReaderHelper;

/**
 *
 * @author Jan Keil
 */
public class EditDistance {
    
    public static void ed() {
         try {
            
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
                    = Alignments.getPairwiseAligner(s1, s2, Alignments.PairwiseSequenceAlignerType.GLOBAL, penalty, matrix);

            SequencePair<ProteinSequence, AminoAcidCompound> pair = editDistance.getPair();
            
            System.out.println(pair.toString(60));
            System.out.println("Edit distance = " + editDistance.getDistance());

        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
        private static ProteinSequence getSequenceForId(String uniProtId) throws Exception {
        URL uniprotFasta = new URL(String.format("http://www.uniprot.org/uniprot/%s.fasta", uniProtId));
        ProteinSequence seq = FastaReaderHelper.readFastaProteinSequence(uniprotFasta.openStream()).get(uniProtId);
        //System.out.printf("id : %s %s%s%s", uniProtId, seq, System.getProperty("line.separator"), seq.getOriginalHeader());
        //System.out.println();
        return seq;
    }

}
