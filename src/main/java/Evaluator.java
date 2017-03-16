/**
 * Created by WSH on 3/4/17.
 */

import java.io.*;


public class Evaluator {
    public static void main(String[] args) throws Exception {
        System.out.print("Please enter your XQuery");
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String expr = reader.readLine();
        String temp = new String();
        while((temp = reader.readLine()) != null) {
            expr += " ";
            expr += temp;
        }
        XMLBasicOperator.XMLBuilder();
        String rewrite;
        if(!XQueryJoinDetector.detect(expr)) { // No explicit join
            rewrite = XQueryRewriter.rewrite(expr);
            if(rewrite.length() != 0) expr = rewrite; // Have implicit join
            System.out.println(expr);
        }
        writeFile(expr);
        MyNodeList result = XQueryCompiler.parse(expr);
        XMLBasicOperator.writeFile(result);
    }

    /**
     * write the rewrite query into a txt file
     * @param s
     */
    public static void writeFile(String s) {
        BufferedWriter bw = null;
        FileWriter fw = null;
        String FILENAME = "/Users/WSH/Desktop/rewrite.txt";
        try {
            String content = s;
            fw = new FileWriter(FILENAME);
            bw = new BufferedWriter(fw);
            bw.write(content);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bw != null)
                    bw.close();
                if (fw != null)
                    fw.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}
