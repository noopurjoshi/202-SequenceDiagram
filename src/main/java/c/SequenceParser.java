package c;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import a.MainAspect;
import a.SequenceInformation;
import net.sourceforge.plantuml.SourceStringReader;

public class SequenceParser {
	
	public SequenceParser() {
		draw(MainAspect.getMessages());
	}

    @SuppressWarnings("unchecked")
	public void draw(final List<SequenceInformation> messages) {
        try {
            final OutputStream png = new FileOutputStream("out.png");
            String source = "@startuml\n";
            for (Iterator<SequenceInformation> message1 = messages.iterator(); message1.hasNext();) {
            	SequenceInformation message = message1.next();
            		switch (message.getTraceType()) {
                    case ENTRY:
                    	if(message.getSource().toString().equalsIgnoreCase(message.getTarget().toString()))
                    		source += "activate " + message.getSource() + "\n" + message.asCallMessage() + "\n";
                    	else 
                    		source += "activate " + message.getSource() + "\n" + "activate " + message.getTarget() + "\n" + message.asCallMessage() + "\n";
                        break;
                    case EXIT:
                    	if(message.getSource().toString().equalsIgnoreCase(message.getTarget().toString()))
                    		source += "deactivate " + message.getSource() + "\n" + message.asExitMessage() + "\n";
                    	else 
                    		source += "deactivate " + message.getSource() + "\n" + "deactivate " + message.getTarget() + "\n" + message.asExitMessage() + "\n";
                    	/*if(message.getSource() == message.getTarget())
                    		source += message.asExitMessage() + "\n" + "deactivate " + message.getTarget() + "\n";
                    	else
                    		source += message.asExitMessage()+"\n" + "deactivate " + message.getSource() + "\n" + "deactivate " + message.getTarget() + "\n";*/
                        break;
            		}
            	}

            source += "@enduml\n";
            System.out.println(source);
            final SourceStringReader reader = new SourceStringReader(source);
            reader.generateImage(png);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
