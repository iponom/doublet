package iponom.doublet;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Ilya Ponomarev.
 */
@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
public class GroupKey {
    private long size;
    private long hash;
    byte [] prefix;
}
