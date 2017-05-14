package a;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.SourceLocation;

@Aspect
public class MainAspect {

    private static List<SequenceInformation> messages = new ArrayList<>();
    private static String s = new String();
    
    // methods in classes
    @Before("within(*.*) && call(* *.*.*(..)) && !withincode(* *.*.main(..)) && !within(*.TracingAspect) && !within(*.SequenceParser) && !within(*.MainAspect)")
    public void before3(JoinPoint thisJoinPoint) {
        entry(getThis(thisJoinPoint), getTarget(thisJoinPoint), thisJoinPoint.getSignature(), thisJoinPoint.getSourceLocation(), thisJoinPoint.getArgs());
    }
    
    @Before("within(*.*) && withincode(* *.*.main(..)) && !call(new(..)) && !within(*.SequenceParser) && !within(*.MainAspect)")
    public void before5(JoinPoint thisJoinPoint) {
        entry(thisJoinPoint.getStaticPart().getSourceLocation().getFileName().replaceAll(".java", ""), getTarget(thisJoinPoint), thisJoinPoint.getSignature(), thisJoinPoint.getSourceLocation(), thisJoinPoint.getArgs());
    }
    
    @After("within(*.*) && call(* *.*.*(..)) && !withincode(* *.*.main(..)) && !within(*.SequenceParser) && !within(*.MainAspect)")
    public void before4(JoinPoint thisJoinPoint) {
        exit(getThis(thisJoinPoint), getTarget(thisJoinPoint), thisJoinPoint.getSignature(), thisJoinPoint.getSourceLocation(), thisJoinPoint.getArgs());
    }
    
    @After("within(*.*) && withincode(* *.*.main(..)) && !call(new(..)) && !within(*.SequenceParser) && !within(*.MainAspect)")
    public void before6(JoinPoint thisJoinPoint) {
        exit(thisJoinPoint.getStaticPart().getSourceLocation().getFileName().replaceAll(".java", ""), getTarget(thisJoinPoint), thisJoinPoint.getSignature(), thisJoinPoint.getSourceLocation(), thisJoinPoint.getArgs());
    }

    private void entry(final String aThis, final String target, final Signature signature, final SourceLocation sourceLocation, final Object[] args) {
        if(aThis != null && target != null) {
            String message = aThis + " -> " + target + " : " + signature.getName() + "(" + Arrays.deepToString(args) + ")\n" + "activate " + aThis;
            messages.add(new SequenceInformation(ExecutionType.ENTRY, aThis, target, signature, sourceLocation, args));
            System.out.println(message);
        }
    }

    private void exit(final String aThis, String target, final Signature signature, final SourceLocation sourceLocation, final Object... returnValue) {
        if(aThis != null && target != null) {
            String message = target + " -> " + aThis + " : return" + "(" + Arrays.deepToString(returnValue) + ")\n deactivate " + aThis;
            messages.add(new SequenceInformation(ExecutionType.EXIT, aThis, target, signature, sourceLocation, returnValue));
            System.out.println(message);
        }
    }

    private String getTarget(JoinPoint thisJoinPoint) {
        if(thisJoinPoint == null || thisJoinPoint.getTarget() == null) return null;
        return thisJoinPoint.getTarget().getClass().getSimpleName();
    }

    private String getThis(JoinPoint thisJoinPoint) {
        if(thisJoinPoint != null && thisJoinPoint.getThis() != null) return thisJoinPoint.getThis().getClass().getSimpleName();
        String name = thisJoinPoint.getStaticPart().getSignature().getDeclaringTypeName();
        return name.substring((name.lastIndexOf(".")+1));
    }

    public static List<SequenceInformation> getMessages() {
        return Collections.unmodifiableList(messages);
    }
}
