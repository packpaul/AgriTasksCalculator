/**
 * Copyright 2018 by Pavel Perminov (packpaul@mail.ru)
 */
package com.pp.agritasks.calculator;

import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.stream.Stream;

/**
 * AgriTasks Calculator main class.
 * <br><br>
 * Usage:
 * <ol>
 *   <li><code>java -jar calculator.jar [--help]</code> - shows help.
 *   <li><code>java -jar calculator.jar &lt;expression&gt;</code> - calculates expression.
 *   <li><code>java -jar calculator.jar --file &lt;file with expression&gt;</code> -
 *       calculates expression from a text file.
 * </ol>
 * 
 * @author Pavel Perminov (packpaul@mail.ru)
 */
public class Main {
    
    public static void main(String[] args)
    {
        if ((args.length == 0) || "--help".equals(args[0])) {
            showHelp();
            System.exit(0);
        }
        
        if ("--file".equals(args[0])) {
            if (args.length != 2) {
                showHelp();
                System.exit(0);
            }
            try(Stream<String> expressions = Files.lines(Paths.get(args[1]))) {
                Calculator calculator = new Calculator();
                for (Iterator<String> ei = expressions.iterator(); ei.hasNext(); ) {
                    calculator.evaluate(ei.next());
                }
                BigInteger result = calculator.buildResult();
                System.out.println(
                        String.format("The result of expression calculation is: %s", result.toString()));
                System.exit(0);                
            } catch(Exception ex) {
                ex.printStackTrace();
                System.exit(-1);
            }
        }
        
        if (args.length != 1) {
            showHelp();
            System.exit(0);
        }

        try {
            BigInteger result = Calculator.calculate(args[0]);
            System.out.println(
                    String.format("The result of expression calculation is: %s", result.toString()));
            System.exit(0);                
        } catch(Exception ex) {
            ex.printStackTrace();
            System.exit(-1);
        }

    }

    private static void showHelp() {
        System.out.println("java -jar calculator.jar [--help] - shows help.");
        System.out.println("java -jar calculator.jar <expression> - calculates expression.");
        System.out.println("java -jar calculator.jar --file <file with expression> - " +
                               "calculates expression from a text file.");
    }
    
}
