
package cz.uk.mff.keil.bioinfotool;

/**
 *
 * @author Jan Keil
 */
public interface FASTA_reader {
    
    long getLength(int index);
    String getDescription(int index);
    String getSequence(int index);
    String getSubsequence(String sampleSequence);
}
