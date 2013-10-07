import java.util.BitSet;

/**
 * Created with IntelliJ IDEA.
 * User: markdev
 * Date: 10/7/13
 * Time: 8:53 PM
 * To change this template use File | Settings | File Templates.
 */
public class FixedBitSet extends BitSet {

    int fixedLength;

    public FixedBitSet(int fixedLength){
        super(fixedLength);
        this.fixedLength = fixedLength;
    }

    @Override
    public int length() {
        return fixedLength;
    }
}
