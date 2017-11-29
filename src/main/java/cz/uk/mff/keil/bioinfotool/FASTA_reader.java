
package cz.uk.mff.keil.bioinfotool;

import org.biojava.nbio.core.sequence.template.Sequence;

/**
 *
 * @author Jan Keil
 */
public interface FASTA_reader {
    
    int getLength(String index);
    
    String getDescription(String index);
    
    String getSequence(String index);
     
    /**
     *
     * @param index of seqence in file
     * @param Start first AA in the subsequence
     * @param End last AA in the subsequence
     * @return subsequence
     */
    Sequence getSubsequence(String index, int Start, int End);

    /**
     *
     * @return type of sequences included in FASTA file.
     * (1: protein, 2: nucleotide, 3: ribonucleotide)
     */
    int getType();
}
