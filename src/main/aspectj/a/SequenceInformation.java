package a;

import java.util.Arrays;
import org.aspectj.lang.Signature;
import org.aspectj.lang.reflect.SourceLocation;

public class SequenceInformation {
    private final ExecutionType traceType;
    private final String source;
    private final String target;
    private final Signature signature;
    private final SourceLocation sourceLocation;
    private final Object[] args;

    public SequenceInformation(final ExecutionType traceType, final String source, final String target, final Signature signature, final SourceLocation sourceLocation, final Object... args) {
        this.traceType = traceType;
        this.source = source;
        this.target = target;
        this.signature = signature;
        this.sourceLocation = sourceLocation;
        this.args = args;
    }

    public ExecutionType getTraceType() {
        return traceType;
    }

    public String getSource() {
        return source;
    }

    public String getTarget() {
        return target;
    }

    public Signature getSignature() {
        return signature;
    }

    public SourceLocation getSourceLocation() {
        return sourceLocation;
    }

    public Object[] getArgs() {
        return args;
    }

    public String asCallMessage() {
        return source + " -> " + target + " : " + signature.getName() + "(" + Arrays.deepToString(args) + ")";
    }

    public String asExitMessage() {
        return target + " -> " + source + " : return" + "(" + Arrays.deepToString(args) + ")";
    }

}
