import com.fasterxml.jackson.databind.ObjectMapper;

import com.fasterxml.jackson.databind.ObjectWriter;

import input.Input;

import output.Output;

import java.io.File;

import java.io.IOException;

public final class Main {
    private Main() {
    }

    private static int ct = 1;

    /**
     * @param args input and output files
     */
    public static void main(final String[] args) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        Input input = objectMapper.readValue(new File(args[0]), Input.class);

        Output output = Output.getInstance(input);
        output.runActions();

        ObjectWriter objectWriter = objectMapper.writerWithDefaultPrettyPrinter();
        objectWriter.writeValue(new File(args[1]), output.getOutput());
        objectWriter.writeValue(new File("checker/resources/out/basic_" + ct + ".json"),
                output.getOutput());
        output.setInstance();
        ct++;
    }
}
