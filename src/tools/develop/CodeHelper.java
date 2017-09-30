package tools.develop;

import tools.nio.NIOReader;
import tools.time.TimeHelper;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CodeHelper {
    private int lineCount = 0;
    private String rootPath;
    private String regex;
    private boolean comment;

    /**
     *
     *
     */
    private int javaFiles = 0;
    private int sourceCodeLines = 0;
    private int codeLines = 0;
    private int blankLines = 0;
    private int commentLines = 0;


    /**
     * @param rootPath
     * @param regex
     */
    public CodeHelper(String rootPath, String regex) {
        this.rootPath = rootPath;
        this.regex = regex;
    }

    public void countCode() {
        TimeHelper timeHelper = new TimeHelper();
        File file = new File(rootPath);
        doCount(file);
        report();
        System.out.println("Time cost : " + timeHelper.getAccurateSeconds() + " seconds");

    }

    public void report() {
        System.out.println("Java files : " + javaFiles);
        System.out.println("Total lines : " + lineCount);
        System.out.println("Code lines : " + codeLines);
        System.out.println("Blank lines : " + blankLines);
        System.out.println("Source code lines : " + sourceCodeLines);
        System.out.println("Comment lines : " + commentLines);

    }

    private void doCount(File file) {
        if (file == null) {
            throw new IllegalArgumentException("file is null!");
        }
        if (file.isFile() && file.getName().matches(regex)) {
            System.out.println("doCount... java source file..." + file.getAbsolutePath());
            javaFiles++;
            try {
                NIOReader nioReader = new NIOReader(file);
                while (!nioReader.isEmpty()) {
                    parseLine(nioReader.readLine());
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (file.isDirectory()) {
            for (File f : file.listFiles()) {
                doCount(f);
            }
        }
    }

    private void parseLine(String line) {
        lineCount++;
        if (parseBlank(line)) {
            blankLines++;
            return;
        } else {
            codeLines++;
        }
        line = line.trim();
        char[] chars = line.toCharArray();
        boolean slash = false;// "/"
        boolean star = false;// "*"
        boolean doubleSlash = false;// "//"
        boolean slashStar = false;// "/*"
        boolean starSlash = false;// "*/"
        boolean hasComment = false;
        boolean hasCode = false;
        for (int i = 0; i < chars.length; i++) {
            if (chars[i] == '/') {
                if (slash) {
                    doubleSlash = true;
                    slash = false;
                } else if (star) {
                    if (slashStar) {
                        hasComment = true;
                        slashStar = false;
                    } else {
                        starSlash = true;
                        star = false;
                        if (comment) {
                            hasCode = false;
                        }
                    }
                } else {
                    slash = true;
                }
            } else if (chars[i] == '*') {
                if (slash) {
                    slashStar = true;
                    hasComment = true;
                    slash = false;
                } else {
                    star = true;
                }
            } else {
                if ((!slashStar) && (!doubleSlash)) {
                    hasCode = true;
                }
            }
            if (doubleSlash) {
                hasComment = true;
                break;
            }
        }
        if (hasCode) {
            sourceCodeLines++;
        }
        if (hasComment) {
            commentLines++;
        }
        if (comment && starSlash) {
            comment = false;
        }
        if (slashStar) {
            comment = true;
        }
    }

    private boolean parseBlank(String line) {
        if (line == null || line.trim().length() == 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * parse with regular expression,use parseLine() instead.
     *
     * @param line
     */
    @Deprecated
    private void parse(String line) {
        lineCount++;
        if (line == null) {
            blankLines++;
            return;
        }
        line = line.trim();
        if (line.length() == 0) {
            blankLines++;
        } else if (comment) {
            commentLines++;
            if (line.endsWith("*/")) {
                comment = false;
            } else if (line.matches(".*\\*/.+")) {
                codeLines++;
                comment = false;
            }
        } else if (line.startsWith("//")) {
            commentLines++;
        } else if (line.matches(".+//.*")) {
            commentLines++;
            codeLines++;
        } else if (line.startsWith("/*") && line.matches(".+\\*/.+")) {
            commentLines++;
            codeLines++;
            if (findPair(line)) {
                comment = false;
            } else {
                comment = true;
            }
        } else if (line.startsWith("/*") && !line.endsWith("*/")) {
            commentLines++;
            comment = true;
        } else if (line.startsWith("/*") && line.endsWith("*/")) {
            commentLines++;
            comment = false;
        } else if (line.matches(".+/\\*.*") && !line.endsWith("*/")) {
            commentLines++;
            codeLines++;
            if (findPair(line)) {
                comment = false;
            } else {
                comment = true;
            }
        } else if (line.matches(".+/\\*.*") && line.endsWith("*/")) {
            commentLines++;
            codeLines++;
            comment = false;
        } else {
            codeLines++;
        }
    }

    private boolean findPair(String line) { // 查找一行中/*与*/是否成对出现
        int count1 = 0;
        int count2 = 0;
        Pattern p = Pattern.compile("/\\*");
        Matcher m = p.matcher(line);
        while (m.find()) {
            count1++;
        }
        p = Pattern.compile("\\*/");
        m = p.matcher(line);
        while (m.find()) {
            count2++;
        }
        return (count1 == count2);
    }

}
