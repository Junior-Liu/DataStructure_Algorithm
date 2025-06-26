import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class RandomWord {
    public static void main(String[] args) {
        int i = 0;
        String champion = "";                   //这里应该出问题，跑起来后换行也一直在等待输入，判准有问题
        while (!StdIn.isEmpty()) {              //A：刚检查过，判准无误，终端输入时Ctrl+Z即可截止输入，StdIn.isEmpty()会返回true
            String word = StdIn.readString();
            i++;
            if (StdRandom.bernoulli(1.0/i)) {
                champion = word;
            }
        }
        StdOut.println(champion);
    }
}
