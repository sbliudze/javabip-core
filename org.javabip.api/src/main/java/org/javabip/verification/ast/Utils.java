package org.javabip.verification.ast;

import java.util.HashSet;
import java.util.Set;

public final class Utils {
    static Set<String> postfixes = new HashSet<String>(){{add("-");add("+"); add("++");add("--");add("~");add("!");}};
    static Set<String> prefixes = new HashSet<String>(){{add("--");add("++");add("!");add("~");}};

    public boolean inPrefixes(String prefix){return prefixes.contains(prefix);}
    public boolean inPostfixes(String postfix){return postfixes.contains(postfix);}
}
