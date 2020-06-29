package ru.nsu.g.mustafin.phrases;

class Parser {
    Args parse(String[] param) throws IllegalArgumentException {
        boolean name_flag = false;
        Args args = new Args();
        for (int i = 0; i < param.length; i++) {
            if (param[i].equals("-n") && i + 1 != param.length) {
                i++;
                args.n = Integer.parseInt(param[i]);
            } else if (param[i].equals("-m") && i + 1 != param.length) {
                i++;
                args.m = Integer.parseInt(param[i]);
            } else if (!name_flag) {
                name_flag = true;
                args.filename = param[i];
            }
        }

        if (args.n < 0 || args.m < 0) {
            throw new IllegalArgumentException("Numbers must be positive");
        }

        return args;
    }
}

class Args {
    public int n = 2;
    public int m = 2;
    public String filename = "-";
}
