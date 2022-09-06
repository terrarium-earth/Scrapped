package earth.terrarium.minefactoryrenewed.common.utils;

import earth.terrarium.minefactoryrenewed.MinefactoryRenewed;

public class ConcatUtils {
    public static String modidConcat(String prefix, String... strings) {
        StringBuilder builder = new StringBuilder();
        builder.append(prefix);
        builder.append("." + MinefactoryRenewed.MOD_ID + ".");
        String joined = String.join(".", strings);
        builder.append(joined);
        return builder.toString();
    }
}
