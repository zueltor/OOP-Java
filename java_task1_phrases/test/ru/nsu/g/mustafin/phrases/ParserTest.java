package ru.nsu.g.mustafin.phrases;

import org.junit.Test;

import static org.junit.Assert.*;

public class ParserTest {

    public static void assertArgs(Args a, Args b) {
        assertEquals(a.filename, b.filename);

        assertEquals(a.n, b.n);

        assertEquals(a.m, b.m);
    }

    @Test
    public void parse() {
        String[] arg_list = {"-n", "3", "-m", "4", "test.txt"};
        Parser p = new Parser();
        Args parsed_args = p.parse(arg_list);
        Args args = new Args();
        args.filename = "test.txt";
        args.n = 3;
        args.m = 4;

        assertArgs(args, parsed_args);

        arg_list = new String[]{"-n", "122"};
        args.filename = "-";
        args.n = 122;
        args.m = 2;
        parsed_args = p.parse(arg_list);

        assertArgs(args, parsed_args);

        arg_list = new String[]{"-m", "12", "-n", "344"};
        args.n = 344;
        args.m = 12;
        parsed_args = p.parse(arg_list);

        assertArgs(args, parsed_args);

        arg_list = new String[]{"filename.txt", "-m", "123", "-n", "3444"};
        args.n = 3444;
        args.m = 123;
        args.filename = "filename.txt";
        parsed_args = p.parse(arg_list);

        assertArgs(args, parsed_args);

    }
}