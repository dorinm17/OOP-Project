import com.fasterxml.jackson.databind.ObjectMapper;

import com.fasterxml.jackson.databind.ObjectWriter;

import input.Input;

import output.Output;

import java.io.File;

import java.io.IOException;

public final class Main {
    private static int i = 1;

    private Main() {
    }

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
        objectWriter.writeValue(new File("out_basic" + i + ".json"), output.getOutput());
        i++;
        output.setInstance();
    }
}
