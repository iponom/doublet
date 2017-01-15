package iponom.doublet;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * The EqualsAndHashCode lombok annotation generates an appropriate code for byte arrays too
 * @author Ilya Ponomarev.
 */
@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
public class GroupKey {
    private long size;
    private long hash;
    private byte[] prefix;
}
