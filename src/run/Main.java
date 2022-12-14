package run;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.fasterxml.jackson.databind.ObjectWriter;

import fileio.Input;

import fileio.Output;

import java.io.File;

import java.io.IOException;

public final class Main {
    private Main() {
    }

    /**
     * @param args input files
     */
    public static void main(final String[] args) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        Input inputData = objectMapper.readValue(new File(args[0]), Input.class);

        ObjectWriter objectWriter = objectMapper.writerWithDefaultPrettyPrinter();
        objectWriter.writeValue(new File(args[1]), Output.class);
    }
}
